package com.dentclinic.view.dentclinic_view.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
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
    @NotNull
    private BigDecimal pesel;
    @NotEmpty
    @Email
    private String email;
    @NotNull
    private LocalDateTime date;
    @NotNull
    private Dentist dentist;
    @NotNull
    private Services service;
}
