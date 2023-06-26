package com.dentclinic.view.dentclinic_view.views;

import com.dentclinic.view.dentclinic_view.layout.HomeLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
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

import java.time.LocalDate;
import java.util.*;

@Component
@UIScope
@PageTitle("Home | DentClinicApp")
@Route(value="home", layout = HomeLayout.class)
@AnonymousAllowed
public class HomeView extends VerticalLayout {

    private HomeLayout homeLayout;
    private H2 specialistsLabel = new H2("Our specialists");
    private HorizontalLayout h2Layout = new HorizontalLayout(specialistsLabel);
    private TextField surnameField = new TextField("Surname");
    private TextField peselField = new TextField("PESEL");
    private Button findButton = new Button("Find");
    private List<String> list = List.of("Day1", "Day2", "Day3", "Day4", "Day5");

    public HomeView( @Autowired HomeLayout homeLayout)
    {
        this.homeLayout = homeLayout;
        addClassName("home-view");
        setMinHeight("100%");
        setHeight("full");
        setWidth("80%");
        getStyle().set("background-color", "white")
                .set("padding", "0px 10% 5% 10%");

        specialistsLabel.getStyle().set("font-weight", "bold");

        h2Layout.setPadding(false);
        h2Layout.setWidth("100%");
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
                Label sampleLabel = new Label("Name Surname" + i);
                sampleLabel.getStyle().set("color", "white");

                VerticalLayout dataLayout = new VerticalLayout(sampleLabel);
                dataLayout.getStyle()
                        .set("background-color", "#00c3a5")
                        .set("border-radius", "25px");
                dataLayout.setMinWidth("400px");

                HorizontalLayout horizontalLayout = new HorizontalLayout(dataLayout);
                horizontalLayout.setMinWidth("570px");
                horizontalLayout.setMinHeight("280px");
                horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
                horizontalLayout.getStyle()
                        .set("background", "#E7E9EB")
                        .set("border-radius", "25px");

                VerticalLayout buttonsGroupLayout = new VerticalLayout();
                buttonsGroupLayout.setAlignItems(FlexComponent.Alignment.CENTER);
                buttonsGroupLayout.setWidth("60%");
                buttonsGroupLayout.setHeight("100%");
                buttonsGroupLayout.setSpacing(false);

                HorizontalLayout buttonsLayout = new HorizontalLayout();
                buttonsLayout.setWidth("100%");
                buttonsLayout.setHeight("85%");
                buttonsLayout.setJustifyContentMode(JustifyContentMode.CENTER);
                buttonsLayout.getStyle().set("padding", "5px 3px 5 px 3px");

                Button leftButton = new Button("<");
                leftButton.setMinHeight("220px");
                leftButton.setWidth("5%");
                buttonsLayout.add(leftButton);
                leftButton.setEnabled(false);

                for(int j=0; j<list.size(); j++) {
                    VerticalLayout dateButtonsLayout = new VerticalLayout(new Label(LocalDate.now().plusDays(j).getDayOfMonth() + "\n" + String.valueOf(LocalDate.now().getMonth()).substring(0,3)));
                    for(int x=0; x<4; x++) {
                        Button bookButton = new Button("Book");
                        dateButtonsLayout.add(bookButton);
                    }
                    dateButtonsLayout.setJustifyContentMode(JustifyContentMode.CENTER);
                    dateButtonsLayout.setAlignItems(FlexComponent.Alignment.CENTER);
                    dateButtonsLayout.setSpacing(false);
                    dateButtonsLayout.setWidth("12%");
                    dateButtonsLayout.setHeight("100%");
                    buttonsLayout.add(dateButtonsLayout);
                }
                Button rightButton = new Button(">");
                rightButton.setMinHeight("220px");
                buttonsLayout.add(rightButton);
                Button timeButton = new Button("More");
                timeButton.setWidth("95%");
                buttonsGroupLayout.add(buttonsLayout, timeButton);
                horizontalLayout.add(buttonsGroupLayout);

                add(horizontalLayout);
            }
        } else {
            add(surnameField, peselField, findButton);
        }
    }
}