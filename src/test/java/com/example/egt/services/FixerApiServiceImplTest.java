package com.example.egt.services;

import com.example.egt.models.FixerApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FixerApiServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FixerApiServiceImpl fixerApiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(fixerApiService, "fixerApiUrl", "http://api.fixer.io");
        ReflectionTestUtils.setField(fixerApiService, "fixerApiKey", "testApiKey");
    }

    @Test
    void testGetLatestRates() {
        String expectedUrl = "http://api.fixer.io/latest?access_key=testApiKey";
        FixerApiResponse expectedResponse = new FixerApiResponse();
        when(restTemplate.getForObject(expectedUrl, FixerApiResponse.class)).thenReturn(expectedResponse);

        FixerApiResponse actualResponse = fixerApiService.getLatestRates();

        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate).getForObject(expectedUrl, FixerApiResponse.class);
    }
}
