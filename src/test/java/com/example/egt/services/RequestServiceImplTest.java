package com.example.egt.services;

import com.example.egt.exceptions.DuplicateRequestException;
import com.example.egt.models.Request;
import com.example.egt.models.requestDtos.CommandDTO;
import com.example.egt.models.requestDtos.CurrentRequestDTO;
import com.example.egt.models.requestDtos.HistoryRequestDTO;
import com.example.egt.repos.RequestRepository;
import com.example.egt.services.contracts.StatisticService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RequestServiceImplTest {

    private RequestServiceImpl requestService;

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private StatisticService statisticService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        requestService = new RequestServiceImpl(requestRepository, statisticService);
    }

    @Test
    void processRequest_ShouldSaveRequestAndRecordStatistics() {
        CurrentRequestDTO dto = new CurrentRequestDTO();
        dto.setRequestId("123");
        dto.setClientId("client1");
        dto.setBase("USD");

        requestService.processRequest(dto);

        verify(requestRepository).save(any(Request.class));
        verify(statisticService).recordStatistics(eq("EXT_SERVICE_1"), eq("123"), any(LocalDateTime.class), eq("client1"));
    }

    @Test
    void processHistoryRequest_ShouldSaveRequestAndRecordStatistics() {
        HistoryRequestDTO dto = new HistoryRequestDTO();
        dto.setRequestId("456");
        dto.setClientId("client2");
        dto.setBase("EUR");
        dto.setPeriod(100);

        requestService.processHistoryRequest(dto);

        verify(requestRepository).save(any(Request.class));
        verify(statisticService).recordStatistics(eq("EXT_SERVICE_1"), eq("456"), any(LocalDateTime.class), eq("client2"));
    }

    @Test
    void checkDuplicateRequest_ShouldThrowExceptionWhenRequestExists() {
        CurrentRequestDTO dto = new CurrentRequestDTO();
        dto.setRequestId("789");

        when(requestRepository.findRequestByRequestId("789")).thenReturn(new Request());

        assertThrows(DuplicateRequestException.class, () -> requestService.checkDuplicateRequest(dto));
    }



    @Test
    void processXmlRequest_ShouldThrowExceptionWhenRequestExists() {
        CommandDTO dto = new CommandDTO();
        dto.setId("103");

        when(requestRepository.findRequestByRequestId("103")).thenReturn(new Request());

        assertThrows(DuplicateRequestException.class, () -> requestService.processXmlRequest(dto));
    }

    @Test
    void processXmlHistoryRequest_ShouldThrowExceptionWhenRequestExists() {
        CommandDTO dto = new CommandDTO();
        dto.setId("104");

        when(requestRepository.findRequestByRequestId("104")).thenReturn(new Request());

        assertThrows(DuplicateRequestException.class, () -> requestService.processXmlHistoryRequest(dto));
    }
}
