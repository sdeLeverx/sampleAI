package com.leverx.sampleai.controller.chatcompletionapi.outputParsers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActorsFilms {
    private String actor;

    private List<String> movies;
}
