package com.example.egt.models.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class CurrentRequestDto {

    String requestId;
    LocalDateTime requestDate;
    int clientId;
    String currency;
}
