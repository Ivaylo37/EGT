package com.example.egt.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class FixerApiResponse {

    private boolean success;
    private long timestamp;
    private String base;
    private LocalDate date;
    private Map<String, BigDecimal> rates;
}
