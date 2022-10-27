package com.dentclinic.view.dentclinic_view.views.admin;

import com.dentclinic.view.dentclinic_view.domain.Services;
import com.dentclinic.view.dentclinic_view.layout.AdminLayout;
import com.dentclinic.view.dentclinic_view.service.AppointmentService;
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
    private AppointmentService api;

    public AdminServicesView(@Autowired AppointmentService appointmentService) {
        api = appointmentService;

        addClassName("admin-service-view");

        createGrid();
//        updateList();

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

//    private void updateList() {
//        grid.setItems(api.fetchFilteredAppointments(filterText.getValue()));
//    }
}

