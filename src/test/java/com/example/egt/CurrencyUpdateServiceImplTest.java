package com.example.egt;

import com.example.egt.models.Currency;
import com.example.egt.models.FixerApiResponse;
import com.example.egt.repos.CurrencyRepository;
import com.example.egt.services.CurrencyUpdateServiceImpl;
import com.example.egt.services.contracts.FixerApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class CurrencyUpdateServiceImplTest {

    @Mock
    private FixerApiService fixerApiService;

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CurrencyUpdateServiceImpl currencyUpdateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateCurrencyRates_SuccessfulUpdate() {
        FixerApiResponse mockResponse = new FixerApiResponse();
        mockResponse.setSuccess(true);
        mockResponse.setTimestamp(1234567890L);
        mockResponse.setBase("USD");
        mockResponse.setDate(LocalDate.now());
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("EUR", BigDecimal.valueOf(0.85));
        rates.put("GBP", BigDecimal.valueOf(0.75));
        mockResponse.setRates(rates);

        when(fixerApiService.getLatestRates()).thenReturn(mockResponse);
        currencyUpdateService.updateCurrencyRates();

        verify(fixerApiService, times(1)).getLatestRates();
        verify(currencyRepository, times(2)).save(any(Currency.class));
    }

    @Test
    void updateCurrencyRates_FailedUpdate() {
        FixerApiResponse mockResponse = new FixerApiResponse();
        mockResponse.setSuccess(false);

        when(fixerApiService.getLatestRates()).thenReturn(mockResponse);
        currencyUpdateService.updateCurrencyRates();

        verify(fixerApiService, times(1)).getLatestRates();
        verify(currencyRepository, never()).save(any(Currency.class));
    }


}