package com.dentclinic.view.dentclinic_view.mapper;

import com.dentclinic.view.dentclinic_view.domain.Dentist;
import com.dentclinic.view.dentclinic_view.domain.DentistDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DentistMapperTest {

    @Autowired
    private DentistMapper mapper;

    @Test
    void mapToDentistTest() {
        //Given
        DentistDto dentistDto = new DentistDto(1L, "DentistName", "DentistSurname", LocalDate.of(2000, 1, 1));

        //When
        Dentist dentist = mapper.mapToDentist(dentistDto);

        //Then
        assertEquals(1L, dentist.getId());
        assertEquals("DentistName", dentist.getName());
        assertEquals("DentistSurname", dentist.getSurname());
        assertEquals(LocalDate.of(2000, 1, 1), dentist.getExperience());
    }

    @Test
    void mapToDentistDtoTest() {
        //Given
        Dentist dentist = new Dentist(1L, "DentistName", "DentistSurname", LocalDate.of(2000, 1, 1));

        //When
        DentistDto dentistDto = mapper.mapToDentistDto(dentist);

        //Then
        assertEquals(1L, dentistDto.getId());
        assertEquals("DentistName", dentistDto.getName());
        assertEquals("DentistSurname", dentistDto.getSurname());
        assertEquals(LocalDate.of(2000, 1, 1), dentistDto.getExperience());
    }

    @Test
    void mapToDentistListTest() {
        //Given
        DentistDto dentistDto1 = new DentistDto(1L, "DentistName1", "DentistSurname1", LocalDate.of(2000, 1, 1));
        DentistDto dentistDto2 = new DentistDto(2L, "DentistName2", "DentistSurname2", LocalDate.of(2000, 1, 2));
        DentistDto dentistDto3 = new DentistDto(3L, "DentistName3", "DentistSurname3", LocalDate.of(2000, 1, 3));

        List<DentistDto> dentistList = List.of(dentistDto1, dentistDto2, dentistDto3);

        //When
        List<Dentist> dentistDtoList = mapper.mapToDentistList(dentistList);

        //Then
        assertEquals(3, dentistList.size());
        assertEquals(1L, dentistDtoList.get(0).getId());
        assertEquals(2L, dentistDtoList.get(1).getId());
        assertEquals(3L, dentistDtoList.get(2).getId());
        assertEquals("DentistName1", dentistDtoList.get(0).getName());
        assertEquals("DentistName2", dentistDtoList.get(1).getName());
        assertEquals("DentistName3", dentistDtoList.get(2).getName());
        assertEquals("DentistSurname1", dentistDtoList.get(0).getSurname());
        assertEquals("DentistSurname2", dentistDtoList.get(1).getSurname());
        assertEquals("DentistSurname3", dentistDtoList.get(2).getSurname());
        assertEquals(LocalDate.of(2000, 1, 1), dentistDtoList.get(0).getExperience());
        assertEquals(LocalDate.of(2000, 1, 2), dentistDtoList.get(1).getExperience());
        assertEquals(LocalDate.of(2000, 1, 3), dentistDtoList.get(2).getExperience());
    }
}