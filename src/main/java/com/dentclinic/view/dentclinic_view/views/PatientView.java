package com.dentclinic.view.dentclinic_view.views;

import com.dentclinic.view.dentclinic_view.domain.Appointment;
import com.dentclinic.view.dentclinic_view.domain.Rate;
import com.dentclinic.view.dentclinic_view.domain.Services;
import com.dentclinic.view.dentclinic_view.form.AppointmentForm;
import com.dentclinic.view.dentclinic_view.service.AppointmentService;
import com.dentclinic.view.dentclinic_view.service.DentistService;
import com.dentclinic.view.dentclinic_view.service.RateService;
import com.dentclinic.view.dentclinic_view.service.ServicesService;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
    private final RateService rateApi;
    private AppointmentForm form;

    HorizontalLayout combinedLayout = new HorizontalLayout();
    Appointment appointment = new Appointment();

    public PatientView(@Autowired AppointmentService appointmentService, @Autowired DentistService dentistService,
                                 @Autowired ServicesService servicesService, @Autowired RateService rateService) {
        api = appointmentService;
        dentApi = dentistService;
        servicesApi = servicesService;
        rateApi = rateService;

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

        form.getDate().setEnabled(false);

        form.getDentist().addValueChangeListener(e -> form.getDate().setEnabled(true));
        form.getDentist().setAllowCustomValue(false);

        form.getService().setAllowCustomValue(false);

        List<LocalDateTime> appointmentsDateTime = api.fetchAllAppointments().stream()
                .map(Appointment::getDate)
                .collect(Collectors.toList());

        form.getBinder().forField(form.getDate())
                .withValidator(localDateTime -> {
                    boolean isDateOk = true;
                    for(LocalDateTime local : appointmentsDateTime)
                    {
                        if(local.isEqual(localDateTime))
                        { isDateOk =false; }
                    }
                    return isDateOk;
                }, "The selected time is not available")
                .withValidator(startDateTime -> startDateTime.getDayOfWeek().getValue() >= 1
                        && startDateTime.getDayOfWeek().getValue() <= 5, "The selected day of week is not available")
                .withValidator(startDateTime -> {
                    LocalTime startTime = LocalTime.of(startDateTime.getHour(),
                            startDateTime.getMinute());
                    return !(LocalTime.of(8, 0).isAfter(startTime)
                            || LocalTime.of(15, 0).isBefore(startTime));
                }, "Our clinic is open from 8 am to 4 pm")
                .bind(Appointment::getDate,
                        Appointment::setDate);
    }

    private void configureCombinedLayout() {
        H1 logo = new H1("DentClinicApp");

        Paragraph instructionText = new Paragraph("In order to create an appointment please fill in the form.");

        String basePriceAndTax = "<div>" + setBasePricesTxt() + setTaxTxt() + "</div>";
        Html htmlBasePriceAndTax = new Html(basePriceAndTax);
        Paragraph basePriceAndTaxP = new Paragraph(htmlBasePriceAndTax);

        String currencies = "<div>You can pay in our clinic using other currencies than PLN.<br>" + setRateTxt() + "</div>";
        Html htmlCurrencies = new Html(currencies);
        Paragraph currenciesPricesP = new Paragraph(htmlCurrencies);

        VerticalLayout priceVerticalLayout = new VerticalLayout(basePriceAndTaxP, currenciesPricesP);
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

    private List<Rate> getRateList()
    { return new ArrayList<>(rateApi.fetchAllRates()); }

    private List<Services> getServicesList()
    { return new ArrayList<>(servicesApi.fetchAllServices()); }

    private List<BigDecimal> getUsdList()
    {
        return new ArrayList<>(getRateList().stream()
                .filter(name -> name.getName().equals("USD"))
                .map(Rate::getValue)
                .collect(Collectors.toList()));
    }

    private List<BigDecimal> getEurList()
    {
        return new ArrayList<>(getRateList().stream()
                .filter(name -> name.getName().equals("EUR"))
                .map(Rate::getValue)
                .collect(Collectors.toList()));
    }

    private List<BigDecimal> getGbpList()
    {
        return new ArrayList<>(getRateList().stream()
                .filter(name -> name.getName().equals("GBP"))
                .map(Rate::getValue)
                .collect(Collectors.toList()));
    }

    private List<BigDecimal> getTaxList()
    {
        return new ArrayList<>(getRateList().stream()
                .filter(name -> name.getName().equals("TAX"))
                .map(Rate::getValue)
                .collect(Collectors.toList()));
    }

    private String setRateTxt()
    {
        String currenciesRates = "";

        if((!getUsdList().isEmpty()) && (!getEurList().isEmpty()) && (!getGbpList().isEmpty()))
        {
            currenciesRates = "Rates according to PLN:<br>" +
                    "USD: " + getUsdList().get(0) + "<br>EUR: " + getEurList().get(0) + "<br>GBP: " + getGbpList().get(0);
        }
        return currenciesRates;
    }

    private String setTaxTxt()
    {
        String taxRateInfoTxt = "";

        if(!(getRateList().isEmpty() || getServicesList().isEmpty()))
        {
            taxRateInfoTxt = "<br>+ Poland tax rate: " + getTaxList().get(0) + " %";
        }
        return taxRateInfoTxt;
    }

    private String setBasePricesTxt()
    {
        StringBuilder basePrices = new StringBuilder();

        if(!(getServicesList().size() == 0)) {
            basePrices = new StringBuilder("Base services prices: <br>");
            for (Services service : getServicesList()) {
                basePrices.append(service.getDescription()).append(": ").append(service.getCost()).append("<br>");
            }
        }
        return basePrices.toString();
    }
}
