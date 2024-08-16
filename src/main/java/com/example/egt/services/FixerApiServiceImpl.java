package com.example.egt.services;

import com.example.egt.models.FixerApiResponse;
import com.example.egt.services.contracts.FixerApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FixerApiServiceImpl implements FixerApiService {

    @Value("${fixer.api.url}")
    private String fixerApiUrl;

    @Value("${fixer.api.key}")
    private String fixerApiKey;

    private final RestTemplate restTemplate;


    public FixerApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public FixerApiResponse getLatestRates() {
        String url = fixerApiUrl + "/latest?access_key=" + fixerApiKey;
        return restTemplate.getForObject(url, FixerApiResponse.class);
    }
}
