package com.example.egt.models;

import com.example.egt.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "currencies")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private long timestamp;

    @Column(nullable = false)
    private String base;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime date;

    @ElementCollection
    @CollectionTable(name = "currency_rates", joinColumns = @JoinColumn(name = "currency_id"))
    @MapKeyColumn(name = "currency_code")
    @Column(name = "rate")
    private Map<String, BigDecimal> rates;
}