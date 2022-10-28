package com.dentclinic.view.dentclinic_view.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Dentist {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @Past
    private LocalDate experience;
}
