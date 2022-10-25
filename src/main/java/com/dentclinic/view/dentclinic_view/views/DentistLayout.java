package com.dentclinic.view.dentclinic_view.views;

import com.dentclinic.view.dentclinic_view.service.DentistService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class DentistLayout extends AppLayout {

    private DentistService api;

    public DentistLayout(@Autowired DentistService dentistService) {
        api = dentistService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("DentClinicApp");
        logo.addClassNames("text-l", "m-m");

        HorizontalLayout header = new HorizontalLayout(
                new DrawerToggle(),
                logo
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink allDentistsLink = new RouterLink("All dentists", DentistView.class);
        allDentistsLink.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(
                allDentistsLink
        ));

        List<RouterLink> dentistLinkList = api.fetchAllDentists().stream()
                                .map(dentist -> dentist.getName() + " " + dentist.getSurname())
                                        .map(name -> new RouterLink(name, DentistView.class))
                .collect(Collectors.toList());
        dentistLinkList.stream()
                        .forEach(link -> link.setHighlightCondition(HighlightConditions.sameLocation()));
        dentistLinkList.stream()
                        .forEach(e -> addToDrawer(new VerticalLayout(e)));
    }
}