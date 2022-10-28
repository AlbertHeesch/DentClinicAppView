package com.dentclinic.view.dentclinic_view.service;

import com.dentclinic.view.dentclinic_view.client.ServicesClient;
import com.dentclinic.view.dentclinic_view.domain.Services;
import com.dentclinic.view.dentclinic_view.domain.ServicesDto;
import com.dentclinic.view.dentclinic_view.mapper.ServicesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicesService {

    private final ServicesClient client;
    private final ServicesMapper mapper;

    public List<Services> fetchAllServices() {
        List<ServicesDto> dtoList = client.getServices();
        return mapper.mapToServicesList(dtoList);
    }

    public void deleteService(Services services) {
        client.deleteService(mapper.mapToServicesDto(services));
    }

    public void saveService(Services services) {
        if (services == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
        } else {
            client.addService(mapper.mapToServicesDto(services));
        }
    }

    public void updateService(Services services) {
        client.updateService(mapper.mapToServicesDto(services));
    }
}