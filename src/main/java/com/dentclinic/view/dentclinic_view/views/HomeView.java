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
import org.springframework.stereotype.Component;

@Route("home")
@PageTitle("Home | DentClinicApp")
@Component
@AnonymousAllowed
public class HomeView extends VerticalLayout {

    public HomeView()
    {
        addClassName("home-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        Button patientButton = new Button("Patient");
        Button employeeButton = new Button("Employee");
        employeeButton.addClickListener(e ->
                employeeButton.getUI().ifPresent(ui ->
                        ui.navigate("/dentist"))
        );
        Button adminButton = new Button("Admin");
        patientButton.addThemeVariants(ButtonVariant.LUMO_LARGE);
        employeeButton.addThemeVariants(ButtonVariant.LUMO_LARGE);
        adminButton.addThemeVariants(ButtonVariant.LUMO_LARGE);
        add(new H1("DentClinicApp"));
        add(new H2("Welcome to our clinic!"));
        add(new Paragraph("Tell us who you are :)"));
        add(new Paragraph("I am a/an :"));
        HorizontalLayout buttonsLayout = new HorizontalLayout(patientButton, employeeButton, adminButton);
        addAndExpand(buttonsLayout);
    }
}
