package com.example.egt.services;

import com.example.egt.models.Statistics;
import com.example.egt.repos.StatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

class StatisticServiceImplTest {

    @Mock
    private StatisticsRepository statisticsRepository;

    @InjectMocks
    private StatisticServiceImpl statisticService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRecordStatistics() {
        String serviceName = "TestService";
        String requestId = "12345";
        LocalDateTime timestamp = LocalDateTime.now();
        String clientId = "client1";

        statisticService.recordStatistics(serviceName, requestId, timestamp, clientId);

        ArgumentCaptor<Statistics> statisticsCaptor = ArgumentCaptor.forClass(Statistics.class);
        verify(statisticsRepository).save(statisticsCaptor.capture());

        Statistics capturedStatistics = statisticsCaptor.getValue();
        assertNotNull(capturedStatistics);
        assertEquals(serviceName, capturedStatistics.getServiceName());
        assertEquals(requestId, capturedStatistics.getRequestId());
        assertEquals(timestamp, capturedStatistics.getTimestamp());
        assertEquals(clientId, capturedStatistics.getClientId());
    }
}
