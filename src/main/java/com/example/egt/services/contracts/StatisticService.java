package com.example.egt.services.contracts;

import java.time.LocalDateTime;

public interface StatisticService {

    void recordStatistics(String service, String requestId, LocalDateTime timeStamp, String clientId);
}
