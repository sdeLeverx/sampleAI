package com.leverx.sampleai.controller.chatcompletionapi.prompt;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PromptController {
    private final ChatClient chatClient;

    @GetMapping("/ai/prompt")
    public String completion(@RequestParam(value = "message", defaultValue = "Tell me the best football club in the world") String message) {
        return chatClient.call(message);
    }
}
