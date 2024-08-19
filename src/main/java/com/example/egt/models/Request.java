package com.example.egt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @Column(name = "request_id",unique=true)
    private String requestId;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String clientId;

    @Column(name = "base_currency", nullable = false, length = 3)
    private String baseCurrency;

    @Column
    private Integer period; // for history requests

}
