package com.example.egt.services;

import com.example.egt.models.requestDtos.JsonRequest;
import com.example.egt.repos.RequestRepository;
import com.example.egt.services.contracts.RequestService;

public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public void processRequest(JsonRequest jsonRequest) {

    }
}
