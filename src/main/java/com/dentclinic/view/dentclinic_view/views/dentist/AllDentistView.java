package com.dentclinic.view.dentclinic_view.views.dentist;

import com.dentclinic.view.dentclinic_view.domain.Appointment;
import com.dentclinic.view.dentclinic_view.layout.DentistLayout;
import com.dentclinic.view.dentclinic_view.service.AppointmentService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;

@PermitAll
@Route(value="dentist/all", layout = DentistLayout.class)
@PageTitle("Appointments | DentClinicApp")
public class AllDentistView extends VerticalLayout
{
    private Grid<Appointment> grid;
    private AppointmentService api;
    TextField filterText = new TextField();

    public AllDentistView(@Autowired AppointmentService appointmentService) {
        api = appointmentService;

        addClassName("all-dentist-view");

        createGrid();
        updateList();

        add(getToolbar(), grid);
        setSizeFull();
    }

    public void createGrid()
    {
        grid = new Grid<>();
        grid.addColumn(Appointment::getName).setHeader("Name");
        grid.addColumn(Appointment::getSurname).setHeader("Surname");
        grid.addColumn(Appointment::getPesel).setHeader("Pesel");
        grid.addColumn(Appointment::getEmail).setHeader("Email");
        grid.addColumn(appointment -> appointment.getDate().toString().replace("T", " ")).setHeader("Date");
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
        grid.setItems(api.fetchFilteredAppointments(filterText.getValue()));
    }
}
