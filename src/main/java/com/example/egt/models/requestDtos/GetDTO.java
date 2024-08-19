package com.example.egt.models.requestDtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class GetDTO {
    @JacksonXmlProperty(isAttribute = true)
    private String client;

    @JacksonXmlProperty(localName = "currency")
    private String currency;
}
