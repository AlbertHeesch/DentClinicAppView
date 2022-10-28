package com.dentclinic.view.dentclinic_view.service;

import com.dentclinic.view.dentclinic_view.client.AppointmentClient;
import com.dentclinic.view.dentclinic_view.domain.Appointment;
import com.dentclinic.view.dentclinic_view.domain.AppointmentDto;
import com.dentclinic.view.dentclinic_view.mapper.AppointmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentClient client;
    private final AppointmentMapper mapper;

    public List<Appointment> fetchAllAppointments()
    {
        List<AppointmentDto> appointmentDtoList = client.getAppointments();
        return mapper.mapToAppointmentList(appointmentDtoList);
    }

    public List<Appointment> fetchFilteredAppointments(String stringFilter)
    {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return fetchAllAppointments();
        } else {
            return fetchAllAppointments().stream()
                    .filter(word -> word.getSurname().toLowerCase(Locale.ROOT).contains(stringFilter.toLowerCase()))
                    .collect(Collectors.toList());
        }
    }

    public void deleteAppointment(Appointment appointment) {
        client.deleteAppointment(mapper.mapToAppointmentDto(appointment));
    }

    public void saveAppointment(Appointment appointment) {
        if (appointment == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
        } else {
            client.addAppointment(mapper.mapToAppointmentDto(appointment));
        }
    }

    public void updateAppointment(Appointment appointment) {
        client.updateAppointment(mapper.mapToAppointmentDto(appointment));
    }
}
