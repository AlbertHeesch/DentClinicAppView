package com.dentclinic.view.dentclinic_view.views;

import com.dentclinic.view.dentclinic_view.domain.Appointment;
import com.dentclinic.view.dentclinic_view.domain.Dentist;
import com.dentclinic.view.dentclinic_view.domain.Services;
import com.dentclinic.view.dentclinic_view.service.AppointmentService;
import com.dentclinic.view.dentclinic_view.service.DentistService;
import com.dentclinic.view.dentclinic_view.service.ServicesService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Locale;

@Route("patient")
@PageTitle("Patient | DentClinicApp")
@AnonymousAllowed
public class PatientView extends VerticalLayout
{
    private final TextField name = new TextField("Name");
    private final TextField surname = new TextField("Surname");
    private final BigDecimalField pesel = new BigDecimalField("PESEL");
    private final EmailField email = new EmailField("Email address");
    private final DateTimePicker date = new DateTimePicker("Appointment date");
    private final ComboBox<Dentist> dentist = new ComboBox<>("Dentist");
    private final ComboBox<Services> service = new ComboBox<>("Service");
    Binder<Appointment> binder = new BeanValidationBinder<>(Appointment.class);
    private Appointment appointment;
    private final AppointmentService api;
    private final DentistService dentApi;
    private final ServicesService servicesApi;

    Button save = new Button("Save");
    Button back = new Button("Back");

    public PatientView(@Autowired AppointmentService appointmentService, @Autowired DentistService dentistService,
                       @Autowired ServicesService servicesService) {
        api = appointmentService;
        dentApi = dentistService;
        servicesApi = servicesService;
        addClassName("patient");

        setSizeFull();
        setAlignItems(Alignment.CENTER);

        H1 logo = new H1("DentClinicApp");
        Paragraph instructionText = new Paragraph("In order to create an appointment please fill in the form below.");

        configureDatePicker();
        configureComboBoxes();

        binder.bindInstanceFields(this);

        email.setErrorMessage("Enter a valid email address");
        email.setClearButtonVisible(true);

        HorizontalLayout nameAndSurnameLayout = new HorizontalLayout(name, surname);
        HorizontalLayout dateLayout = new HorizontalLayout(date);
        HorizontalLayout peselAndEmailLayout = new HorizontalLayout(pesel, email);
        HorizontalLayout dentistAndServiceLayout = new HorizontalLayout(dentist, service);
        HorizontalLayout buttonsLayout = new HorizontalLayout(createButtonsLayout());

        addListener(PatientView.SaveEvent.class, this::saveAppointment);

        add(logo,  instructionText, nameAndSurnameLayout, dateLayout, peselAndEmailLayout, dentistAndServiceLayout, buttonsLayout);
    }

    private void configureComboBoxes()
    {
        dentist.setItems(dentApi.fetchAllDentists());
        dentist.setItemLabelGenerator(Dentist::getSurname);
        service.setItems(servicesApi.fetchAllServices());
        service.setItemLabelGenerator(Services::getDescription);
    }

    private void configureDatePicker()
    {
        date.setLocale(new Locale("pl", "PL"));
        date.setMin(LocalDateTime.now());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);
        save.addClickListener(event -> validateAndSave());

        back.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        back.addClickShortcut(Key.ESCAPE);
        back.addClickListener(event -> back.getUI().ifPresent(ui -> ui.navigate("/home")));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, back);
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
        binder.readBean(appointment);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(appointment);
            fireEvent(new PatientView.SaveEvent(this, appointment));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class ContactFormEvent extends ComponentEvent<PatientView> {
        private Appointment appointment;

        protected ContactFormEvent(PatientView source, Appointment appointment) {
            super(source, false);
            this.appointment = appointment;
        }

        public Appointment getAppointment() {
            return appointment;
        }
    }

    public static class SaveEvent extends PatientView.ContactFormEvent {
        SaveEvent(PatientView source, Appointment appointment) {
            super(source, appointment);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    private void saveAppointment(PatientView.SaveEvent event) {
            api.saveAppointment(event.getAppointment());
    }

    public void editAppointment(Appointment appointment) {
            setAppointment(appointment);
            setVisible(true);
            addClassName("editing");
    }

    private void addAppointment() {
        editAppointment(new Appointment());
    }
}
