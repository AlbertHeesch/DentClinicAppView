package com.dentclinic.view.dentclinic_view.client;

import com.dentclinic.view.dentclinic_view.domain.DentistDto;
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
public class DentistClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentClient.class);
    private final RestTemplate restTemplate;

    public List<DentistDto> getDentists() {
        String BASE_URL = "http://localhost:8080/v1/dentist";
        URI url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .build()
                .encode()
                .toUri();

        try {
            DentistDto[] boardsResponse = restTemplate.getForObject(url, DentistDto[].class);
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

    public void addDentist(DentistDto dentistDto) {
        String url = "http://localhost:8080/v1/dentist";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DentistDto> entity = new HttpEntity<>(dentistDto, headers);

        restTemplate.exchange(url, HttpMethod.POST, entity, DentistDto.class);
    }

    public void updateDentist(DentistDto dentistDto) {
        String url = "http://localhost:8080/v1/dentist";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DentistDto> entity = new HttpEntity<>(dentistDto, headers);

        restTemplate.exchange(url, HttpMethod.PUT, entity, DentistDto.class);
    }

    public void deleteDentist(DentistDto dentistDto) {
        String url = "http://localhost:8080/v1/dentist/" + dentistDto.getId().toString();

        restTemplate.delete(url);
    }
}