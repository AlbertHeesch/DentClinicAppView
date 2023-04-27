package com.dentclinic.view.dentclinic_view.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Clinic {
    private Long id;
    private  String name;
    private Address address;
    private List<Dentist> dentists;
}
