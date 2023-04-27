package com.dentclinic.view.dentclinic_view.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("home")
@PageTitle("Home | DentClinicApp")
@AnonymousAllowed
public class HomeView extends VerticalLayout {

    public HomeView()
    {
        addClassName("home-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        createText();
        createButtons();
    }

    private void createText()
    {
        add(new H1("DentClinicApp"));
        add(new H2("Welcome to our clinic!"));
    }

    private void createButtons()
    {
        Button patientButton = new Button("Book an appointment");
        patientButton.addClickListener(e ->
                patientButton.getUI().ifPresent(ui ->
                                ui.navigate("/patient")));

        patientButton.addThemeVariants(ButtonVariant.LUMO_LARGE);

        HorizontalLayout buttonsLayout = new HorizontalLayout(patientButton);
        addAndExpand(buttonsLayout);
    }
}
