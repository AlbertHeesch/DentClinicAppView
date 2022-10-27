package com.dentclinic.view.dentclinic_view.layout;

import com.dentclinic.view.dentclinic_view.security.SecurityService;
import com.dentclinic.view.dentclinic_view.service.DentistService;
import com.dentclinic.view.dentclinic_view.views.admin.AdminAppointmentsView;
import com.dentclinic.view.dentclinic_view.views.admin.AdminDentistsView;
import com.dentclinic.view.dentclinic_view.views.admin.AdminRatesView;
import com.dentclinic.view.dentclinic_view.views.admin.AdminServicesView;
import com.dentclinic.view.dentclinic_view.views.dentist.AllDentistView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminLayout extends AppLayout {

    private final DentistService api;
    private final SecurityService securityService;

    public AdminLayout(@Autowired DentistService dentistService, SecurityService securityService) {
        api = dentistService;
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("DentClinicApp");
        logo.addClassNames("text-l", "m-m");

        Button logout = new Button("Log out", e -> securityService.logout());
        Button homeButton = new Button("Home");
        homeButton.addClickListener(e ->
                homeButton.getUI().ifPresent(ui ->
                        ui.navigate("/home"))
        );
        HorizontalLayout header = new HorizontalLayout(
                new DrawerToggle(),
                logo,
                homeButton,
                logout
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink adminAppointmentsLink = new RouterLink("Appointments", AdminAppointmentsView.class);
        adminAppointmentsLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink adminDentistsLink = new RouterLink("Dentists", AdminDentistsView.class);
        adminDentistsLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink adminServicesLink = new RouterLink("Services", AdminServicesView.class);
        adminServicesLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink adminRatesLink = new RouterLink("Rates", AdminRatesView.class);
        adminRatesLink.setHighlightCondition(HighlightConditions.sameLocation());

            addToDrawer(new VerticalLayout(
                    adminAppointmentsLink,
                    adminDentistsLink,
                    adminServicesLink,
                    adminRatesLink
            ));
    }
}