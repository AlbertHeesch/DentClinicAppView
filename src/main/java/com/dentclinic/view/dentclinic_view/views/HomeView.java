package com.dentclinic.view.dentclinic_view.views;

import com.dentclinic.view.dentclinic_view.layout.HomeLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Home | DentClinicApp")
@Route(value="home", layout = HomeLayout.class)
@AnonymousAllowed
public class HomeView extends VerticalLayout {

    public HomeView()
    {
        addClassName("home-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        HorizontalLayout topLayout = new HorizontalLayout();
        for(int i =0; i<50; i++) {
            add(new HorizontalLayout((new Label("SAMPLEEEE\n"))));
        }
        add(createTopLayout());
    }

    private HorizontalLayout createTopLayout() {
      HorizontalLayout topLayout = new HorizontalLayout();

        topLayout.setHeight("40px");

        return topLayout;
    }
}
