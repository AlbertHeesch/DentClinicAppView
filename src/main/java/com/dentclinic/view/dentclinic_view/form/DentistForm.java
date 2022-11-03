package com.dentclinic.view.dentclinic_view.form;

import com.dentclinic.view.dentclinic_view.domain.Dentist;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.time.LocalDate;
import java.util.Locale;

public class DentistForm extends FormLayout {
    TextField name = new TextField("Name");
    TextField surname = new TextField("Surname");
    DatePicker experience = new DatePicker("Date of employment");
    Binder<Dentist> binder = new BeanValidationBinder<>(Dentist.class);
    Dentist dentist;

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public DentistForm(){
        addClassName("dentist-form");

        configureDatePicker();

        binder.bindInstanceFields(this);

        add(name, surname, experience,createButtonsLayout());
    }

    private void configureDatePicker()
    {
        experience.setLocale(new Locale("pl", "PL"));
        experience.setPlaceholder("DD.MM.YYYY");
        experience.setHelperText("Format: DD.MM.YYYY");
        experience.setMax(LocalDate.now());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, dentist)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    public void setDentist(Dentist dentist) {
        this.dentist = dentist;
        binder.readBean(dentist);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(dentist);
            fireEvent(new SaveEvent(this, dentist));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class ContactFormEvent extends ComponentEvent<DentistForm> {
        private Dentist dentist;

        protected ContactFormEvent(DentistForm source, Dentist dentist) {
            super(source, false);
            this.dentist = dentist;
        }

        public Dentist getDentist() {
            return dentist;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(DentistForm source, Dentist dentist) {
            super(source, dentist);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(DentistForm source, Dentist dentist) {
            super(source, dentist);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(DentistForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
