package com.dentclinic.view.dentclinic_view.client;

import com.dentclinic.view.dentclinic_view.domain.AppointmentDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentClientTest {

    @InjectMocks
    private AppointmentClient client;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void getAppointmentsTest() throws URISyntaxException {
        //Given
        AppointmentDto appointmentDto1 = new AppointmentDto(1L, "Name1", "Surname1", "1234", "sample1@email.com",
                LocalDateTime.of(2022, 1, 1, 12, 0), 1L, 1L);
        AppointmentDto appointmentDto2 = new AppointmentDto(2L, "Name2", "Surname2", "12345", "sample2@email.com",
                LocalDateTime.of(2022, 1, 2, 12, 0), 1L, 1L);
        AppointmentDto appointmentDto3 = new AppointmentDto(3L, "Name3", "Surname3", "123456", "sample3@email.com",
                LocalDateTime.of(2022, 1, 3, 12, 0), 1L, 1L);
        AppointmentDto[] appointments = new AppointmentDto[3];
        appointments[0] = appointmentDto1;
        appointments[1] = appointmentDto2;
        appointments[2] = appointmentDto3;

        URI uri = new URI("http://localhost:8080/app/v1/appointment/");
        when(restTemplate.getForObject(uri, AppointmentDto[].class)).thenReturn(appointments);

        //When
        List<AppointmentDto> fetchedAppointmentsDto = client.getAppointments();

        //Then
        assertEquals(3, fetchedAppointmentsDto.size());
        assertEquals(1L, fetchedAppointmentsDto.get(0).getId());
        assertEquals(2L, fetchedAppointmentsDto.get(1).getId());
        assertEquals(3L, fetchedAppointmentsDto.get(2).getId());
        assertEquals("Name1", fetchedAppointmentsDto.get(0).getName());
        assertEquals("Name2", fetchedAppointmentsDto.get(1).getName());
        assertEquals("Name3", fetchedAppointmentsDto.get(2).getName());
        assertEquals("Surname1", fetchedAppointmentsDto.get(0).getSurname());
        assertEquals("Surname2", fetchedAppointmentsDto.get(1).getSurname());
        assertEquals("Surname3", fetchedAppointmentsDto.get(2).getSurname());
        assertEquals("1234", fetchedAppointmentsDto.get(0).getPesel());
        assertEquals("12345", fetchedAppointmentsDto.get(1).getPesel());
        assertEquals("123456", fetchedAppointmentsDto.get(2).getPesel());
        assertEquals("sample1@email.com", fetchedAppointmentsDto.get(0).getEmail());
        assertEquals("sample2@email.com", fetchedAppointmentsDto.get(1).getEmail());
        assertEquals("sample3@email.com", fetchedAppointmentsDto.get(2).getEmail());
        assertEquals(LocalDateTime.of(2022, 1, 1, 12, 0), fetchedAppointmentsDto.get(0).getDate());
        assertEquals(LocalDateTime.of(2022, 1, 2, 12, 0), fetchedAppointmentsDto.get(1).getDate());
        assertEquals(LocalDateTime.of(2022, 1, 3, 12, 0), fetchedAppointmentsDto.get(2).getDate());
        assertEquals(1L, fetchedAppointmentsDto.get(0).getDentistId());
        assertEquals(1L, fetchedAppointmentsDto.get(1).getDentistId());
        assertEquals(1L, fetchedAppointmentsDto.get(2).getDentistId());
        assertEquals(1L, fetchedAppointmentsDto.get(0).getServiceId());
        assertEquals(1L, fetchedAppointmentsDto.get(1).getServiceId());
        assertEquals(1L, fetchedAppointmentsDto.get(2).getServiceId());
    }

    @Test
    void shouldFetchEmptyList() throws URISyntaxException {
        //Given
        AppointmentDto[] appointmentDtos = new AppointmentDto[0];

        URI uri = new URI("http://localhost:8080/app/v1/appointment/");
        when(restTemplate.getForObject(uri, AppointmentDto[].class)).thenReturn(appointmentDtos);

        //When
        List<AppointmentDto> fetchedAppointmentsDto = client.getAppointments();

        //Then
        assertEquals(0, fetchedAppointmentsDto.size());
    }
}