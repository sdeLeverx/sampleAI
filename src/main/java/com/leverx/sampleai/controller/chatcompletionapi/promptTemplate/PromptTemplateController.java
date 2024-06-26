package com.leverx.sampleai.controller.chatcompletionapi.promptTemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PromptTemplateController {
    private final ChatClient chatClient;

    @Value("classpath:/prompts/joke-prompt.st")
    private Resource jokeResource;

    @GetMapping("/ai/promptTemplate")
    public String completion(@RequestParam(value = "adjective", defaultValue = "funny") String adjective,
                                       @RequestParam(value = "topic", defaultValue = "freelancer") String topic) {
        PromptTemplate promptTemplate = new PromptTemplate(jokeResource);
        Prompt prompt = promptTemplate.create(Map.of("adjective", adjective, "topic", topic));
        return chatClient.call(prompt).getResult().getOutput().getContent();
    }
}
