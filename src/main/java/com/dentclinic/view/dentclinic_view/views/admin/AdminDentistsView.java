package com.dentclinic.view.dentclinic_view.views.admin;

import com.dentclinic.view.dentclinic_view.domain.Dentist;
import com.dentclinic.view.dentclinic_view.form.DentistForm;
import com.dentclinic.view.dentclinic_view.layout.AdminLayout;
import com.dentclinic.view.dentclinic_view.service.DentistService;
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
import java.util.List;
import java.util.stream.Collectors;

@RolesAllowed({ "ROLE_ADMIN" })
@Route(value="admin/dentist", layout = AdminLayout.class)
@PageTitle("Dentists | DentClinicApp")
public class AdminDentistsView extends VerticalLayout {
    private Grid<Dentist> grid;
    private final DentistService api;
    private DentistForm form;

    public AdminDentistsView(@Autowired DentistService dentistService) {
        api = dentistService;

        addClassName("admin-dentist-view");

        createGrid();
        configureForm();

        add(getToolbar(),getContent());
        setSizeFull();
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolbar() {
        Button addDentistButton = new Button("Add a dentist");
        addDentistButton.addClickListener(click -> addDentist());

        HorizontalLayout toolbar = new HorizontalLayout(addDentistButton);
        toolbar.addClassName("toolbar");

        if(!(api.fetchAllDentists().size() == 0))
        {
            Paragraph editInfoText = new Paragraph("In order to edit an element, click it.");
            toolbar.add(editInfoText);
        }

        return toolbar;
    }

    public void createGrid()
    {
        grid = new Grid<>();
        grid.addColumn(Dentist::getId).setHeader("ID");
        grid.addColumn(Dentist::getName).setHeader("Name");
        grid.addColumn(Dentist::getSurname).setHeader("Surname");
        grid.addColumn(Dentist::getExperience).setHeader("Experience");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editDentist(event.getValue()));
    }

    private void updateList() {
            grid.setItems(api.fetchAllDentists());
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
        form = new DentistForm();
        form.setWidth("25em");
        form.addListener(DentistForm.SaveEvent.class, this::saveDentist);
        form.addListener(DentistForm.DeleteEvent.class, this::deleteDentist);
        form.addListener(DentistForm.CloseEvent.class, e -> closeEditor());
    }

    private void saveDentist(DentistForm.SaveEvent event) {
        List<Dentist> sameIdDentistList = api.fetchAllDentists().stream()
                .filter(rate -> rate.getId().equals(event.getDentist().getId()))
                .collect(Collectors.toList());

        if(sameIdDentistList.size() == 0) {
            api.saveDentist(event.getDentist());
        } else {
            api.updateDentist(event.getDentist());}
        updateList();
        closeEditor();
    }

    private void deleteDentist(DentistForm.DeleteEvent event) {
        api.deleteDentist(event.getDentist());
        updateList();
        closeEditor();
    }

    public void editDentist(Dentist dentist) {
        if (dentist == null) {
            closeEditor();
        } else {
            form.setDentist(dentist);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setDentist(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addDentist() {
        grid.asSingleSelect().clear();
        editDentist(new Dentist());
    }
}

