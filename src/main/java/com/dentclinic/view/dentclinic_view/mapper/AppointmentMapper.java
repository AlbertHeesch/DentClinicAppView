package com.dentclinic.view.dentclinic_view.mapper;

import com.dentclinic.view.dentclinic_view.domain.*;
import com.dentclinic.view.dentclinic_view.service.DentistService;
import com.dentclinic.view.dentclinic_view.service.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentMapper
{
    private final DentistService dentistService;
    private final ServicesService servicesService;

    public Appointment mapToAppointment(final AppointmentDto appointmentDto) {
        List<Dentist> dentistList = dentistService.fetchAllDentists().stream()
                .filter(dentists -> dentists.getId() == appointmentDto.getDentistId())
                .collect(Collectors.toList());
        List<Services> serviceList = servicesService.fetchAllServices().stream()
                .filter(services -> services.getId() == appointmentDto.getServiceId())
                .collect(Collectors.toList());

        return new Appointment(
                appointmentDto.getId(),
                appointmentDto.getName(),
                appointmentDto.getSurname(),
                BigDecimal.valueOf(Double.parseDouble(appointmentDto.getPesel())).setScale(0),
                appointmentDto.getEmail(),
                appointmentDto.getDate(),
                dentistList.get(0),
                serviceList.get(0)
        );
    }

    public AppointmentDto mapToAppointmentDto(final Appointment appointment)
    {
        return new AppointmentDto(
                appointment.getId(),
                appointment.getName(),
                appointment.getSurname(),
                appointment.getPesel().toString(),
                appointment.getEmail(),
                appointment.getDate(),
                appointment.getDentist().getId(),
                appointment.getService().getId()
        );
    }

    public List<Appointment> mapToAppointmentList(final List<AppointmentDto> appointmentDtoList)
    {
        return appointmentDtoList.stream()
                .map(this::mapToAppointment)
                .collect(Collectors.toList());
    }
}