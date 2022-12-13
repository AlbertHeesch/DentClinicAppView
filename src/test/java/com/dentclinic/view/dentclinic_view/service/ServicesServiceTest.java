package com.dentclinic.view.dentclinic_view.service;

import com.dentclinic.view.dentclinic_view.client.ServicesClient;
import com.dentclinic.view.dentclinic_view.domain.Services;
import com.dentclinic.view.dentclinic_view.domain.ServicesDto;
import com.dentclinic.view.dentclinic_view.mapper.ServicesMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicesServiceTest {

    @InjectMocks
    private ServicesService service;

    @Mock
    private ServicesClient client;

    @Mock
    private ServicesMapper mapper;

    @Test
    void shouldFetchEmptyServiceList() {
        //Given
        List<ServicesDto> emptyDtoList = Collections.emptyList();
        when(client.getServices()).thenReturn(emptyDtoList);

        List<Services> emptyList = Collections.emptyList();
        when(mapper.mapToServicesList(any(List.class))).thenReturn(emptyList);

        //When
        List<Services> fetchedList = service.fetchAllServices();

        //Then
        assertEquals(0, fetchedList.size());
    }

    @Test
    void fetchAllServicesTest() {
        //Given
        ServicesDto servicesDto1 = new ServicesDto(1L, "ServiceDescription1", 1.1);
        ServicesDto servicesDto2 = new ServicesDto(2L, "ServiceDescription2", 2.2);
        ServicesDto servicesDto3 = new ServicesDto(3L, "ServiceDescription3", 3.3);
        List<ServicesDto> servicesDtoList = List.of(servicesDto1, servicesDto2, servicesDto3);
        when(client.getServices()).thenReturn(servicesDtoList);

        Services services1 = new Services(1L, "ServiceDescription1", new BigDecimal("1.1"));
        Services services2 = new Services(2L, "ServiceDescription2", new BigDecimal("2.2"));
        Services services3 = new Services(3L, "ServiceDescription3", new BigDecimal("3.3"));
        List<Services> servicesList = List.of(services1, services2, services3);
        when(mapper.mapToServicesList(any(List.class))).thenReturn(servicesList);

        //When
        List<Services> fetchedList = service.fetchAllServices();

        //Then
        assertEquals(3, fetchedList.size());
        assertEquals(1L, fetchedList.get(0).getId());
        assertEquals(2L, fetchedList.get(1).getId());
        assertEquals(3L, fetchedList.get(2).getId());
        assertEquals("ServiceDescription1", fetchedList.get(0).getDescription());
        assertEquals("ServiceDescription2", fetchedList.get(1).getDescription());
        assertEquals("ServiceDescription3", fetchedList.get(2).getDescription());
        assertEquals(new BigDecimal("1.1"), fetchedList.get(0).getCost());
        assertEquals(new BigDecimal("2.2"), fetchedList.get(1).getCost());
        assertEquals(new BigDecimal("3.3"), fetchedList.get(2).getCost());
    }
}