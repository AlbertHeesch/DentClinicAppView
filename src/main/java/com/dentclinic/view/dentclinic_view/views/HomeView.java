package com.dentclinic.view.dentclinic_view.views;

import com.dentclinic.view.dentclinic_view.domain.Dentist;
import com.dentclinic.view.dentclinic_view.layout.HomeLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@UIScope
@PageTitle("Home | DentClinicApp")
@Route(value="home", layout = HomeLayout.class)
@AnonymousAllowed
public class HomeView extends VerticalLayout {

    private HomeLayout homeLayout;
    private VirtualList<Dentist> virtualList = new VirtualList<>();
    private VerticalLayout listLayout = new VerticalLayout(virtualList);
    private Label specialistsLabel = new Label("Our specialists");
    private VerticalLayout h2Layout = new VerticalLayout(specialistsLabel);

    public HomeView( @Autowired HomeLayout homeLayout)
    {
        this.homeLayout = homeLayout;
        addClassName("home-view");
        setHeight("100%");
        setWidth("100%");
        setAlignItems(Alignment.CENTER);
        getStyle().set("background-color", "blue");

        specialistsLabel.getStyle().set("font-weight", "bold");

        h2Layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        h2Layout.setPadding(false);
        h2Layout.getStyle().set("background-color", "orange");
        h2Layout.setWidth("81%");
        h2Layout.setHeight("4%");

        List<Dentist> dentistList = new ArrayList<>();
        for(int i=0; i<100; i++) {
            dentistList.add(new Dentist(1L, "Name1", "Surname1", LocalDate.of(2000, 1, 1)));
        }
        virtualList.setItems(dentistList);
        listLayout.setWidth("81%");
        listLayout.setHeight("100%");
        listLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        listLayout.getStyle().set("background-color", "green");

        setContent(homeLayout.getTabs().getSelectedTab());
        homeLayout.getTabs().addSelectedChangeListener(event -> setContent(event.getSelectedTab()));
    }

    private void setContent(Tab tab) {
        removeAll();

        if(tab.equals(homeLayout.getBookTab())) {
            add(h2Layout, listLayout);
        } else {
            //TODO update functionality
        }
    }
}
