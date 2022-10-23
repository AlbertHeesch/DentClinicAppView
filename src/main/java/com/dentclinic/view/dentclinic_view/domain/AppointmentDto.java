package com.dentclinic.view.dentclinic_view.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class AppointmentDto {
    private Long id;
    private String name;
    private String surname;
    private String pesel;
    private String email;
    private LocalDate date;
    private Dentist dentist;
    private Services service;
}
