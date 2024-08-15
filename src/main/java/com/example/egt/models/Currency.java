package com.example.egt.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Currency {

    private Long id;
    private String code;
    private BigDecimal rate;
    private String baseCode;
    private LocalDate date;
    private LocalDateTime lastUpdated;
}
