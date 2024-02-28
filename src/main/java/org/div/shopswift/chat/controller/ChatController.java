package org.div.shopswift.chat.controller;

import org.div.shopswift.chat.dto.BaseResponse;
import org.div.shopswift.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/ask")
    public BaseResponse<String> askQuestion(@RequestParam String prompt) {
        return chatService.getChatResponse(prompt);
    }
}