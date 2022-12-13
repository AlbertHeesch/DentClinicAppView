package com.dentclinic.view.dentclinic_view.mapper;

import com.dentclinic.view.dentclinic_view.domain.Rate;
import com.dentclinic.view.dentclinic_view.domain.RateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RateMapperTest {

    @Autowired
    private RateMapper mapper;

    @Test
    void mapToRateTest() {
        //Given
        RateDto rateDto = new RateDto(1L, "RateName", 1.1);

        //When
        Rate rate = mapper.mapToRate(rateDto);

        //Then
        assertEquals(1L, rate.getId());
        assertEquals("RateName", rate.getName());
        assertEquals(new BigDecimal("1.1"), rate.getValue());
    }

    @Test
    void mapToRateDtoTest() {
        Rate rate = new Rate(1L, "RateName", new BigDecimal("1.1"));

        //When
        RateDto rateDto = mapper.mapToRateDto(rate);

        //Then
        assertEquals(1L, rateDto.getId());
        assertEquals("RateName", rateDto.getName());
        assertEquals(1.1, rateDto.getValue());
    }

    @Test
    void mapToRateListTest() {
        //Given
        RateDto rateDto1 = new RateDto(1L, "RateName1", 1.1);
        RateDto rateDto2 = new RateDto(2L, "RateName2", 2.2);
        RateDto rateDto3 = new RateDto(3L, "RateName3", 3.3);

        List<RateDto> rateDtoList = List.of(rateDto1, rateDto2, rateDto3);

        //When
        List<Rate> rateList = mapper.mapToRateList(rateDtoList);

        //Then
        assertEquals(3, rateList.size());
        assertEquals(1L, rateList.get(0).getId());
        assertEquals(2L, rateList.get(1).getId());
        assertEquals(3L, rateList.get(2).getId());
        assertEquals("RateName1", rateList.get(0).getName());
        assertEquals("RateName2", rateList.get(1).getName());
        assertEquals("RateName3", rateList.get(2).getName());
        assertEquals(new BigDecimal("1.1"), rateList.get(0).getValue());
        assertEquals(new BigDecimal("2.2"), rateList.get(1).getValue());
        assertEquals(new BigDecimal("3.3"), rateList.get(2).getValue());
    }
}