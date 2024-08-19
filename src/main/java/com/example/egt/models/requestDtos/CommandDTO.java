package com.example.egt.models.requestDtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "command")
public class CommandDTO {
    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(localName = "get")
    private GetDTO get;

    @JacksonXmlProperty(localName = "history")
    private HistoryDTO history;
}

