package com.dentclinic.view.dentclinic_view.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class HomeLayout extends AppLayout {

    private Tab bookTab = new Tab("Book");
    private Tab updateTab = new Tab("Update");
    private Tabs tabs = new Tabs(bookTab, updateTab);

    public HomeLayout() {
        H1 title = new H1("DentClinicApp");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("left", "var(--lumo-space-l)")
                .set("margin", "0")
                .set("position", "sticky")
                .set("color", "white");

        bookTab.getStyle().set("color", "white");
        updateTab.getStyle().set("color", "white");

        HorizontalLayout itemsLayout = new HorizontalLayout(title, tabs);
        itemsLayout.setWidth("80%");
        itemsLayout.setAlignSelf(FlexComponent.Alignment.CENTER, title);
        itemsLayout.setAlignSelf(FlexComponent.Alignment.CENTER, tabs);
        itemsLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        HorizontalLayout topLayout1 = new HorizontalLayout(itemsLayout);
        topLayout1.getStyle().set("background", "#282A35");
        topLayout1.setWidth("100%");
        topLayout1.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        Select<String> citySelect = new Select<>("1", "2", "3");
        citySelect.setPlaceholder("City");
        Select<String> dateSelect = new Select<>("1", "2", "3");
        dateSelect.setPlaceholder("Date");
        Select<String> timeSelect = new Select<>("1", "2", "3");
        timeSelect.setPlaceholder("Time");
        Select<String> filterSelect = new Select<>("1", "2", "3");
        filterSelect.setPlaceholder("Filter");

        HorizontalLayout selectItemsLayout = new HorizontalLayout(citySelect, dateSelect, timeSelect, filterSelect);
        selectItemsLayout.setWidth("80%");

        HorizontalLayout topLayout2 = new HorizontalLayout(selectItemsLayout);
        topLayout2.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        topLayout2.setSizeFull();
        topLayout2.getStyle()
                .set("background", "#E7E9EB")
                .set("padding", "2px 5px 2px 0px");

        VerticalLayout verticalLayout = new VerticalLayout(topLayout1, topLayout2);
        verticalLayout.setPadding(false);
        verticalLayout.setSpacing(false);

        addToNavbar(verticalLayout);
    }

    public Tab getBookTab() {
        return bookTab;
    }

    public Tab getUpdateTab() {
        return updateTab;
    }

    public Tabs getTabs() {
        return tabs;
    }
}