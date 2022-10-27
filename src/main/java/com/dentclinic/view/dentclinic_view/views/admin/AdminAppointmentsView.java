package com.dentclinic.view.dentclinic_view.views.admin;

import com.dentclinic.view.dentclinic_view.domain.Appointment;
import com.dentclinic.view.dentclinic_view.domain.Dentist;
import com.dentclinic.view.dentclinic_view.domain.Services;
import com.dentclinic.view.dentclinic_view.layout.AdminLayout;
import com.dentclinic.view.dentclinic_view.service.AppointmentService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;

@RolesAllowed({ "ROLE_ADMIN" })
@Route(value="admin/appointment", layout = AdminLayout.class)
@PageTitle("Appointments | DentClinicApp")
public class AdminAppointmentsView extends VerticalLayout {
    private Grid<Appointment> grid;
    private AppointmentService api;
    TextField filterText = new TextField();

    public AdminAppointmentsView(@Autowired AppointmentService appointmentService) {
        api = appointmentService;

        addClassName("admin-appointment-view");

        createGrid();
        updateList();

        add(getToolbar(), grid);
        setSizeFull();
    }

    public void createGrid()
    {
        grid = new Grid<>();
        grid.addColumn(Appointment::getId).setHeader("ID");
        grid.addColumn(Appointment::getName).setHeader("Name");
        grid.addColumn(Appointment::getSurname).setHeader("Surname");
        grid.addColumn(Appointment::getPesel).setHeader("Pesel");
        grid.addColumn(Appointment::getEmail).setHeader("Email");
        grid.addColumn(Appointment::getDate).setHeader("Date");
        grid.addColumn(appointmentDto -> appointmentDto.getDentist().getSurname()).setHeader("Dentist");
        grid.addColumn(appointmentDto -> appointmentDto.getService().getDescription()).setHeader("Service");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    public HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by surname...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateList() {
        if(api.fetchAllAppointments().size() == 0) {
            grid.setItems(new Appointment(0L, " ", "No appointments found", " ", " ", LocalDate.now(),
                    new Dentist(0L, " ", " ", LocalDate.now()),
                    new Services(0L, " ", 0.0)));
        } else {
            grid.setItems(api.fetchFilteredAppointments(filterText.getValue()));
        }
    }
}

