package com.dentclinic.view.dentclinic_view.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentDto {
    private Long id;
    private String name;
    private String surname;
    private String pesel;
    private String email;
    private LocalDateTime date;
    private Long dentistId;
    private Long serviceId;
}
