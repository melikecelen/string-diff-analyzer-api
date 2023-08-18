package com.melikecelen.stringdiffanalyzer.api.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CharacterResource {

    private PrefixEnum prefix;
    private Character character;
    private int count;

    @Override
    public String toString() {
        return prefix.getValue() + ":" + String.valueOf(character).repeat(count);
    }
}
