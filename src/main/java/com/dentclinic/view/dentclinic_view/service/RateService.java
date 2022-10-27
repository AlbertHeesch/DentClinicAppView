package com.dentclinic.view.dentclinic_view.service;

import com.dentclinic.view.dentclinic_view.client.RateClient;
import com.dentclinic.view.dentclinic_view.domain.Rate;
import com.dentclinic.view.dentclinic_view.domain.RateDto;
import com.dentclinic.view.dentclinic_view.mapper.RateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RateService {

    private final RateClient client;
    private final RateMapper mapper;

    public List<Rate> fetchAllRates() {
        List<RateDto> dtoList = client.getRates();
        return mapper.mapToRateList(dtoList);
    }
}