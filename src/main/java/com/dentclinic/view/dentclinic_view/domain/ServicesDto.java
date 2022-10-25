package com.dentclinic.view.dentclinic_view.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ServicesDto {
    private Long id;
    private String description;
    private Double cost;
}