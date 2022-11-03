package com.dentclinic.view.dentclinic_view.views.admin;

import com.dentclinic.view.dentclinic_view.domain.Appointment;
import com.dentclinic.view.dentclinic_view.form.AppointmentForm;
import com.dentclinic.view.dentclinic_view.layout.AdminLayout;
import com.dentclinic.view.dentclinic_view.service.AppointmentService;
import com.dentclinic.view.dentclinic_view.service.DentistService;
import com.dentclinic.view.dentclinic_view.service.ServicesService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

@RolesAllowed({ "ROLE_ADMIN" })
@Route(value="admin/appointment", layout = AdminLayout.class)
@PageTitle("Appointments | DentClinicApp")
public class AdminAppointmentsView extends VerticalLayout {
    private Grid<Appointment> grid;
    private final AppointmentService api;
    private final DentistService dentApi;
    private final ServicesService servicesApi;
    TextField filterText = new TextField();
    private AppointmentForm form;

    public AdminAppointmentsView(@Autowired AppointmentService appointmentService, @Autowired DentistService dentistService,
                                 @Autowired ServicesService servicesService) {
        api = appointmentService;
        dentApi = dentistService;
        servicesApi = servicesService;

        addClassName("admin-appointment-view");

        createGrid();
        configureForm();

        add(getToolbar(),getContent());
        setSizeFull();
        updateList();
        closeEditor();
    }

    public void createGrid()
    {
        grid = new Grid<>();
        grid.addColumn(Appointment::getId).setHeader("ID");
        grid.addColumn(Appointment::getName).setHeader("Name");
        grid.addColumn(Appointment::getSurname).setHeader("Surname");
        grid.addColumn(Appointment::getPesel).setHeader("Pesel");
        grid.addColumn(Appointment::getEmail).setHeader("Email");
        grid.addColumn(appointment -> appointment.getDate().toString().replace("T", " ")).setHeader("Date");
        grid.addColumn(appointment -> appointment.getDentist().getSurname()).setHeader("Dentist");
        grid.addColumn(appointment -> appointment.getService().getDescription()).setHeader("Service");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editAppointment(event.getValue()));
    }

    public HorizontalLayout getToolbar() {
        Button addAppointmentButton = new Button("Add an appointment");
        addAppointmentButton.addClickListener(click -> addAppointment());

        filterText.setPlaceholder("Filter by surname...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addAppointmentButton);
        toolbar.addClassName("toolbar");

        if(!(api.fetchAllAppointments().size() == 0))
        {
            Paragraph editInfoText = new Paragraph("In order to edit an element, click it.");
            toolbar.add(editInfoText);
        }

        return toolbar;
    }

    private void updateList() {
            grid.setItems(api.fetchFilteredAppointments(filterText.getValue()));
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
        form = new AppointmentForm(dentApi.fetchAllDentists(), servicesApi.fetchAllServices());
        form.setWidth("25em");
        form.addListener(AppointmentForm.SaveEvent.class, this::saveAppointment);
        form.addListener(AppointmentForm.DeleteEvent.class, this::deleteAppointment);
        form.addListener(AppointmentForm.CloseEvent.class, e -> closeEditor());
    }

    private void saveAppointment(AppointmentForm.SaveEvent event) {
        List<Appointment> sameIdAppointmentList = api.fetchAllAppointments().stream()
                .filter(rate -> rate.getId().equals(event.getAppointment().getId()))
                .collect(Collectors.toList());

        if(sameIdAppointmentList.size() == 0) {
            api.saveAppointment(event.getAppointment());
        } else {
            api.updateAppointment(event.getAppointment());}
        updateList();
        closeEditor();
    }

    private void deleteAppointment(AppointmentForm.DeleteEvent event) {
        api.deleteAppointment(event.getAppointment());
        updateList();
        closeEditor();
    }

    public void editAppointment(Appointment appointment) {
        if (appointment == null) {
            closeEditor();
        } else {
            form.setAppointment(appointment);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setAppointment(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addAppointment() {
        grid.asSingleSelect().clear();
        editAppointment(new Appointment());
    }
}
