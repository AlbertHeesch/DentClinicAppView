package com.dentclinic.view.dentclinic_view.client;

import com.dentclinic.view.dentclinic_view.domain.ServicesDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicesClientTest {

    @InjectMocks
    private ServicesClient client;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void getServicesTest() throws URISyntaxException {
        //Given
        ServicesDto servicesDto1 = new ServicesDto(1L, "ServiceDescription1", 1.1);
        ServicesDto servicesDto2 = new ServicesDto(2L, "ServiceDescription2", 2.2);
        ServicesDto servicesDto3 = new ServicesDto(3L, "ServiceDescription3", 3.3);
        ServicesDto[] services = new ServicesDto[3];
        services[0] = servicesDto1;
        services[1] = servicesDto2;
        services[2] = servicesDto3;

        URI uri = new URI("http://localhost:8080/app/v1/service/");
        when(restTemplate.getForObject(uri, ServicesDto[].class)).thenReturn(services);

        //When
        List<ServicesDto> fetchedServicesDto = client.getServices();

        //Then
        assertEquals(3, fetchedServicesDto.size());
        assertEquals(1L, fetchedServicesDto.get(0).getId());
        assertEquals(2L, fetchedServicesDto.get(1).getId());
        assertEquals(3L, fetchedServicesDto.get(2).getId());
        assertEquals("ServiceDescription1", fetchedServicesDto.get(0).getDescription());
        assertEquals("ServiceDescription2", fetchedServicesDto.get(1).getDescription());
        assertEquals("ServiceDescription3", fetchedServicesDto.get(2).getDescription());
        assertEquals(1.1, fetchedServicesDto.get(0).getCost());
        assertEquals(2.2, fetchedServicesDto.get(1).getCost());
        assertEquals(3.3, fetchedServicesDto.get(2).getCost());
    }

    @Test
    void shouldFetchEmptyList() throws URISyntaxException {
        //Given
        ServicesDto[] services = new ServicesDto[0];

        URI uri = new URI("http://localhost:8080/app/v1/service/");
        when(restTemplate.getForObject(uri, ServicesDto[].class)).thenReturn(services);

        //When
        List<ServicesDto> fetchedServicesDto = client.getServices();

        //Then
        assertEquals(0, fetchedServicesDto.size());
    }
}