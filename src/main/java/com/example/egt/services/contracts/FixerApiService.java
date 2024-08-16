package com.example.egt.services.contracts;

import com.example.egt.models.FixerApiResponse;

public interface FixerApiService {

    public FixerApiResponse getLatestRates();
}
