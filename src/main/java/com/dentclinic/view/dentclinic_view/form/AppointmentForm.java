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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    private Button back = new Button("Back");

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
        date.setHelperText("Open Mondays-Fridays, 8:00-16:00");
        date.setDatePlaceholder("Date");
        date.setTimePlaceholder("Time");
        date.setMin(LocalDateTime.now());
        date.setMax(LocalDateTime.now().plusDays(14));
        binder.forField(date).withValidator(startDateTime -> startDateTime.getDayOfWeek().getValue() >= 1
                && startDateTime.getDayOfWeek().getValue() <= 5, "The selected day of week is not available")
                .withValidator(startDateTime -> {
                    LocalTime startTime = LocalTime.of(startDateTime.getHour(),
                            startDateTime.getMinute());
                    return !(LocalTime.of(8, 0).isAfter(startTime)
                            || LocalTime.of(15, 0).isBefore(startTime));
                }, "The selected time is not available")
                .bind(Appointment::getDate,
                        Appointment::setDate);
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

        back.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        back.addClickShortcut(Key.ESCAPE);
        back.addClickListener(event -> back.getUI().ifPresent(ui -> ui.navigate("/home")));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close, back);
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

    public Button getSave() {
        return save;
    }

    public Button getDelete() {
        return delete;
    }

    public Button getClose() {
        return close;
    }

    public Button getBack() {
        return back;
    }

    public ComboBox<Services> getService() {
        return service;
    }
}
