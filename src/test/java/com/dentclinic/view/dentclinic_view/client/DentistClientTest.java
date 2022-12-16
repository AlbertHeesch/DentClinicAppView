package com.dentclinic.view.dentclinic_view.client;

import com.dentclinic.view.dentclinic_view.domain.DentistDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DentistClientTest {

    @InjectMocks
    private DentistClient client;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void getDentistsTest() throws URISyntaxException {
        //Given
        DentistDto dentistDto1 = new DentistDto(1L, "DentistName1", "DentistSurname1", LocalDate.of(2000, 1, 1));
        DentistDto dentistDto2 = new DentistDto(2L, "DentistName2", "DentistSurname2", LocalDate.of(2000, 1, 2));
        DentistDto dentistDto3 = new DentistDto(3L, "DentistName3", "DentistSurname3", LocalDate.of(2000, 1, 3));
        DentistDto[] dentists = new DentistDto[3];
        dentists[0] = dentistDto1;
        dentists[1] = dentistDto2;
        dentists[2] = dentistDto3;

        URI uri = new URI("http://localhost:8080/app/v1/dentist/");
        when(restTemplate.getForObject(uri, DentistDto[].class)).thenReturn(dentists);

        //When
        List<DentistDto> fetchedDentistsDto = client.getDentists();

        //Then
        assertEquals(3, fetchedDentistsDto.size());
        assertEquals(1L, fetchedDentistsDto.get(0).getId());
        assertEquals(2L, fetchedDentistsDto.get(1).getId());
        assertEquals(3L, fetchedDentistsDto.get(2).getId());
        assertEquals("DentistName1", fetchedDentistsDto.get(0).getName());
        assertEquals("DentistName2", fetchedDentistsDto.get(1).getName());
        assertEquals("DentistName3", fetchedDentistsDto.get(2).getName());
        assertEquals("DentistSurname1", fetchedDentistsDto.get(0).getSurname());
        assertEquals("DentistSurname2", fetchedDentistsDto.get(1).getSurname());
        assertEquals("DentistSurname3", fetchedDentistsDto.get(2).getSurname());
        assertEquals(LocalDate.of(2000, 1, 1), fetchedDentistsDto.get(0).getExperience());
        assertEquals(LocalDate.of(2000, 1, 2), fetchedDentistsDto.get(1).getExperience());
        assertEquals(LocalDate.of(2000, 1, 3), fetchedDentistsDto.get(2).getExperience());
    }

    @Test
    void shouldFetchEmptyList() throws URISyntaxException {
        //Given
        DentistDto[] dentistDtos = new DentistDto[0];

        URI uri = new URI("http://localhost:8080/app/v1/dentist/");
        when(restTemplate.getForObject(uri, DentistDto[].class)).thenReturn(dentistDtos);

        //When
        List<DentistDto> fetchedDentistsDto = client.getDentists();

        //Then
        assertEquals(0, fetchedDentistsDto.size());
    }
}