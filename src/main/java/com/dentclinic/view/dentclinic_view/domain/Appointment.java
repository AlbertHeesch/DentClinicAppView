package com.dentclinic.view.dentclinic_view.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Appointment {
    private Long id;
    private String name;
    private String surname;
    private String pesel;
    private String email;
    private LocalDate date;
    private Dentist dentist;
    private Services service;
}
