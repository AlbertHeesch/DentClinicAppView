package com.dentclinic.view.dentclinic_view.client;

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
public class RateClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(RateClient.class);
    private final RestTemplate restTemplate;

    public List<RateDto> getRates() {
        String BASE_URL = "http://localhost:8082/v1/rate";
        URI url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
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
        String BASE_URL = "http://localhost:8082/v1/rate";
        URI url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("id", rateDto.getId())
                .queryParam("name", rateDto.getName())
                .queryParam("value", rateDto.getValue())
                .build()
                .encode()
                .toUri();

        restTemplate.postForObject(url, null, RateDto.class);
    }

    public void updateRate(RateDto rateDto) {
        String BASE_URL = "http://localhost:8082/v1/rate";
        URI url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("id", rateDto.getId())
                .queryParam("name", rateDto.getName())
                .queryParam("value", rateDto.getValue())
                .build()
                .encode()
                .toUri();

        restTemplate.put(url, null);
    }

    public void deleteRate(RateDto rateDto) {
        String BASE_URL = "http://localhost:8082/v1/rate";
        URI url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("id", rateDto.getId())
                .queryParam("name", rateDto.getName())
                .queryParam("value", rateDto.getValue())
                .build()
                .encode()
                .toUri();

        restTemplate.delete(url);
    }
}
