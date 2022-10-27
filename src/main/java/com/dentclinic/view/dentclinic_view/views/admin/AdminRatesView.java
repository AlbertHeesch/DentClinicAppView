package com.dentclinic.view.dentclinic_view.views.admin;

import com.dentclinic.view.dentclinic_view.domain.Rate;
import com.dentclinic.view.dentclinic_view.layout.AdminLayout;
import com.dentclinic.view.dentclinic_view.service.AppointmentService;
import com.vaadin.flow.component.grid.Grid;
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
    private AppointmentService api;

    public AdminRatesView(@Autowired AppointmentService appointmentService) {
        api = appointmentService;

        addClassName("admin-rate-view");

        createGrid();
//        updateList();

        add(grid);
        setSizeFull();
    }

    public void createGrid()
    {
        grid = new Grid<>();
        grid.addColumn(Rate::getId).setHeader("ID");
        grid.addColumn(Rate::getName).setHeader("Name");
        grid.addColumn(Rate::getValue).setHeader("Value");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
//    private void updateList() {
//        grid.setItems(api.fetchFilteredAppointments(filterText.getValue()));
//    }
}

