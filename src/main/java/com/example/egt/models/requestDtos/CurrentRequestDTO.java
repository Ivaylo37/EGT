package com.example.egt.models.requestDtos;

import com.example.egt.models.Request;
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
public class CurrentRequestDTO implements JsonRequest {

    @NotNull
    String requestId;

    @NotNull
    LocalDateTime requestDate;

    @NotNull
    String clientId;

    @NotNull
    String base;
}
