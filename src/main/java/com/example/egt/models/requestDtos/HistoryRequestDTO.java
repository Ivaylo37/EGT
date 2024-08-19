package com.example.egt.models.requestDtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HistoryRequestDTO {

    @NotNull
    String requestId;

    @NotNull
    LocalDateTime requestDate;

    @NotNull
    int clientId;

    @NotNull
    String base;

    @NotNull
    int period;
}
