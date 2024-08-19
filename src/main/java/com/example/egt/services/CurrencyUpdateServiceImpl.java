package com.example.egt.services;

import com.example.egt.models.Currency;
import com.example.egt.models.FixerApiResponse;
import com.example.egt.repos.CurrencyRepository;
import com.example.egt.services.contracts.CurrencyUpdateService;
import com.example.egt.services.contracts.FixerApiService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


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
        System.out.println("Updating Currency Rates");
        FixerApiResponse response = fixerApiService.getLatestRates();
        if (response.isSuccess()) {
            response.getRates().forEach((code, rate) -> {
                Currency currency = new Currency();
                currency.setId(currency.getId());
                currency.setTimestamp(response.getTimestamp());
                currency.setBase(response.getBase());
                currency.setDate(response.getDate().atStartOfDay());
                currency.setRates(response.getRates());
                currencyRepository.save(currency);
            });
        } else {
            System.err.println("Failed to fetch currency rates");
        }
    }
}
