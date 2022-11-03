package com.dentclinic.view.dentclinic_view.client;

import com.dentclinic.view.dentclinic_view.domain.AppointmentDto;
import com.dentclinic.view.dentclinic_view.domain.RateDto;
import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
        String url = "http://localhost:8080/v1/appointment";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AppointmentDto> entity = new HttpEntity<>(appointmentDto, headers);

        restTemplate.exchange(url, HttpMethod.POST, entity, AppointmentDto.class);
    }

    public void updateAppointment(AppointmentDto appointmentDto) {
        String url = "http://localhost:8080/v1/appointment";
//        JSONObject request = new JSONObject();
//        request.put("id", appointmentDto.getId());
//        request.put("name", appointmentDto.getName());
//        request.put("surname", appointmentDto.getSurname());
//        request.put("pesel", appointmentDto.getPesel());
//        request.put("email", appointmentDto.getEmail());
//        request.put("date", appointmentDto.getDate().toString());
//        request.put("dentist", appointmentDto.getDentist());
//        request.put("service", appointmentDto.getService());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AppointmentDto> entity = new HttpEntity<>(appointmentDto, headers);

        restTemplate.exchange(url, HttpMethod.PUT, entity, AppointmentDto.class);
    }

    public void deleteAppointment(AppointmentDto appointmentDto) {
        String url = "http://localhost:8080/v1/appointment/" + appointmentDto.getId().toString();

        restTemplate.delete(url);
    }
}
