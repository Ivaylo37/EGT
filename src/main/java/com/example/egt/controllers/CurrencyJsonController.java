package com.example.egt.controllers;

import com.example.egt.models.Currency;
import com.example.egt.models.requestDtos.CurrentRequestDto;
import com.example.egt.services.contracts.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public Currency getCurrency(@RequestBody CurrentRequestDto currentRequestDto) {
        return currencyService.getCurrencyByCode(currentRequestDto.getBase());
    }
}
