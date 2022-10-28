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

    public void deleteRate(Rate rate) {
        client.deleteRate(mapper.mapToRateDto(rate));
    }

    public void saveRate(Rate rate) {
        if (rate == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
        } else {
            client.addRate(mapper.mapToRateDto(rate));
        }
    }

    public void updateRate(Rate rate) {
        client.updateRate(mapper.mapToRateDto(rate));
    }
}