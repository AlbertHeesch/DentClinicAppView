package com.dentclinic.view.dentclinic_view.views;

import com.dentclinic.view.dentclinic_view.layout.HomeLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.BigDecimalField;
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
    private BigDecimalField peselField = new BigDecimalField("PESEL");
    private Button findButton = new Button("Find");
    private Button backButton = new Button("Back");
    private Button saveButton = new Button("Save");
    private List<String> list = List.of("Day1", "Day2", "Day3", "Day4", "Day5");
    private HorizontalLayout backgroundUpdateLayout = new HorizontalLayout(surnameField, peselField, findButton);

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

        addListeners();
    }

    private void setContent(Tab tab) {
        removeAll();

        //TODO oops something went wrong with if statement
        if(tab.equals(homeLayout.getBookTab())) {
            add(h2Layout);
            for(int i=0; i<10; i++) {
                Label sampleLabel = new Label("Name Surname" + i);
                sampleLabel.getStyle().set("color", "white");

                VerticalLayout dataLayout = new VerticalLayout(sampleLabel);
                dataLayout.getStyle()
                        .set("background-color", "#00c3a5")
                        .set("border-radius", "25px");
                dataLayout.setMinWidth("400px");
                dataLayout.setHeight("100%");

                HorizontalLayout horizontalLayout = new HorizontalLayout(dataLayout);
                horizontalLayout.setMinWidth("1100px");
                horizontalLayout.setHeight("320px");
                horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
                horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
                horizontalLayout.getStyle()
                        .set("background", "#E7E9EB")
                        .set("border-radius", "25px");

                VerticalLayout buttonsGroupLayout = new VerticalLayout();
                buttonsGroupLayout.setAlignItems(FlexComponent.Alignment.CENTER);
                buttonsGroupLayout.setWidth("60%");
                buttonsGroupLayout.setHeight("90%");
                buttonsGroupLayout.setSpacing(false);
                buttonsGroupLayout.setPadding(false);

                HorizontalLayout buttonsLayout = new HorizontalLayout();
                buttonsLayout.setWidth("100%");
                buttonsLayout.setHeight("85%");
                buttonsLayout.setJustifyContentMode(JustifyContentMode.CENTER);
                buttonsLayout.getStyle().set("padding", "5px 3px 5px 3px");

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
                timeButton.addClickListener(e -> {
                   if(timeButton.getText().equals("More")){
                       timeButton.setText("Less");
                       horizontalLayout.setHeight("450px");
                       leftButton.setMinHeight("325px");
                       rightButton.setMinHeight("325px");
                       buttonsLayout.removeAll();
                       buttonsLayout.add(leftButton);

                       for(int j=0; j<list.size(); j++) {
                           VerticalLayout dateButtonsLayout = new VerticalLayout(new Label(LocalDate.now().plusDays(j).getDayOfMonth() + "\n" + String.valueOf(LocalDate.now().getMonth()).substring(0,3)));
                           for(int x=0; x<7; x++) {
                               Button bookButton = new Button("Book");
                               dateButtonsLayout.add(bookButton);
                           }
                           dateButtonsLayout.setJustifyContentMode(JustifyContentMode.CENTER);
                           dateButtonsLayout.setAlignItems(FlexComponent.Alignment.CENTER);
                           dateButtonsLayout.setSpacing(false);
                           dateButtonsLayout.setWidth("12%");
                           dateButtonsLayout.setHeight("100%");
                           buttonsLayout.add(dateButtonsLayout);
                           buttonsLayout.add(rightButton);
                       }
                   } else {
                       timeButton.setText("More");
                       horizontalLayout.setHeight("340px");
                       leftButton.setMinHeight("220px");
                       rightButton.setMinHeight("220px");
                       buttonsLayout.removeAll();
                       buttonsLayout.add(leftButton);

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
                           buttonsLayout.add(rightButton);
                       }
                   }
                });
                buttonsGroupLayout.add(buttonsLayout, timeButton);
                horizontalLayout.add(buttonsGroupLayout);
                HorizontalLayout backgroundLayout = new HorizontalLayout(horizontalLayout);
                backgroundLayout.setWidth("100%");
                add(backgroundLayout);
            }
        } else {
            backgroundUpdateLayout.setWidth("100%");
            backgroundUpdateLayout.setAlignItems(Alignment.BASELINE);
            backgroundUpdateLayout.setJustifyContentMode(JustifyContentMode.CENTER);
            setJustifyContentMode(JustifyContentMode.CENTER);
            setWidth("100%");
            add(backgroundUpdateLayout);
        }
    }

    private void addListeners() {
        findButton.addClickListener(e -> {
            backgroundUpdateLayout.removeAll();
            backgroundUpdateLayout.add(saveButton, backButton);
        });
        saveButton.addClickListener(e -> {
            backgroundUpdateLayout.removeAll();
            backgroundUpdateLayout.add(surnameField, peselField, findButton);
            Notification notification = Notification
                    .show("Changes have been saved.");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            surnameField.clear();
            peselField.clear();
        });
        backButton.addClickListener(e -> {
            backgroundUpdateLayout.removeAll();
            backgroundUpdateLayout.add(surnameField, peselField, findButton);
            surnameField.clear();
            peselField.clear();
        });
    }
}