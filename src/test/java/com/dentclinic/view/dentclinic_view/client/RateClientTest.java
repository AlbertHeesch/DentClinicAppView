package com.dentclinic.view.dentclinic_view.client;

import com.dentclinic.view.dentclinic_view.domain.RateDto;
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
class RateClientTest {

    @InjectMocks
    private RateClient client;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void getRatesTest() throws URISyntaxException {
        //Given
        RateDto rateDto1 = new RateDto(1L, "RateName1", 1.1);
        RateDto rateDto2 = new RateDto(2L, "RateName2", 2.2);
        RateDto rateDto3 = new RateDto(3L, "RateName3", 3.3);
        RateDto[] rates = new RateDto[3];
        rates[0] = rateDto1;
        rates[1] = rateDto2;
        rates[2] = rateDto3;

        URI uri = new URI("http://localhost:8080/appRates/v1/rate/");
        when(restTemplate.getForObject(uri, RateDto[].class)).thenReturn(rates);

        //When
        List<RateDto> fetchedRatesDto = client.getRates();

        //Then
        assertEquals(3, fetchedRatesDto.size());
        assertEquals(1L, fetchedRatesDto.get(0).getId());
        assertEquals(2L, fetchedRatesDto.get(1).getId());
        assertEquals(3L, fetchedRatesDto.get(2).getId());
        assertEquals("RateName1", fetchedRatesDto.get(0).getName());
        assertEquals("RateName2", fetchedRatesDto.get(1).getName());
        assertEquals("RateName3", fetchedRatesDto.get(2).getName());
        assertEquals(1.1, fetchedRatesDto.get(0).getValue());
        assertEquals(2.2, fetchedRatesDto.get(1).getValue());
        assertEquals(3.3, fetchedRatesDto.get(2).getValue());
    }

    @Test
    void shouldFetchEmptyList() throws URISyntaxException {
        //Given
        RateDto[] rateDtos = new RateDto[0];

        URI uri = new URI("http://localhost:8080/appRates/v1/rate/");
        when(restTemplate.getForObject(uri, RateDto[].class)).thenReturn(rateDtos);

        //When
        List<RateDto> fetchedRatesDto = client.getRates();

        //Then
        assertEquals(0, fetchedRatesDto.size());
    }
}