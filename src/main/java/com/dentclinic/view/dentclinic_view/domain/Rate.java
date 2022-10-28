package com.dentclinic.view.dentclinic_view.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Rate
{
    private Long id;
    @NotEmpty
    private String name;
    @Positive
    private BigDecimal value;
}