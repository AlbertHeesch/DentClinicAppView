package com.dentclinic.view.dentclinic_view.form;

import com.dentclinic.view.dentclinic_view.domain.Services;
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

public class ServicesForm extends FormLayout {
    TextField description = new TextField("Service Description");
    BigDecimalField cost = new BigDecimalField("Cost PLN");
    Binder<Services> binder = new BeanValidationBinder<>(Services.class);
    Services service;

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public ServicesForm(){
        addClassName("services-form");


        binder.bindInstanceFields(this);

        add(description, cost, createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new ServicesForm.DeleteEvent(this, service)));
        close.addClickListener(event -> fireEvent(new ServicesForm.CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    public void setService(Services service) {
        this.service = service;
        binder.readBean(service);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(service);
            fireEvent(new ServicesForm.SaveEvent(this, service));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class ContactFormEvent extends ComponentEvent<ServicesForm> {
        private Services service;

        protected ContactFormEvent(ServicesForm source, Services services) {
            super(source, false);
            this.service = services;
        }

        public Services getService() {
            return service;
        }
    }

    public static class SaveEvent extends ServicesForm.ContactFormEvent {
        SaveEvent(ServicesForm source, Services services) {
            super(source, services);
        }
    }

    public static class DeleteEvent extends ServicesForm.ContactFormEvent {
        DeleteEvent(ServicesForm source, Services services) {
            super(source, services);
        }

    }

    public static class CloseEvent extends ServicesForm.ContactFormEvent {
        CloseEvent(ServicesForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}