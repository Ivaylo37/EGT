package com.example.egt.services;

import com.example.egt.models.Currency;
import com.example.egt.models.FixerApiResponse;
import com.example.egt.repos.CurrencyRepository;
import com.example.egt.services.contracts.CurrencyUpdateService;
import com.example.egt.services.contracts.FixerApiService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CurrencyUpdateServiceImpl implements CurrencyUpdateService {

    private final FixerApiService fixerApiService;
    private final CurrencyRepository currencyRepository;

    public CurrencyUpdateServiceImpl(FixerApiService fixerApiService, CurrencyRepository currencyRepository) {
        this.fixerApiService = fixerApiService;
        this.currencyRepository = currencyRepository;
    }

    @Scheduled(fixedRate = 3600000) // Run every hour (3600000 milliseconds)
    public void updateCurrencyRates() {
        FixerApiResponse response = fixerApiService.getLatestRates();
        if (response.isSuccess()) {
                Currency currency = new Currency();
                currency.setId(currency.getId());
                currency.setTimestamp(response.getTimestamp());
                currency.setBase(response.getBase());
                currency.setDate(response.getDate());
                currency.setRates(response.getRates());
                currencyRepository.save(currency);
        } else {
            // Handle error or log it
            System.err.println("Failed to fetch currency rates");
        }
    }
}
