package com.dentclinic.view.dentclinic_view.views;

import com.dentclinic.view.dentclinic_view.domain.Appointment;
import com.dentclinic.view.dentclinic_view.form.AppointmentForm;
import com.dentclinic.view.dentclinic_view.service.AppointmentService;
import com.dentclinic.view.dentclinic_view.service.DentistService;
import com.dentclinic.view.dentclinic_view.service.ServicesService;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
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
    Appointment appointment = new Appointment();
    private Long price = 0L;

    public PatientView(@Autowired AppointmentService appointmentService, @Autowired DentistService dentistService,
                                 @Autowired ServicesService servicesService) {
        api = appointmentService;
        dentApi = dentistService;
        servicesApi = servicesService;

        addClassName("patient-view");
        configureForm();
        configureCombinedLayout();

        add(combinedLayout);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private void configureForm()
    {
        form = new AppointmentForm(dentApi.fetchAllDentists(), servicesApi.fetchAllServices());
        form.setWidth("25em");
        form.addListener(AppointmentForm.SaveEvent.class, this::saveAppointment);
        form.setAppointment(appointment);
        form.setVisible(true);
        form.getSave().setText("Book");
        form.getDelete().setVisible(false);
        form.getClose().setVisible(false);
    }

    private void configureCombinedLayout()
    {
        H1 logo = new H1("DentClinicApp");
        Paragraph instructionText = new Paragraph("In order to create an appointment please fill in the form below.");


        String basePriceAndTax = "<div>Base price: PLN<br>Poland tax rate: %</div>";
        Html htmlBasePriceAndTax = new Html(basePriceAndTax);
        Paragraph basePriceAndTaxP = new Paragraph(htmlBasePriceAndTax);

        H2 totalPriceTxt = new H2("Total price: " + price + " PLN");

        String currencies = "<div>EUR: <br>USD: <br>GBP: </div>";
        Html htmlCurrencies = new Html(currencies);
        Paragraph currenciesPricesP = new Paragraph(htmlCurrencies);

        VerticalLayout priceVerticalLayout = new VerticalLayout(basePriceAndTaxP, totalPriceTxt, currenciesPricesP);
        VerticalLayout verticalLayout = new VerticalLayout(logo, instructionText, priceVerticalLayout);
        HorizontalLayout horizontalLayout = new HorizontalLayout(form);
        combinedLayout.add(verticalLayout, horizontalLayout);

        boolean isServicePicked = Optional.ofNullable(form.getService().getValue()).isPresent();
        priceVerticalLayout.setVisible(!isServicePicked);
    }

    private void saveAppointment(AppointmentForm.SaveEvent event) {
        List<Appointment> sameIdAppointmentList = api.fetchAllAppointments().stream()
                .filter(rate -> rate.getId().equals(event.getAppointment().getId()))
                .collect(Collectors.toList());

        if(sameIdAppointmentList.size() == 0) {
            api.saveAppointment(event.getAppointment());
        } else {
            api.updateAppointment(event.getAppointment());}
        booked();
    }

    private void booked() {
        form.setAppointment(null);
        Notification notification = Notification
                .show("An appointment has been booked! :)");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        getUI().ifPresent(ui ->
                ui.navigate("/home"));
    }
}
