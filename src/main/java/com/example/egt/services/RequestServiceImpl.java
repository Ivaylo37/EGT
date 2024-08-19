package com.example.egt.services;

import com.example.egt.exceptions.DuplicateRequestException;
import com.example.egt.models.Request;
import com.example.egt.models.requestDtos.CommandDTO;
import com.example.egt.models.requestDtos.CurrentRequestDTO;
import com.example.egt.models.requestDtos.HistoryRequestDTO;
import com.example.egt.repos.RequestRepository;
import com.example.egt.services.contracts.RequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }



    @Override
    public void processRequest(CurrentRequestDTO requestDTO) {
        checkDuplicateRequest(requestDTO);
        Request request = new Request();
        request.setRequestId(requestDTO.getRequestId());
        request.setTimestamp(LocalDateTime.now());
        request.setClientId(requestDTO.getClientId());
        request.setBaseCurrency(requestDTO.getBase());
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
    }

}
