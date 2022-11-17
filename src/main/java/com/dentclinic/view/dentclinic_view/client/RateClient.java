package com.dentclinic.view.dentclinic_view.client;

import com.dentclinic.view.dentclinic_view.domain.RateDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RateClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(RateClient.class);
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/appRates/v1/rate/";

    public List<RateDto> getRates() {
        URI url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .build()
                .encode()
                .toUri();

        try {
            RateDto[] boardsResponse = restTemplate.getForObject(url, RateDto[].class);
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

    public void addRate(RateDto rateDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RateDto> entity = new HttpEntity<>(rateDto, headers);

      restTemplate.exchange(baseUrl, HttpMethod.POST, entity, RateDto.class);
    }

    public void updateRate(RateDto rateDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RateDto> entity = new HttpEntity<>(rateDto, headers);

        restTemplate.exchange(baseUrl, HttpMethod.PUT, entity, RateDto.class);
    }

    public void deleteRate(RateDto rateDto) {
        String url = baseUrl + rateDto.getId().toString();

        restTemplate.delete(url);
    }
}
