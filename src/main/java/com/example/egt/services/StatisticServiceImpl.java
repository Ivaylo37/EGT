package com.example.egt.services;

import com.example.egt.models.Statistics;
import com.example.egt.repos.StatisticsRepository;
import com.example.egt.services.contracts.StatisticService;

import java.time.LocalDateTime;

public class StatisticServiceImpl implements StatisticService {

    private final StatisticsRepository statisticsRepository;

    public StatisticServiceImpl(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    @Override
    public void recordStatistics(String service, String requestId, LocalDateTime timeStamp, String clientId) {
        Statistics statistics = new Statistics();
        statistics.setServiceName(service);
        statistics.setRequestId(requestId);
        statistics.setTimestamp(timeStamp);
        statistics.setClientId(clientId);
        statisticsRepository.save(statistics);
    }
}
