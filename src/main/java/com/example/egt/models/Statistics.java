package com.example.egt.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Statistics {

    private Long id;
    private String serviceName;
    private String requestId;
    private LocalDateTime timestamp;
    private String endClientId;
}
