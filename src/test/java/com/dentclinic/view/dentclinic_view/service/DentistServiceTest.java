package com.dentclinic.view.dentclinic_view.service;

import com.dentclinic.view.dentclinic_view.client.DentistClient;
import com.dentclinic.view.dentclinic_view.domain.Dentist;
import com.dentclinic.view.dentclinic_view.domain.DentistDto;
import com.dentclinic.view.dentclinic_view.mapper.DentistMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DentistServiceTest {

    @InjectMocks
    private DentistService service;

    @Mock
    private DentistClient client;

    @Mock
    private DentistMapper mapper;

    @Test
    void shouldFetchEmptyDentistList() {
        //Given
        List<DentistDto> emptyDtoList = Collections.emptyList();
        when(client.getDentists()).thenReturn(emptyDtoList);

        List<Dentist> emptyList = Collections.emptyList();
        when(mapper.mapToDentistList(any(List.class))).thenReturn(emptyList);

        //When
        List<Dentist> fetchedList = service.fetchAllDentists();

        //Then
        assertEquals(0, fetchedList.size());
    }

    @Test
    void fetchAllDentistsTest() {
        //Given
        DentistDto dentistDto1 = new DentistDto(1L, "DentistName1", "DentistSurname1", LocalDate.of(2000, 1, 1));
        DentistDto dentistDto2 = new DentistDto(2L, "DentistName2", "DentistSurname2", LocalDate.of(2000, 1, 2));
        DentistDto dentistDto3 = new DentistDto(3L, "DentistName3", "DentistSurname3", LocalDate.of(2000, 1, 3));
        List<DentistDto> dentistDtoList = List.of(dentistDto1, dentistDto2, dentistDto3);
        when(client.getDentists()).thenReturn(dentistDtoList);

        Dentist dentist1 = new Dentist(1L, "DentistName1", "DentistSurname1", LocalDate.of(2000, 1, 1));
        Dentist dentist2 = new Dentist(2L, "DentistName2", "DentistSurname2", LocalDate.of(2000, 1, 2));
        Dentist dentist3 = new Dentist(3L, "DentistName3", "DentistSurname3", LocalDate.of(2000, 1, 3));
        List<Dentist> dentistList = List.of(dentist1, dentist2, dentist3);
        when(mapper.mapToDentistList(any(List.class))).thenReturn(dentistList);

        //When
        List<Dentist> fetchedList = service.fetchAllDentists();

        //Then
        assertEquals(3, fetchedList.size());
        assertEquals(1L, fetchedList.get(0).getId());
        assertEquals(2L, fetchedList.get(1).getId());
        assertEquals(3L, fetchedList.get(2).getId());
        assertEquals("DentistName1", fetchedList.get(0).getName());
        assertEquals("DentistName2", fetchedList.get(1).getName());
        assertEquals("DentistName3", fetchedList.get(2).getName());
        assertEquals("DentistSurname1", fetchedList.get(0).getSurname());
        assertEquals("DentistSurname2", fetchedList.get(1).getSurname());
        assertEquals("DentistSurname3", fetchedList.get(2).getSurname());
        assertEquals(LocalDate.of(2000, 1, 1), fetchedList.get(0).getExperience());
        assertEquals(LocalDate.of(2000, 1, 2), fetchedList.get(1).getExperience());
        assertEquals(LocalDate.of(2000, 1, 3), fetchedList.get(2).getExperience());
    }
}