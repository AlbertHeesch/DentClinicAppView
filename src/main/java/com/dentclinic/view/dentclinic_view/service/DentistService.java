package com.dentclinic.view.dentclinic_view.service;

import com.dentclinic.view.dentclinic_view.client.DentistClient;
import com.dentclinic.view.dentclinic_view.domain.DentistDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DentistService {

    private final DentistClient client;

    public List<DentistDto> fetchAllDentists() {
        return client.getDentists();
    }
}