package com.leverx.sampleai.controller.chatcompletionapi.outputParsers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OutputParserController {
    private final ChatClient chatClient;

    @GetMapping("/ai/output")
    public ActorsFilms generate(@RequestParam(value = "actor", defaultValue = "Jeff Bridges") String actor) {
        var outputParser = new BeanOutputParser<>(ActorsFilms.class);

        String format = outputParser.getFormat();
        log.info("format: " + format);
        String userMessage = """
                Generate the filmography for the actor {actor}.
                {format}
                """;
        PromptTemplate promptTemplate = new PromptTemplate(userMessage, Map.of("actor", actor, "format", format));
        Prompt prompt = promptTemplate.create();
        Generation generation = chatClient.call(prompt).getResult();

        return outputParser.parse(generation.getOutput().getContent());
    }
}
