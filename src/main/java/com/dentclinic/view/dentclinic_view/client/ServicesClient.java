package com.dentclinic.view.dentclinic_view.client;

import com.dentclinic.view.dentclinic_view.domain.ServicesDto;
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
public class ServicesClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServicesClient.class);
    private final RestTemplate restTemplate;

    public List<ServicesDto> getServices() {
        String BASE_URL = "http://localhost:8080/v1/service";
        URI url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .build()
                .encode()
                .toUri();

        try {
            ServicesDto[] boardsResponse = restTemplate.getForObject(url, ServicesDto[].class);
            return Optional.ofNullable(boardsResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList())
                    .stream()
                    .filter(p -> Objects.nonNull(p.getId()) && Objects.nonNull(p.getDescription()))
                    .collect(Collectors.toList());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public void addService(ServicesDto servicesDto) {
        String url = "http://localhost:8080/v1/service";
//        JSONObject request = new JSONObject();
//        request.put("description", servicesDto.getDescription());
//        request.put("cost", servicesDto.getCost());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ServicesDto> entity = new HttpEntity<>(servicesDto, headers);

        restTemplate.exchange(url, HttpMethod.POST, entity, ServicesDto.class);
    }

    public void updateService(ServicesDto servicesDto) {
        String url = "http://localhost:8080/v1/service";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ServicesDto> entity = new HttpEntity<>(servicesDto, headers);

        restTemplate.exchange(url, HttpMethod.PUT, entity, ServicesDto.class);
    }

    public void deleteService(ServicesDto servicesDto) {
        String url = "http://localhost:8080/v1/service/" + servicesDto.getId().toString();

        restTemplate.delete(url);
    }
}