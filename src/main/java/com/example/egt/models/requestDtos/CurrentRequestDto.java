package com.example.egt.models.requestDtos;

import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty
    String requestId;

    @NotEmpty
    LocalDateTime requestDate;

    @NotEmpty
    int clientId;

    @NotEmpty
    String base;
}
