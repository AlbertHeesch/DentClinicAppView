package com.dentclinic.view.dentclinic_view.views;

import com.dentclinic.view.dentclinic_view.domain.AppointmentDto;
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
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;

@PermitAll
@Route(value="dentist", layout = DentistLayout.class)
@PageTitle("Appointments | DentClinicApp")
@Component
public class DentistView extends VerticalLayout
{
    private Grid<AppointmentDto> grid;
    private AppointmentService api;
    TextField filterText = new TextField();

    public DentistView(@Autowired AppointmentService appointmentService) {
        api = appointmentService;

        addClassName("dentist-view");

        grid = new Grid<>();
        grid.addColumn(AppointmentDto::getName).setHeader("Name");
        grid.addColumn(AppointmentDto::getSurname).setHeader("Surname");
        grid.addColumn(AppointmentDto::getPesel).setHeader("Pesel");
        grid.addColumn(AppointmentDto::getEmail).setHeader("Email");
        grid.addColumn(AppointmentDto::getDate).setHeader("Date");
        grid.addColumn(appointmentDto -> appointmentDto.getService().getDescription()).setHeader("Service");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        updateList();
        add(getToolbar(), grid);
        setSizeFull();
    }

    private HorizontalLayout getToolbar() {
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
