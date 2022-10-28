package com.dentclinic.view.dentclinic_view.mapper;

import com.dentclinic.view.dentclinic_view.domain.Rate;
import com.dentclinic.view.dentclinic_view.domain.RateDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateMapper {
    public Rate mapToRate(final RateDto rateDto)
    {
        return new Rate(
                rateDto.getId(),
                rateDto.getName(),
                BigDecimal.valueOf(rateDto.getValue())
        );
    }

    public RateDto mapToRateDto(final Rate rate)
    {
        return new RateDto(
                rate.getId(),
                rate.getName(),
                rate.getValue().doubleValue()
        );
    }

    public List<Rate> mapToRateList(final List<RateDto> rateDtoList)
    {
        return rateDtoList.stream()
                .map(this::mapToRate)
                .collect(Collectors.toList());
    }
}