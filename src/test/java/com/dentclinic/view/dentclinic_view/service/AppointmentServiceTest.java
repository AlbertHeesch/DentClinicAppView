package com.dentclinic.view.dentclinic_view.service;

import com.dentclinic.view.dentclinic_view.client.AppointmentClient;
import com.dentclinic.view.dentclinic_view.domain.*;
import com.dentclinic.view.dentclinic_view.mapper.AppointmentMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService service;

    @Mock
    private AppointmentClient client;

    @Mock
    private AppointmentMapper mapper;

    @Test
    void shouldFetchEmptyAppointmentList() {
        //Given
        List<AppointmentDto> emptyDtoList = Collections.emptyList();
        when(client.getAppointments()).thenReturn(emptyDtoList);

        List<Appointment> emptyList = Collections.emptyList();
        when(mapper.mapToAppointmentList(any(List.class))).thenReturn(emptyList);

        //When
        List<Appointment> fetchedList = service.fetchAllAppointments();

        //Then
        assertEquals(0, fetchedList.size());
    }

    @Test
    void fetchAllAppointmentsTest() {
        //Given
        AppointmentDto appointmentDto1 = new AppointmentDto(1L, "Name1", "Surname1", "1234", "sample1@email.com",
                LocalDateTime.of(2022, 1, 1, 12, 0), 1L, 1L);
        AppointmentDto appointmentDto2 = new AppointmentDto(2L, "Name2", "Surname2", "12345", "sample2@email.com",
                LocalDateTime.of(2022, 1, 2, 12, 0), 1L, 1L);
        AppointmentDto appointmentDto3 = new AppointmentDto(3L, "Name3", "Surname3", "123456", "sample3@email.com",
                LocalDateTime.of(2022, 1, 3, 12, 0), 1L, 1L);
        List<AppointmentDto> appointmentDtoList = List.of(appointmentDto1, appointmentDto2, appointmentDto3);
        when(client.getAppointments()).thenReturn(appointmentDtoList);

        Appointment appointment1 = new Appointment(1L, "Name1", "Surname1", new BigDecimal("1234"), "sample1@email.com",
                LocalDateTime.of(2022, 1, 1, 12, 0),
                new Dentist(1L, "DentistName", "DentistSurname", LocalDate.of(1999, 1, 1)),
                new Services(1L, "ServiceDescription", new BigDecimal("1.1")));
        Appointment appointment2 = new Appointment(2L, "Name2", "Surname2", new BigDecimal("12345"), "sample2@email.com",
                LocalDateTime.of(2022, 1, 2, 12, 0),
                new Dentist(1L, "DentistName", "DentistSurname", LocalDate.of(1999, 1, 1)),
                new Services(1L, "ServiceDescription", new BigDecimal("1.1")));
        Appointment appointment3 = new Appointment(3L, "Name3", "Surname3", new BigDecimal("123456"), "sample3@email.com",
                LocalDateTime.of(2022, 1, 3, 12, 0),
                new Dentist(1L, "DentistName", "DentistSurname", LocalDate.of(1999, 1, 1)),
                new Services(1L, "ServiceDescription", new BigDecimal("1.1")));
        List<Appointment> appointmentList = List.of(appointment1, appointment2, appointment3);
        when(mapper.mapToAppointmentList(any(List.class))).thenReturn(appointmentList);

        //When
        List<Appointment> fetchedList = service.fetchAllAppointments();

        //Then
        assertEquals(3, fetchedList.size());
        assertEquals(1L, fetchedList.get(0).getId());
        assertEquals(2L, fetchedList.get(1).getId());
        assertEquals(3L, fetchedList.get(2).getId());
        assertEquals("Name1", fetchedList.get(0).getName());
        assertEquals("Name2", fetchedList.get(1).getName());
        assertEquals("Name3", fetchedList.get(2).getName());
        assertEquals("Surname1", fetchedList.get(0).getSurname());
        assertEquals("Surname2", fetchedList.get(1).getSurname());
        assertEquals("Surname3", fetchedList.get(2).getSurname());
        assertEquals(new BigDecimal("1234"), fetchedList.get(0).getPesel());
        assertEquals(new BigDecimal("12345"), fetchedList.get(1).getPesel());
        assertEquals(new BigDecimal("123456"), fetchedList.get(2).getPesel());
        assertEquals("sample1@email.com", fetchedList.get(0).getEmail());
        assertEquals("sample2@email.com", fetchedList.get(1).getEmail());
        assertEquals("sample3@email.com", fetchedList.get(2).getEmail());
        assertEquals(LocalDateTime.of(2022, 1, 1, 12, 0), fetchedList.get(0).getDate());
        assertEquals(LocalDateTime.of(2022, 1, 2, 12, 0), fetchedList.get(1).getDate());
        assertEquals(LocalDateTime.of(2022, 1, 3, 12, 0), fetchedList.get(2).getDate());
        /*Dentist*/
        assertEquals(1L, fetchedList.get(0).getDentist().getId());
        assertEquals("DentistName", fetchedList.get(0).getDentist().getName());
        assertEquals("DentistSurname", fetchedList.get(0).getDentist().getSurname());
        assertEquals(LocalDate.of(1999, 1, 1), fetchedList.get(0).getDentist().getExperience());
        assertEquals(1L, fetchedList.get(1).getDentist().getId());
        assertEquals("DentistName", fetchedList.get(1).getDentist().getName());
        assertEquals("DentistSurname", fetchedList.get(1).getDentist().getSurname());
        assertEquals(LocalDate.of(1999, 1, 1), fetchedList.get(1).getDentist().getExperience());
        assertEquals(1L, fetchedList.get(2).getDentist().getId());
        assertEquals("DentistName", fetchedList.get(2).getDentist().getName());
        assertEquals("DentistSurname", fetchedList.get(2).getDentist().getSurname());
        assertEquals(LocalDate.of(1999, 1, 1), fetchedList.get(2).getDentist().getExperience());
        /*Service*/
        assertEquals(1L, fetchedList.get(0).getService().getId());
        assertEquals("ServiceDescription", fetchedList.get(0).getService().getDescription());
        assertEquals(new BigDecimal("1.1"), fetchedList.get(0).getService().getCost());
        assertEquals(1L, fetchedList.get(1).getService().getId());
        assertEquals("ServiceDescription", fetchedList.get(1).getService().getDescription());
        assertEquals(new BigDecimal("1.1"), fetchedList.get(1).getService().getCost());
        assertEquals(1L, fetchedList.get(2).getService().getId());
        assertEquals("ServiceDescription", fetchedList.get(2).getService().getDescription());
        assertEquals(new BigDecimal("1.1"), fetchedList.get(2).getService().getCost());
    }
}