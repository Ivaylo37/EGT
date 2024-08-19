package com.example.egt.services;

import com.example.egt.exceptions.CurrencyNotFoundException;
import com.example.egt.models.Currency;
import com.example.egt.repos.CurrencyRepository;
import com.example.egt.services.contracts.CurrencyService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {


    private final CurrencyRepository currencyRepository;
    private final RedisTemplate<String, Currency> redisTemplate;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository, RedisTemplate<String, Currency> redisTemplate) {
        this.currencyRepository = currencyRepository;
        this.redisTemplate = redisTemplate;
    }

    @Cacheable(value = "currencies", key = "#code")
    @Override
    public Currency getCurrencyByCode(String code) {
        String cacheKey = "currencies:" + code;
        Currency cachedCurrency = redisTemplate.opsForValue().get(cacheKey);

        if (cachedCurrency != null) {
            return cachedCurrency;
        }

        Currency currency = currencyRepository.findTopByBaseOrderByDateDesc(code.toUpperCase());
        if (currency != null) {
            redisTemplate.opsForValue().set(cacheKey, currency);
        } else {
            throw new CurrencyNotFoundException(code);
        }
        return currency;
    }

    @Override
    public List<Currency> getCurrencyHistory(String code, int periodHours) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Currency code cannot be null or empty");
        }
        if (periodHours <= 0) {
            throw new IllegalArgumentException("Period hours must be a positive integer");
        }

        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusHours(periodHours);

        List<Currency> currencyHistory = currencyRepository.findByBaseAndDateBetweenOrderByDateDesc(
                code.toUpperCase(), startDate, endDate);

        if (currencyHistory.isEmpty()) {
            throw new CurrencyNotFoundException("No currency data found for code: " + code);
        }

        return currencyHistory;
    }
}
