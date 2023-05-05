package com.dentclinic.view.dentclinic_view.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;

public class HomeLayout extends AppLayout {

    public HomeLayout() {
        H1 title = new H1("DentClinicApp");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("left", "var(--lumo-space-l)")
                .set("margin", "0")
                .set("position", "sticky");

        Tab bookTab = new Tab("Book");
        Tab updateTab = new Tab("Update");

        Tabs tabs = new Tabs(bookTab, updateTab);
        tabs.addThemeVariants(TabsVariant.LUMO_CENTERED);

        HorizontalLayout layout = new HorizontalLayout(title, tabs);
        layout.setWidth("100%");

        layout.setAlignSelf(FlexComponent.Alignment.CENTER, title);
        layout.setAlignSelf(FlexComponent.Alignment.CENTER, tabs);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        addToNavbar(layout);
    }
}