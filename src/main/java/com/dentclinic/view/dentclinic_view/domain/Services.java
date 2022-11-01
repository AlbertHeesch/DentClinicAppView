package com.dentclinic.view.dentclinic_view.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Services {
    private Long id;
    @NotEmpty
    private String description;
    @Positive
    private BigDecimal cost;
}
