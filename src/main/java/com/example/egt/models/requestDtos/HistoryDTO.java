package com.example.egt.models.requestDtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class HistoryDTO {
    @JacksonXmlProperty(isAttribute = true)
    private String consumer;

    @JacksonXmlProperty(isAttribute = true)
    private String currency;

    @JacksonXmlProperty(isAttribute = true)
    private int period;
}
