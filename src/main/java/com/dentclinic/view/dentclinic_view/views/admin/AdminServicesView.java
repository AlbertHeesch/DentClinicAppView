package com.dentclinic.view.dentclinic_view.views.admin;

import com.dentclinic.view.dentclinic_view.domain.Services;
import com.dentclinic.view.dentclinic_view.form.RateForm;
import com.dentclinic.view.dentclinic_view.form.ServicesForm;
import com.dentclinic.view.dentclinic_view.layout.AdminLayout;
import com.dentclinic.view.dentclinic_view.service.ServicesService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.math.BigDecimal;

@RolesAllowed({ "ROLE_ADMIN" })
@Route(value="admin/service", layout = AdminLayout.class)
@PageTitle("Services | DentClinicApp")
public class AdminServicesView extends VerticalLayout {
    private Grid<Services> grid;
    private final ServicesService api;
    private ServicesForm form;

    public AdminServicesView(@Autowired ServicesService servicesService) {
        api = servicesService;

        addClassName("admin-service-view");

        createGrid();
        configureForm();
        updateList();

        add(getToolbar(),getContent());
        setSizeFull();
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolbar() {
        Button addServiceButton = new Button("Add service");
        addServiceButton.addClickListener(click -> addService());

        HorizontalLayout toolbar = new HorizontalLayout(addServiceButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void createGrid()
    {
        grid = new Grid<>();
        grid.addColumn(Services::getId).setHeader("ID");
        grid.addColumn(Services::getDescription).setHeader("Service");
        grid.addColumn(Services::getCost).setHeader("Cost");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void updateList() {
        if(api.fetchAllServices().size() == 0)
        {
            grid.setItems(new Services(0L, "No services found", new BigDecimal("0.0")));
        } else {
            grid.setItems(api.fetchAllServices());
        }
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new ServicesForm();
        form.setWidth("25em");
        form.addListener(ServicesForm.SaveEvent.class, this::saveService);
        form.addListener(ServicesForm.DeleteEvent.class, this::deleteService);
        form.addListener(RateForm.CloseEvent.class, e -> closeEditor());
    }

    private void saveService(ServicesForm.SaveEvent event) {
        api.saveService(event.getService());
        updateList();
        closeEditor();
    }

    private void deleteService(ServicesForm.DeleteEvent event) {
        api.deleteService(event.getService());
        updateList();
        closeEditor();
    }

    public void editService(Services services) {
        if (services == null) {
            closeEditor();
        } else {
            form.setService(services);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setService(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addService() {
        grid.asSingleSelect().clear();
        editService(new Services());
    }
}


