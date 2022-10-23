package com.dentclinic.view.dentclinic_view.views;

import com.dentclinic.view.dentclinic_view.domain.Appointment;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("dentist")
public class DentistView extends VerticalLayout
{
    private Grid<Appointment> grid = new Grid<>(Appointment.class);

    public DentistView() {
        grid.setColumns("name", "surname", "pesel", "email", "date", "service");
        add(grid);
        setSizeFull();
        refresh();
    }

    public void refresh() {
//        grid.setItems(bookService.getBooks());
    }
}
