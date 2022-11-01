package com.dentclinic.view.dentclinic_view.mapper;

import com.dentclinic.view.dentclinic_view.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentMapper
{
    public Appointment mapToAppointment(final AppointmentDto appointmentDto) {
        return new Appointment(
                appointmentDto.getId(),
                appointmentDto.getName(),
                appointmentDto.getSurname(),
                BigDecimal.valueOf(Double.parseDouble(appointmentDto.getPesel())).setScale(0),
                appointmentDto.getEmail(),
                appointmentDto.getDate(),
                new Dentist(
                        appointmentDto.getDentist().getId(),
                        appointmentDto.getDentist().getName(),
                        appointmentDto.getDentist().getSurname(),
                        appointmentDto.getDentist().getExperience()
                ),
                new Services(
                        appointmentDto.getService().getId(),
                        appointmentDto.getService().getDescription(),
                        BigDecimal.valueOf(appointmentDto.getService().getCost())
                )
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
                new DentistDto(
                        appointment.getDentist().getId(),
                        appointment.getDentist().getName(),
                        appointment.getDentist().getSurname(),
                        appointment.getDentist().getExperience()
                ),
                new ServicesDto(
                        appointment.getService().getId(),
                        appointment.getService().getDescription(),
                        appointment.getService().getCost().doubleValue()
                )
        );
    }

    public List<Appointment> mapToAppointmentList(final List<AppointmentDto> appointmentDtoList)
    {
        return appointmentDtoList.stream()
                .map(this::mapToAppointment)
                .collect(Collectors.toList());
    }
}