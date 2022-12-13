package com.dentclinic.view.dentclinic_view.mapper;

import com.dentclinic.view.dentclinic_view.domain.Appointment;
import com.dentclinic.view.dentclinic_view.domain.AppointmentDto;
import com.dentclinic.view.dentclinic_view.domain.Dentist;
import com.dentclinic.view.dentclinic_view.domain.Services;
import com.dentclinic.view.dentclinic_view.service.DentistService;
import com.dentclinic.view.dentclinic_view.service.ServicesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentMapperTest {

    @InjectMocks
    private AppointmentMapper mapper;

    @Mock
    private DentistService dentistService;

    @Mock
    private ServicesService servicesService;

    @Test
    void mapToAppointmentTest() {
        //Given
        AppointmentDto appointmentDto = new AppointmentDto(1L, "Name", "Surname", "1234", "sample@email.com",
                LocalDateTime.of(2022, 1, 1, 12, 0), 1L, 1L);

        Dentist dentist = new Dentist(1L, "DentistName", "DentistSurname", LocalDate.of(1999, 1, 1));
        when(dentistService.fetchAllDentists()).thenReturn(List.of(dentist));

        Services service = new Services(1L, "ServiceDescription", new BigDecimal("1.1"));
        when(servicesService.fetchAllServices()).thenReturn(List.of(service));

        //When
        Appointment appointment = mapper.mapToAppointment(appointmentDto);

        //Then
        assertEquals(1L, appointment.getId());
        assertEquals("Name", appointment.getName());
        assertEquals("Surname", appointment.getSurname());
        assertEquals(new BigDecimal("1234"), appointment.getPesel());
        assertEquals("sample@email.com", appointment.getEmail());
        assertEquals(LocalDateTime.of(2022, 1, 1, 12, 0), appointment.getDate());
        /*Dentist*/
        assertEquals(1L, appointment.getDentist().getId());
        assertEquals("DentistName", appointment.getDentist().getName());
        assertEquals("DentistSurname", appointment.getDentist().getSurname());
        assertEquals(LocalDate.of(1999, 1, 1), appointment.getDentist().getExperience());
        /*Service*/
        assertEquals(1L, appointment.getService().getId());
        assertEquals("ServiceDescription", appointment.getService().getDescription());
        assertEquals(new BigDecimal("1.1"), appointment.getService().getCost());
    }

    @Test
    void mapToAppointmentDtoTest() {
        //Given
        Appointment appointment = new Appointment(1L, "Name", "Surname", new BigDecimal("1234"), "sample@email.com", LocalDateTime.of(2022, 1, 1, 12, 0),
                new Dentist(1L, "DentistName", "DentistSurname", LocalDate.of(1999, 1, 1)),
                new Services(1L, "ServiceDescription", new BigDecimal("1.1")));

        //When
        AppointmentDto appointmentDto = mapper.mapToAppointmentDto(appointment);

        //Then
        assertEquals(1L, appointmentDto.getId());
        assertEquals("Name", appointmentDto.getName());
        assertEquals("Surname", appointmentDto.getSurname());
        assertEquals("1234", appointmentDto.getPesel());
        assertEquals("sample@email.com", appointmentDto.getEmail());
        assertEquals(LocalDateTime.of(2022, 1, 1, 12, 0), appointmentDto.getDate());
        assertEquals(1L, appointmentDto.getDentistId());
        assertEquals(1L, appointmentDto.getServiceId());
    }

    @Test
    void mapToAppointmentListTest() {
        //Given
        AppointmentDto appointmentDto1 = new AppointmentDto(1L, "Name1", "Surname1", "1234", "sample1@email.com",
                LocalDateTime.of(2022, 1, 1, 12, 0), 1L, 1L);
        AppointmentDto appointmentDto2 = new AppointmentDto(2L, "Name2", "Surname2", "12345", "sample2@email.com",
                LocalDateTime.of(2022, 1, 2, 12, 0), 1L, 1L);
        AppointmentDto appointmentDto3 = new AppointmentDto(3L, "Name3", "Surname3", "123456", "sample3@email.com",
                LocalDateTime.of(2022, 1, 3, 12, 0), 1L, 1L);

        List<AppointmentDto> appointmentDtoList = List.of(appointmentDto1, appointmentDto2, appointmentDto3);

        Dentist dentist = new Dentist(1L, "DentistName", "DentistSurname", LocalDate.of(1999, 1, 1));
        when(dentistService.fetchAllDentists()).thenReturn(List.of(dentist));

        Services service = new Services(1L, "ServiceDescription", new BigDecimal("1.1"));
        when(servicesService.fetchAllServices()).thenReturn(List.of(service));

        //When
        List<Appointment> appointmentList = mapper.mapToAppointmentList(appointmentDtoList);

        //Then
        assertEquals(3, appointmentList.size());
        assertEquals(1L, appointmentList.get(0).getId());
        assertEquals(2L, appointmentList.get(1).getId());
        assertEquals(3L, appointmentList.get(2).getId());
        assertEquals("Name1", appointmentList.get(0).getName());
        assertEquals("Name2", appointmentList.get(1).getName());
        assertEquals("Name3", appointmentList.get(2).getName());
        assertEquals("Surname1", appointmentList.get(0).getSurname());
        assertEquals("Surname2", appointmentList.get(1).getSurname());
        assertEquals("Surname3", appointmentList.get(2).getSurname());
        assertEquals(new BigDecimal("1234"), appointmentList.get(0).getPesel());
        assertEquals(new BigDecimal("12345"), appointmentList.get(1).getPesel());
        assertEquals(new BigDecimal("123456"), appointmentList.get(2).getPesel());
        assertEquals("sample1@email.com", appointmentList.get(0).getEmail());
        assertEquals("sample2@email.com", appointmentList.get(1).getEmail());
        assertEquals("sample3@email.com", appointmentList.get(2).getEmail());
        assertEquals(LocalDateTime.of(2022, 1, 1, 12, 0), appointmentList.get(0).getDate());
        assertEquals(LocalDateTime.of(2022, 1, 2, 12, 0), appointmentList.get(1).getDate());
        assertEquals(LocalDateTime.of(2022, 1, 3, 12, 0), appointmentList.get(2).getDate());
        /*Dentist*/
        assertEquals(1L, appointmentList.get(0).getDentist().getId());
        assertEquals("DentistName", appointmentList.get(0).getDentist().getName());
        assertEquals("DentistSurname", appointmentList.get(0).getDentist().getSurname());
        assertEquals(LocalDate.of(1999, 1, 1), appointmentList.get(0).getDentist().getExperience());
        assertEquals(1L, appointmentList.get(1).getDentist().getId());
        assertEquals("DentistName", appointmentList.get(1).getDentist().getName());
        assertEquals("DentistSurname", appointmentList.get(1).getDentist().getSurname());
        assertEquals(LocalDate.of(1999, 1, 1), appointmentList.get(1).getDentist().getExperience());
        assertEquals(1L, appointmentList.get(2).getDentist().getId());
        assertEquals("DentistName", appointmentList.get(2).getDentist().getName());
        assertEquals("DentistSurname", appointmentList.get(2).getDentist().getSurname());
        assertEquals(LocalDate.of(1999, 1, 1), appointmentList.get(2).getDentist().getExperience());
        /*Service*/
        assertEquals(1L, appointmentList.get(0).getService().getId());
        assertEquals("ServiceDescription", appointmentList.get(0).getService().getDescription());
        assertEquals(new BigDecimal("1.1"), appointmentList.get(0).getService().getCost());
        assertEquals(1L, appointmentList.get(1).getService().getId());
        assertEquals("ServiceDescription", appointmentList.get(1).getService().getDescription());
        assertEquals(new BigDecimal("1.1"), appointmentList.get(1).getService().getCost());
        assertEquals(1L, appointmentList.get(2).getService().getId());
        assertEquals("ServiceDescription", appointmentList.get(2).getService().getDescription());
        assertEquals(new BigDecimal("1.1"), appointmentList.get(2).getService().getCost());
    }
}