package com.example.egt.services.contracts;

import com.example.egt.models.Currency;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyService {

    public Currency getCurrencyByCode(String code);

    public List<Currency> getCurrencyHistory(String code, int periodHours);

}
