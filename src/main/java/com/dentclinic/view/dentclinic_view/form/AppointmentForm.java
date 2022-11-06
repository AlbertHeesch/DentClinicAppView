package com.dentclinic.view.dentclinic_view.form;

import com.dentclinic.view.dentclinic_view.domain.Appointment;
import com.dentclinic.view.dentclinic_view.domain.Dentist;
import com.dentclinic.view.dentclinic_view.domain.Services;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public class AppointmentForm extends FormLayout
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

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public AppointmentForm(List<Dentist> dentists, List<Services> services){
        addClassName("appointment-form");

        dentist.setItems(dentists);
        dentist.setItemLabelGenerator(Dentist::getSurname);
        service.setItems(services);
        service.setItemLabelGenerator(Services::getDescription);

        configureDatePicker();

        binder.bindInstanceFields(this);

        email.setErrorMessage("Enter a valid email address");
        email.setClearButtonVisible(true);
        email.setInvalid(true);

        add(name, surname, pesel, email, date, dentist, service, createButtonsLayout());
    }

    private void configureDatePicker()
    {
        date.setLocale(new Locale("pl", "PL"));
//        date.setMin(LocalDateTime.now().plusHours(1L));
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, appointment)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
        binder.readBean(appointment);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(appointment);
            fireEvent(new SaveEvent(this, appointment));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class ContactFormEvent extends ComponentEvent<AppointmentForm> {
        private Appointment appointment;

        protected ContactFormEvent(AppointmentForm source, Appointment appointment) {
            super(source, false);
            this.appointment = appointment;
        }

        public Appointment getAppointment() {
            return appointment;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(AppointmentForm source, Appointment appointment) {
            super(source, appointment);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(AppointmentForm source, Appointment appointment) {
            super(source, appointment);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(AppointmentForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
