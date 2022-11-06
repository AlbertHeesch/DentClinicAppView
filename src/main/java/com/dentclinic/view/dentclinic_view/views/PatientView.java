package com.dentclinic.view.dentclinic_view.views;

import com.dentclinic.view.dentclinic_view.domain.Appointment;
import com.dentclinic.view.dentclinic_view.form.AppointmentForm;
import com.dentclinic.view.dentclinic_view.service.AppointmentService;
import com.dentclinic.view.dentclinic_view.service.DentistService;
import com.dentclinic.view.dentclinic_view.service.ServicesService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Route("patient")
@PageTitle("Patient | DentClinicApp")
@AnonymousAllowed
public class PatientView extends VerticalLayout
{
    private final AppointmentService api;
    private final DentistService dentApi;
    private final ServicesService servicesApi;
    private AppointmentForm form;

    HorizontalLayout combinedLayout = new HorizontalLayout();

    public PatientView(@Autowired AppointmentService appointmentService, @Autowired DentistService dentistService,
                                 @Autowired ServicesService servicesService) {
        api = appointmentService;
        dentApi = dentistService;
        servicesApi = servicesService;
        form = new AppointmentForm(dentApi.fetchAllDentists(), servicesApi.fetchAllServices());

        form.setWidth("25em");
        form.addListener(AppointmentForm.SaveEvent.class, this::saveAppointment);
        form.setAppointment(new Appointment());
        form.setVisible(true);

        addClassName("patient-view");
        configureCombinedLayout();

        add(combinedLayout);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private void configureCombinedLayout()
    {
        long price = 0;
        H1 logo = new H1("DentClinicApp");
        Paragraph instructionText = new Paragraph("In order to create an appointment please fill in the form below.");

        Paragraph basePriceTxt = new Paragraph("Base price: " + " PLN");
        Paragraph taxRateTxt = new Paragraph("Poland tax rate: " + " %");
        H2 totalPriceTxt = new H2("Total price: " + price + " PLN");
        VerticalLayout priceVerticalLayout = new VerticalLayout(basePriceTxt, taxRateTxt, totalPriceTxt);

        VerticalLayout verticalLayout = new VerticalLayout(logo, instructionText, priceVerticalLayout);
        HorizontalLayout horizontalLayout = new HorizontalLayout(form);
        combinedLayout.add(verticalLayout, horizontalLayout);
    }

    private void saveAppointment(AppointmentForm.SaveEvent event) {
        List<Appointment> sameIdAppointmentList = api.fetchAllAppointments().stream()
                .filter(rate -> rate.getId().equals(event.getAppointment().getId()))
                .collect(Collectors.toList());

        if(sameIdAppointmentList.size() == 0) {
            api.saveAppointment(event.getAppointment());
        } else {
            api.updateAppointment(event.getAppointment());}
        closeEditor();
    }

    private void closeEditor() {
        form.setAppointment(null);
        form.setVisible(false);
    }
}
