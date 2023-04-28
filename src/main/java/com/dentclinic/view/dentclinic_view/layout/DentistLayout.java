package com.dentclinic.view.dentclinic_view.layout;

import com.dentclinic.view.dentclinic_view.security.SecurityService;
import com.dentclinic.view.dentclinic_view.service.DentistService;
import com.dentclinic.view.dentclinic_view.views.dentist.DentistOneView;
import com.dentclinic.view.dentclinic_view.views.dentist.DentistThreeView;
import com.dentclinic.view.dentclinic_view.views.dentist.DentistTwoView;
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

public class DentistLayout extends AppLayout {

    private final DentistService api;
    private final SecurityService securityService;

    public DentistLayout(@Autowired DentistService dentistService, SecurityService securityService) {
        api = dentistService;
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 appName = new H1("DentClinicApp");
        appName.addClassNames("text-l", "m-m");

        Button logout = new Button("Log out", e -> securityService.logout());
        Button homeButton = new Button("Home");
        homeButton.addClickListener(e ->
                homeButton.getUI().ifPresent(ui ->
                        ui.navigate("/home"))
        );
        HorizontalLayout header = new HorizontalLayout(
                new DrawerToggle(),
                appName,
                homeButton,
                logout
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(appName);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");
        header.setHeight("100px");

        addToNavbar(header);

    }

    private void createDrawer() {
        if(api.fetchAllDentists().size() == 0)
        {
            RouterLink noAppointmentsLink = new RouterLink("No dentists found", AllDentistView.class);
            noAppointmentsLink.setHighlightCondition(HighlightConditions.sameLocation());

            addToDrawer(new VerticalLayout(noAppointmentsLink));
        } else {
            RouterLink allDentistsLink = new RouterLink("All dentists", AllDentistView.class);
            allDentistsLink.setHighlightCondition(HighlightConditions.sameLocation());

            RouterLink dentist1Link = new RouterLink(api.fetchAllDentists().get(0).getName() + " "
                    + api.fetchAllDentists().get(0).getSurname(), DentistOneView.class);
            allDentistsLink.setHighlightCondition(HighlightConditions.sameLocation());

            RouterLink dentist2Link = new RouterLink(api.fetchAllDentists().get(1).getName() + " "
                    + api.fetchAllDentists().get(1).getSurname(), DentistTwoView.class);
            allDentistsLink.setHighlightCondition(HighlightConditions.sameLocation());

            RouterLink dentist3Link = new RouterLink(api.fetchAllDentists().get(2).getName() + " "
                    + api.fetchAllDentists().get(2).getSurname(), DentistThreeView.class);
            allDentistsLink.setHighlightCondition(HighlightConditions.sameLocation());

            addToDrawer(new VerticalLayout(
                    allDentistsLink,
                    dentist1Link,
                    dentist2Link,
                    dentist3Link
            ));
        }
    }
}