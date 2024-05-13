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









//    @GetMapping("/ai/learn/{message}")
//    public String getMessage(@PathVariable("message") String message) {
//
//        PromptTemplate promptTemplate = new PromptTemplate("""
//        Please explain about {message}.
//        """);
//        promptTemplate.add("message", message);
//        return this.chatClient.call(promptTemplate.create()).getResult().toString();
//    }
//
//    @PostMapping("translations")
//    public ResponseEntity<TranslationResponse> translate(@RequestBody TranslationModel translationModel) {
//        BeanOutputParser<TranslationResponse> beanOutputParser = new BeanOutputParser<>(TranslationResponse.class);
//        PromptTemplate promptTemplate = new PromptTemplate("""
//                Translate the following {fromLanguage} text to {toLanguage}: {textToTranslate} {format}""");
//        promptTemplate.add("fromLanguage", translationModel.getTranslateFrom());
//        promptTemplate.add("toLanguage", translationModel.getTranslateTo());
//        promptTemplate.add("textToTranslate", translationModel.getTextToTranslate());
//
//        promptTemplate.add("format", beanOutputParser.getFormat());
//        promptTemplate.setOutputParser(beanOutputParser);
//
//        ChatResponse chatResponse = this.chatClient.call(promptTemplate.create()).getResult();
//        return ResponseEntity.ok(beanOutputParser.parse(chatResponse.getGeneration().getContent()));
//    }
}
