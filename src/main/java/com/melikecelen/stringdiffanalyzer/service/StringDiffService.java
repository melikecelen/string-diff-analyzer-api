package com.melikecelen.stringdiffanalyzer.service;

import com.melikecelen.stringdiffanalyzer.api.resource.PrefixEnum;
import com.melikecelen.stringdiffanalyzer.api.resource.CharacterResource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StringDiffService {
    private static final int MIN_COUNT = 1;

    public String mix(String s1, String s2) {
        //extracting only lowercase characters in the given inputs
        String text1 = extractLowercaseCharacters(s1);
        String text2 = extractLowercaseCharacters(s2);

        //finding count occurring for each lowercase character
        Map<Character, Integer> map1 = countCharacterOccurrences(text1);
        Map<Character, Integer> map2 = countCharacterOccurrences(text2);

        List<CharacterResource> characterFrequencyList = compareCharacterFrequency(map1, map2);

        // Sorting results by count, prefix and alphabetically
        characterFrequencyList.sort(Comparator
                .comparing(CharacterResource::getCount)
                .reversed()
                .thenComparing(CharacterResource::getPrefix)
                .thenComparing(CharacterResource::getCharacter));

        return convertToString(characterFrequencyList);
    }

    public String extractLowercaseCharacters(String text) {
        return text.chars()
                .filter(Character::isLowerCase)
                .mapToObj(Character::toString)
                .collect(Collectors.joining());
    }

    public Map<Character, Integer> countCharacterOccurrences(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();

        for (char c : text.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        return frequencyMap;
    }

    public List<CharacterResource> compareCharacterFrequency(Map<Character, Integer> map1, Map<Character, Integer> map2) {
        List<CharacterResource> characterResourceList = new ArrayList<>();
        map1.forEach((key, value) -> {
            if (map2.containsKey(key)) {
                if (value > MIN_COUNT && value > map2.get(key)) {
                    characterResourceList.add(new CharacterResource(PrefixEnum.ONE, key, value));
                } else if (value < map2.get(key)) {
                    characterResourceList.add(new CharacterResource(PrefixEnum.TWO, key, map2.get(key)));
                } else if (value > MIN_COUNT) {
                    characterResourceList.add(new CharacterResource(PrefixEnum.EQUAL, key, value));
                }
                map2.remove(key);
            }
            else if (value > MIN_COUNT){
                characterResourceList.add(new CharacterResource(PrefixEnum.ONE, key, value));
            }
        });

        map2.forEach((key, value) -> {
            if (value > MIN_COUNT) {
                characterResourceList.add(new CharacterResource(PrefixEnum.TWO, key, value));
            }
        });

        return characterResourceList;
    }

    public String convertToString(List<CharacterResource> list) {
        return list.stream()
                .map(CharacterResource::toString)
                .collect(Collectors.joining("/"));
    }
}
