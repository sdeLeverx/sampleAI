package com.leverx.sampleai.controller.chatcompletionapi.stuff;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class StuffController {
    private final ChatClient chatClient;

    @Value("classpath:/docs/wikipedia-curling.md")
    private Resource docsToStuffResource;

    @Value("classpath:/prompts/qa-prompt.st")
    private Resource qaPromptResource;

    @GetMapping("/ai/stuff")
    public Completion completion(@RequestParam(value = "message",
            defaultValue = "Which athletes won the mixed doubles gold medal in curling at the 2022 Winter Olympics?'") String message,
                                 @RequestParam(value = "stuffit", defaultValue = "false") boolean stuffit) {
        PromptTemplate promptTemplate = new PromptTemplate(qaPromptResource);
        Map<String, Object> map = new HashMap<>();
        map.put("question", message);
        if (stuffit) {
            map.put("context", docsToStuffResource);
        } else {
            map.put("context", "");
        }
        Prompt prompt = promptTemplate.create(map);
        Generation generation = chatClient.call(prompt).getResult();
        return new Completion(generation.getOutput().getContent());
    }
}
