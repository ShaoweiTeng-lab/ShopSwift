package org.div.shopswift.chat.dto;

import lombok.Data;

import java.util.List;

@Data
public class OpenAIRequest {
    private String model;
    private List<Message> messages;
    private double temperature;
}
