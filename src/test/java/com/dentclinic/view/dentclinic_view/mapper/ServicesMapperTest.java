package com.dentclinic.view.dentclinic_view.mapper;

import com.dentclinic.view.dentclinic_view.domain.Services;
import com.dentclinic.view.dentclinic_view.domain.ServicesDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServicesMapperTest {

    @Autowired
    private ServicesMapper mapper;

    @Test
    void mapToServicesTest() {
        //Given
        ServicesDto serviceDto = new ServicesDto(1L, "ServiceDescription", 1.1);

        //When
        Services service = mapper.mapToServices(serviceDto);

        //Then
        assertEquals(1L, service.getId());
        assertEquals("ServiceDescription", service.getDescription());
        assertEquals(new BigDecimal("1.1"), service.getCost());
    }

    @Test
    void mapToServicesDtoTest() {
        //Given
        Services service = new Services(1L, "ServiceDescription", new BigDecimal("1.1"));

        //When
        ServicesDto serviceDto = mapper.mapToServicesDto(service);

        //Then
        assertEquals(1L, serviceDto.getId());
        assertEquals("ServiceDescription", serviceDto.getDescription());
        assertEquals(1.1, serviceDto.getCost());
    }

    @Test
    void mapToServicesListTest() {
        //Given
        ServicesDto serviceDto1 = new ServicesDto(1L, "ServiceDescription1", 1.1);
        ServicesDto serviceDto2 = new ServicesDto(2L, "ServiceDescription2", 2.2);
        ServicesDto serviceDto3 = new ServicesDto(3L, "ServiceDescription3", 3.3);

        List<ServicesDto> servicesDtoList = List.of(serviceDto1, serviceDto2, serviceDto3);

        //When
        List<Services> servicesList = mapper.mapToServicesList(servicesDtoList);

        //Then
        assertEquals(3, servicesList.size());
        assertEquals(1L, servicesList.get(0).getId());
        assertEquals(2L, servicesList.get(1).getId());
        assertEquals(3L, servicesList.get(2).getId());
        assertEquals("ServiceDescription1", servicesList.get(0).getDescription());
        assertEquals("ServiceDescription2", servicesList.get(1).getDescription());
        assertEquals("ServiceDescription3", servicesList.get(2).getDescription());
        assertEquals(new BigDecimal("1.1"), servicesList.get(0).getCost());
        assertEquals(new BigDecimal("2.2"), servicesList.get(1).getCost());
        assertEquals(new BigDecimal("3.3"), servicesList.get(2).getCost());
    }
}