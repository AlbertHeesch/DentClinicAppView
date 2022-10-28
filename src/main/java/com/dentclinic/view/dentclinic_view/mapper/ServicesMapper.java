package com.dentclinic.view.dentclinic_view.mapper;

import com.dentclinic.view.dentclinic_view.domain.Services;
import com.dentclinic.view.dentclinic_view.domain.ServicesDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicesMapper {

    public Services mapToServices(final ServicesDto servicesDto) {
        return new Services(
                servicesDto.getId(),
                servicesDto.getDescription(),
                BigDecimal.valueOf(servicesDto.getCost())
        );
    }

    public ServicesDto mapToServicesDto(final Services services)
    {
        return new ServicesDto(
                services.getId(),
                services.getDescription(),
                services.getCost().doubleValue()
        );
    }

    public List<Services> mapToServicesList(final List<ServicesDto> servicesDtoList)
    {
        return servicesDtoList.stream()
                .map(this::mapToServices)
                .collect(Collectors.toList());
    }
}