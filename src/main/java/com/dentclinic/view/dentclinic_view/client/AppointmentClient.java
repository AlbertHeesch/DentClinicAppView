package com.dentclinic.view.dentclinic_view.client;

import com.dentclinic.view.dentclinic_view.domain.AppointmentDto;
import com.dentclinic.view.dentclinic_view.domain.RateDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AppointmentClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentClient.class);
    private final RestTemplate restTemplate;

    public List<AppointmentDto> getAppointments() {
        String BASE_URL = "http://localhost:8080/v1/appointment";
        URI url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .build()
                .encode()
                .toUri();

        try {
            AppointmentDto[] boardsResponse = restTemplate.getForObject(url, AppointmentDto[].class);
            return Optional.ofNullable(boardsResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList())
                    .stream()
                    .filter(p -> Objects.nonNull(p.getId()) && Objects.nonNull(p.getName()))
                    .collect(Collectors.toList());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public void addAppointment(AppointmentDto appointmentDto) {
        String BASE_URL = "http://localhost:8082/v1/appointment";
        URI url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("id", appointmentDto.getId())
                .queryParam("name", appointmentDto.getName())
                .queryParam("surname", appointmentDto.getSurname())
                .queryParam("pesel", appointmentDto.getPesel())
                .queryParam("email", appointmentDto.getEmail())
                .queryParam("date", appointmentDto.getDate())
                .queryParam("dentist", appointmentDto.getDentist())
                .queryParam("service", appointmentDto.getService())
                .build()
                .encode()
                .toUri();

        restTemplate.postForObject(url, null, AppointmentDto.class);
    }

    public void updateAppointment(AppointmentDto appointmentDto) {
        String BASE_URL = "http://localhost:8082/v1/appointment";
        URI url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("id", appointmentDto.getId())
                .queryParam("name", appointmentDto.getName())
                .queryParam("surname", appointmentDto.getSurname())
                .queryParam("pesel", appointmentDto.getPesel())
                .queryParam("email", appointmentDto.getEmail())
                .queryParam("date", appointmentDto.getDate())
                .queryParam("dentist", appointmentDto.getDentist())
                .queryParam("service", appointmentDto.getService())
                .build()
                .encode()
                .toUri();

        restTemplate.put(url, null);
    }

    public void deleteAppointment(AppointmentDto appointmentDto) {
        String BASE_URL = "http://localhost:8082/v1/appointment";
        URI url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("id", appointmentDto.getId())
                .queryParam("name", appointmentDto.getName())
                .queryParam("surname", appointmentDto.getSurname())
                .queryParam("pesel", appointmentDto.getPesel())
                .queryParam("email", appointmentDto.getEmail())
                .queryParam("date", appointmentDto.getDate())
                .queryParam("dentist", appointmentDto.getDentist())
                .queryParam("service", appointmentDto.getService())
                .build()
                .encode()
                .toUri();

        restTemplate.delete(url);
    }
}
