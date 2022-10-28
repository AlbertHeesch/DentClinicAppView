package com.dentclinic.view.dentclinic_view.form;

import com.dentclinic.view.dentclinic_view.domain.Rate;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class RateForm extends FormLayout {
    TextField name = new TextField("Rate Name");
    BigDecimalField value = new BigDecimalField("Value");
    Binder<Rate> binder = new BeanValidationBinder<>(Rate.class);
    Rate rate;

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public RateForm(){
        addClassName("rate-form");


        binder.bindInstanceFields(this);

        add(name, value, createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, rate)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    public void setRate(Rate rate) {
        this.rate = rate;
        binder.readBean(rate);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(rate);
            fireEvent(new SaveEvent(this, rate));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class ContactFormEvent extends ComponentEvent<RateForm> {
        private Rate rate;

        protected ContactFormEvent(RateForm source, Rate rate) {
            super(source, false);
            this.rate = rate;
        }

        public Rate getRate() {
            return rate;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(RateForm source, Rate rate) {
            super(source, rate);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(RateForm source, Rate rate) {
            super(source, rate);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(RateForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
