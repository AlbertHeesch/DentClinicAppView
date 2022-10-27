package com.dentclinic.view.dentclinic_view.views.admin;

import com.dentclinic.view.dentclinic_view.domain.Services;
import com.dentclinic.view.dentclinic_view.layout.AdminLayout;
import com.dentclinic.view.dentclinic_view.service.ServicesService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;

@RolesAllowed({ "ROLE_ADMIN" })
@Route(value="admin/service", layout = AdminLayout.class)
@PageTitle("Services | DentClinicApp")
public class AdminServicesView extends VerticalLayout {
    private Grid<Services> grid;
    private ServicesService api;

    public AdminServicesView(@Autowired ServicesService servicesService) {
        api = servicesService;

        addClassName("admin-service-view");

        createGrid();
        updateList();

        add(grid);
        setSizeFull();
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
            grid.setItems(new Services(0L, "No services found", 0.0));
        } else {
            grid.setItems(api.fetchAllServices());
        }
    }
}

