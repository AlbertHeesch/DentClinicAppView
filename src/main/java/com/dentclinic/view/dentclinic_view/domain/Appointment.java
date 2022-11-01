package com.dentclinic.view.dentclinic_view.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Appointment {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @PositiveOrZero
    private BigDecimal pesel;
    @NotEmpty
    private String email;
    @Future
    private LocalDateTime date;
    private Dentist dentist;
    private Services service;
}
