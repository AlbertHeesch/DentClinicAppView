package com.dentclinic.view.dentclinic_view.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Dentist {
    private Long id;
    private String name;
    private String surname;
    private LocalDate experience;
    private List<Appointment> appointmentList = new ArrayList<>();
}
