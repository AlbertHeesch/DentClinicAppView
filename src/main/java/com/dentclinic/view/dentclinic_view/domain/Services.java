package com.dentclinic.view.dentclinic_view.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Services {
    private Long id;
    private String description;
    private Double cost;
    private List<Appointment> appointmentList = new ArrayList<>();
}
