package com.example.egt.controllers;

import com.example.egt.models.Currency;
import com.example.egt.models.requestDtos.CommandDTO;
import com.example.egt.models.requestDtos.GetDTO;
import com.example.egt.models.requestDtos.HistoryDTO;
import com.example.egt.services.contracts.CurrencyService;
import com.example.egt.services.contracts.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xml_api")
public class XmlApiController {

    private final CurrencyService currencyService;
    private final RequestService requestService;

    public XmlApiController(CurrencyService currencyService, RequestService requestService) {
        this.currencyService = currencyService;
        this.requestService = requestService;
    }

    @PostMapping(value = "/command", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> handleCommand(@RequestBody CommandDTO command) {
        if (command.getGet() != null) {
            requestService.processXmlRequest(command);
            return handleCurrentData(command.getGet());
        } else if (command.getHistory() != null) {
            requestService.processXmlHistoryRequest(command);
            return handleHistoricalData(command.getHistory());
        } else {
            return ResponseEntity.badRequest().body("<error>Invalid command</error>");
        }
    }

    private ResponseEntity<?> handleCurrentData(GetDTO getDto) {
        try {
            Currency currency = currencyService.getCurrencyByCode(getDto.getCurrency());
            return ResponseEntity.ok(currency);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("<error>" + e.getMessage() + "</error>");
        }
    }

    private ResponseEntity<?> handleHistoricalData(HistoryDTO historyDto) {
        try {
            List<Currency> history = currencyService.getCurrencyHistory(historyDto.getCurrency(), historyDto.getPeriod());
            return ResponseEntity.ok(history);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("<error>" + e.getMessage() + "</error>");
        }
    }
}
