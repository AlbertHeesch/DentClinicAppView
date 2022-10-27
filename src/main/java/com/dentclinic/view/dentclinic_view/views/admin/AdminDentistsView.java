package com.dentclinic.view.dentclinic_view.views.admin;

import com.dentclinic.view.dentclinic_view.domain.Dentist;
import com.dentclinic.view.dentclinic_view.layout.AdminLayout;
import com.dentclinic.view.dentclinic_view.service.AppointmentService;
import com.dentclinic.view.dentclinic_view.service.DentistService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;

@RolesAllowed({ "ROLE_ADMIN" })
@Route(value="admin/dentist", layout = AdminLayout.class)
@PageTitle("Dentists | DentClinicApp")
public class AdminDentistsView extends VerticalLayout {
    private Grid<Dentist> grid;
    private DentistService api;

    public AdminDentistsView(@Autowired DentistService dentistService) {
        api = dentistService;

        addClassName("admin-dentist-view");

        createGrid();
        updateList();

        add(grid);
        setSizeFull();
    }

    public void createGrid()
    {
        grid = new Grid<>();
        grid.addColumn(Dentist::getId).setHeader("ID");
        grid.addColumn(Dentist::getName).setHeader("Name");
        grid.addColumn(Dentist::getSurname).setHeader("Surname");
        grid.addColumn(Dentist::getExperience).setHeader("Experience");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
    private void updateList() {
        if(api.fetchAllDentists().size() == 0)
        {
            grid.setItems(new Dentist(0L, " ", "No dentists found", LocalDate.now()));
        } else {
            grid.setItems(api.fetchAllDentists());
        }
    }
}

