package com.dentclinic.view.dentclinic_view.service;

import com.dentclinic.view.dentclinic_view.client.DentistClient;
import com.dentclinic.view.dentclinic_view.domain.Dentist;
import com.dentclinic.view.dentclinic_view.domain.DentistDto;
import com.dentclinic.view.dentclinic_view.mapper.DentistMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DentistService {

    private final DentistClient client;
    private final DentistMapper mapper;

    public List<Dentist> fetchAllDentists() {
        List<DentistDto> dtoList = client.getDentists();
        return mapper.mapToDentistList(dtoList);
    }
}