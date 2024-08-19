package com.example.egt.services.contracts;

import com.example.egt.models.requestDtos.CommandDTO;
import com.example.egt.models.requestDtos.CurrentRequestDTO;
import com.example.egt.models.requestDtos.HistoryRequestDTO;

public interface RequestService {

    void checkDuplicateRequest(CurrentRequestDTO requestDTO);
    void processRequest(CurrentRequestDTO requestDTO);
    void processHistoryRequest(HistoryRequestDTO historyRequestDTO);

    void processXmlRequest(CommandDTO commandDTO);
    void processXmlHistoryRequest(CommandDTO commandDTO);


}
