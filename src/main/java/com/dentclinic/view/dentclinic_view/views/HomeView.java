package com.dentclinic.view.dentclinic_view.views;

import com.dentclinic.view.dentclinic_view.layout.HomeLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
@PageTitle("Home | DentClinicApp")
@Route(value="home", layout = HomeLayout.class)
@AnonymousAllowed
public class HomeView extends VerticalLayout {

    private HomeLayout homeLayout;
    private Label specialistsLabel = new Label("Our specialists");
    private VerticalLayout h2Layout = new VerticalLayout(specialistsLabel);
    private TextField surnameField = new TextField("Surname");
    private TextField peselField = new TextField("PESEL");
    private Button findButton = new Button("Find");

    public HomeView( @Autowired HomeLayout homeLayout)
    {
        this.homeLayout = homeLayout;
        addClassName("home-view");
        setMinHeight("100%");
        setHeight("full");
        setWidth("100%");
        setAlignItems(Alignment.CENTER);
        getStyle().set("background-color", "blue");

        specialistsLabel.getStyle().set("font-weight", "bold");

        h2Layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        h2Layout.setPadding(false);
        h2Layout.getStyle().set("background-color", "orange");
        h2Layout.setWidth("81%");
        h2Layout.setHeight("4%");

        setContent(homeLayout.getTabs().getSelectedTab());
        homeLayout.getTabs().addSelectedChangeListener(event -> setContent(event.getSelectedTab()));
    }

    private void setContent(Tab tab) {
        removeAll();
        //TODO oops something went wrong with if statement
        if(tab.equals(homeLayout.getBookTab())) {
            add(h2Layout);
            for(int i=0; i<100; i++) {
                Label sampleLabel = new Label("Sample Label" + i);
                Button bookButton = new Button("Book");
                HorizontalLayout horizontalLayout = new HorizontalLayout(sampleLabel, bookButton);
                horizontalLayout.setWidth("81%");
                horizontalLayout.setHeight("100%");
//                ho.setAlignItems(FlexComponent.Alignment.CENTER);
                horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
                if(i%2 == 0){
                    horizontalLayout.getStyle().set("background-color", "green");
                } else {
                    horizontalLayout.getStyle().set("background-color", "pink");
                }
                add(horizontalLayout);
            }
        } else {
            add(surnameField, peselField, findButton);
        }
    }
}
