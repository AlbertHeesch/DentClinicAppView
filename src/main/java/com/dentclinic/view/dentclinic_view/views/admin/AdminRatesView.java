package com.dentclinic.view.dentclinic_view.views.admin;

import com.dentclinic.view.dentclinic_view.domain.Rate;
import com.dentclinic.view.dentclinic_view.form.RateForm;
import com.dentclinic.view.dentclinic_view.layout.AdminLayout;
import com.dentclinic.view.dentclinic_view.service.RateService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;

@RolesAllowed({ "ROLE_ADMIN" })
@Route(value="admin/rate", layout = AdminLayout.class)
@PageTitle("Rates | DentClinicApp")
public class AdminRatesView extends VerticalLayout {
    private Grid<Rate> grid;
    private final RateService api;
    private RateForm form;

    public AdminRatesView(@Autowired RateService rateService) {
        api = rateService;

        addClassName("admin-rate-view");

        createGrid();
        configureForm();

        add(getToolbar(),getContent());
        setSizeFull();
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolbar() {
        Button addRateButton = new Button("Add a rate");
        addRateButton.addClickListener(click -> addRate());

        HorizontalLayout toolbar = new HorizontalLayout(addRateButton);
        toolbar.addClassName("toolbar");

        if(!(api.fetchAllRates().size() == 0))
        {
            Paragraph editInfoText = new Paragraph("In order to edit an element, click it.");
            toolbar.add(editInfoText);
        }

        return toolbar;
    }

    public void createGrid()
    {
        grid = new Grid<>();
        grid.addColumn(Rate::getId).setHeader("ID");
        grid.addColumn(Rate::getName).setHeader("Name");
        grid.addColumn(Rate::getValue).setHeader("Value");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editRate(event.getValue()));
    }
    private void updateList() {
            grid.setItems(api.fetchAllRates());
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
        form = new RateForm();
        form.setWidth("25em");
        form.addListener(RateForm.SaveEvent.class, this::saveRate);
        form.addListener(RateForm.DeleteEvent.class, this::deleteRate);
        form.addListener(RateForm.CloseEvent.class, e -> closeEditor());
    }

    private void saveRate(RateForm.SaveEvent event) {
        api.saveRate(event.getRate());
        updateList();
        closeEditor();
    }

    private void deleteRate(RateForm.DeleteEvent event) {
        api.deleteRate(event.getRate());
        updateList();
        closeEditor();
    }

    public void editRate(Rate rate) {
        if (rate == null) {
            closeEditor();
        } else {
            form.setRate(rate);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setRate(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addRate() {
        grid.asSingleSelect().clear();
        editRate(new Rate());
    }
}

