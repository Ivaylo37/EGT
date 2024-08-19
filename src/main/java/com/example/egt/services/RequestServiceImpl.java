package com.example.egt.services;

import com.example.egt.exceptions.DuplicateRequestException;
import com.example.egt.models.Request;
import com.example.egt.models.requestDtos.CommandDTO;
import com.example.egt.models.requestDtos.CurrentRequestDTO;
import com.example.egt.models.requestDtos.HistoryRequestDTO;
import com.example.egt.repos.RequestRepository;

import com.example.egt.services.contracts.RequestService;
import com.example.egt.services.contracts.StatisticService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RequestServiceImpl implements RequestService {

    private static final String service1 = "EXT_SERVICE_1";
    private static final String service2 = "EXT_SERVICE_2";

    private final RequestRepository requestRepository;
    private final StatisticService statisticService;

    public RequestServiceImpl(RequestRepository requestRepository, StatisticService statisticService) {
        this.requestRepository = requestRepository;
        this.statisticService = statisticService;
    }



    @Override
    public void processRequest(CurrentRequestDTO requestDTO) {
        checkDuplicateRequest(requestDTO);
        Request request = new Request();
        request.setRequestId(requestDTO.getRequestId());
        request.setTimestamp(LocalDateTime.now());
        request.setClientId(requestDTO.getClientId());
        request.setBaseCurrency(requestDTO.getBase());
        statisticService.recordStatistics(service1, request.getRequestId(), request.getTimestamp(), request.getClientId());
        requestRepository.save(request);
    }

    @Override
    public void processHistoryRequest(HistoryRequestDTO historyRequestDTO) {
        checkDuplicateRequest(historyRequestDTO);
        Request request = new Request();
        request.setRequestId(historyRequestDTO.getRequestId());
        request.setTimestamp(LocalDateTime.now());
        request.setClientId(historyRequestDTO.getClientId());
        request.setBaseCurrency(historyRequestDTO.getBase());
        request.setPeriod(historyRequestDTO.getPeriod());
        statisticService.recordStatistics(service1, request.getRequestId(), request.getTimestamp(), request.getClientId());
        requestRepository.save(request);
    }


    @Override
    public void checkDuplicateRequest(CurrentRequestDTO requestDTO) {
        Request request = requestRepository.findRequestByRequestId(requestDTO.getRequestId());
        if (request != null) {
            throw new DuplicateRequestException("This request already exists");
        }
    }

    @Override
    public void processXmlRequest(CommandDTO commandDTO) {
        Request request = requestRepository.findRequestByRequestId(commandDTO.getId());
        if (request != null) {
            throw new DuplicateRequestException("This request already exists");
        }
        request = new Request();
        request.setRequestId(commandDTO.getId());
        request.setTimestamp(LocalDateTime.now());
        request.setClientId(commandDTO.getGet().getClient());
        request.setBaseCurrency(commandDTO.getGet().getCurrency());
        statisticService.recordStatistics(service2, request.getRequestId(), request.getTimestamp(), request.getClientId());
        requestRepository.save(request);
    }

    @Override
    public void processXmlHistoryRequest(CommandDTO commandDTO) {
        Request request = requestRepository.findRequestByRequestId(commandDTO.getId());
        if (request != null) {
            throw new DuplicateRequestException("This request already exists");
        }
        request = new Request();
        request.setRequestId(commandDTO.getId());
        request.setTimestamp(LocalDateTime.now());
        request.setClientId(commandDTO.getGet().getClient());
        request.setBaseCurrency(commandDTO.getGet().getCurrency());
        request.setPeriod(commandDTO.getHistory().getPeriod());
        statisticService.recordStatistics(service2, request.getRequestId(), request.getTimestamp(), request.getClientId());
        requestRepository.save(request);
    }

}
