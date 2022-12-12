package com.dentclinic.view.dentclinic_view.service;

import com.dentclinic.view.dentclinic_view.client.RateClient;
import com.dentclinic.view.dentclinic_view.domain.Rate;
import com.dentclinic.view.dentclinic_view.domain.RateDto;
import com.dentclinic.view.dentclinic_view.mapper.RateMapper;
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
class RateServiceTest {

    @InjectMocks
    private RateService service;

    @Mock
    private RateClient client;

    @Mock
    private RateMapper mapper;

    @Test
    void shouldFetchEmptyRateList() {
        //Given
        List<RateDto> emptyDtoList = Collections.emptyList();
        when(client.getRates()).thenReturn(emptyDtoList);

        List<Rate> emptyList = Collections.emptyList();
        when(mapper.mapToRateList(any(List.class))).thenReturn(emptyList);

        //When
        List<Rate> fetchedList = service.fetchAllRates();

        //Then
        assertEquals(0, fetchedList.size());
    }

    @Test
    void fetchAllRatesTest() {
        //Given
        RateDto rateDto1 = new RateDto(1L, "RateName1", 1.1);
        RateDto rateDto2 = new RateDto(2L, "RateName2", 2.2);
        RateDto rateDto3 = new RateDto(3L, "RateName3", 3.3);
        List<RateDto> rateDtoList = List.of(rateDto1, rateDto2, rateDto3);
        when(client.getRates()).thenReturn(rateDtoList);

        Rate rate1 = new Rate(1L, "RateName1", new BigDecimal("1.1"));
        Rate rate2 = new Rate(2L, "RateName2", new BigDecimal("2.2"));
        Rate rate3 = new Rate(3L, "RateName3", new BigDecimal("3.3"));
        List<Rate> rateList = List.of(rate1, rate2, rate3);
        when(mapper.mapToRateList(any(List.class))).thenReturn(rateList);

        //When
        List<Rate> fetchedList = service.fetchAllRates();

        //Then
        assertEquals(3, fetchedList.size());
        assertEquals(1L, fetchedList.get(0).getId());
        assertEquals(2L, fetchedList.get(1).getId());
        assertEquals(3L, fetchedList.get(2).getId());
        assertEquals("RateName1", fetchedList.get(0).getName());
        assertEquals("RateName2", fetchedList.get(1).getName());
        assertEquals("RateName3", fetchedList.get(2).getName());
        assertEquals(new BigDecimal("1.1"), fetchedList.get(0).getValue());
        assertEquals(new BigDecimal("2.2"), fetchedList.get(1).getValue());
        assertEquals(new BigDecimal("3.3"), fetchedList.get(2).getValue());
    }
}