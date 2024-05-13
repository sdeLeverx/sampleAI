package com.leverx.sampleai.controller.embeddingsapi.retreivalaugmentedgeneration;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RagController {
    private final RagService ragService;

    @GetMapping("/ai/rag")
    public AssistantMessage generate(
            @RequestParam(value = "message", defaultValue = "What bike is good for city commuting?") String message) {
        return ragService.retrieve(message);
    }
}
