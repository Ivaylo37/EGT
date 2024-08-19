package com.example.egt;

import com.example.egt.exceptions.CurrencyNotFoundException;
import com.example.egt.models.Currency;
import com.example.egt.repos.CurrencyRepository;
import com.example.egt.services.CurrencyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CurrencyServiceImplTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private RedisTemplate<String, Currency> redisTemplate;

    @Mock
    private ValueOperations<String, Currency> valueOperations;

    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void getCurrencyByCode_CacheHit() {
        String code = "USD";
        Currency cachedCurrency = new Currency();
        cachedCurrency.setBase(code);

        when(valueOperations.get("currencies:" + code)).thenReturn(cachedCurrency);

        Currency result = currencyService.getCurrencyByCode(code);

        assertEquals(cachedCurrency, result);
        verify(currencyRepository, never()).findTopByBaseOrderByDateDesc(anyString());
    }

    @Test
    void getCurrencyByCode_CacheMiss_RepositoryHit() {
        String code = "EUR";
        Currency currency = new Currency();
        currency.setBase(code);

        when(valueOperations.get("currencies:" + code)).thenReturn(null);
        when(currencyRepository.findTopByBaseOrderByDateDesc(code.toUpperCase())).thenReturn(currency);

        Currency result = currencyService.getCurrencyByCode(code);

        assertEquals(currency, result);
        verify(valueOperations).set("currencies:" + code, currency);
    }

    @Test
    void getCurrencyByCode_CacheMiss_RepositoryMiss() {
        String code = "GBP";

        when(valueOperations.get("currencies:" + code)).thenReturn(null);
        when(currencyRepository.findTopByBaseOrderByDateDesc(code.toUpperCase())).thenReturn(null);

        assertThrows(CurrencyNotFoundException.class, () -> currencyService.getCurrencyByCode(code));
    }

    @Test
    void getCurrencyHistory_ValidInput() {
        String code = "JPY";
        int periodHours = 24;
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusHours(periodHours);

        List<Currency> currencyHistory = Arrays.asList(new Currency(), new Currency());

        when(currencyRepository.findByBaseAndDateBetweenOrderByDateDesc(
                eq(code.toUpperCase()), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(currencyHistory);

        List<Currency> result = currencyService.getCurrencyHistory(code, periodHours);

        assertEquals(currencyHistory, result);
        verify(currencyRepository).findByBaseAndDateBetweenOrderByDateDesc(
                eq(code.toUpperCase()), any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void getCurrencyHistory_EmptyResult() {
        String code = "CAD";
        int periodHours = 12;

        when(currencyRepository.findByBaseAndDateBetweenOrderByDateDesc(
                anyString(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        assertThrows(CurrencyNotFoundException.class, () -> currencyService.getCurrencyHistory(code, periodHours));
    }

    @Test
    void getCurrencyHistory_NullCode() {
        assertThrows(IllegalArgumentException.class, () -> currencyService.getCurrencyHistory(null, 24));
    }

    @Test
    void getCurrencyHistory_EmptyCode() {
        assertThrows(IllegalArgumentException.class, () -> currencyService.getCurrencyHistory("  ", 24));
    }

    @Test
    void getCurrencyHistory_NonPositivePeriod() {
        assertThrows(IllegalArgumentException.class, () -> currencyService.getCurrencyHistory("USD", 0));
        assertThrows(IllegalArgumentException.class, () -> currencyService.getCurrencyHistory("USD", -1));
    }
}
