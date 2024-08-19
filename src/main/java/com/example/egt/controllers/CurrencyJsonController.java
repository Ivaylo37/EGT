package com.example.egt.controllers;

import com.example.egt.exceptions.CurrencyNotFoundException;
import com.example.egt.models.Currency;
import com.example.egt.models.requestDtos.CurrentRequestDto;
import com.example.egt.models.requestDtos.HistoryRequestDto;
import com.example.egt.services.contracts.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CurrencyJsonController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyJsonController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }


    @Operation(
            summary = "Get a currency's current details",
            description = "Returns the all the information related to the provided currency.",
            security = @SecurityRequirement(name = "swagger_oauth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Currency record found successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Currency.class))}),
            @ApiResponse(responseCode = "404", description = "Currency with this symbol was not found",
                    content = @Content)})
    @PostMapping("/currency")
    public ResponseEntity<?> getCurrency(@Valid @RequestBody CurrentRequestDto currentRequestDto) {
        try {
            Currency currency = currencyService.getCurrencyByCode(currentRequestDto.getBase());
            return ResponseEntity.ok(currency);
        } catch (CurrencyNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Currency not found: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        }
    }


    @Operation(
            summary = "Get a currency's history",
            description = "Returns the all the information related to the provided currency for the given period.",
            security = @SecurityRequirement(name = "swagger_oauth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Currency history found successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Currency.class))}),
            @ApiResponse(responseCode = "404", description = "Currency with this symbol was not found",
                    content = @Content)})
    @PostMapping("/history")
    public ResponseEntity<?> getCurrencyHistory(@Valid @RequestBody HistoryRequestDto historyRequestDto) {
        try {
            List<Currency> history = currencyService.getCurrencyHistory(historyRequestDto.getBase(), historyRequestDto.getPeriod());
            return ResponseEntity.ok(history);
        } catch (CurrencyNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Currency not found: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        }
    }
}
