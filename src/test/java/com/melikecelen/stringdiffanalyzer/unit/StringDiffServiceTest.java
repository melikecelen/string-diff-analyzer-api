package com.melikecelen.stringdiffanalyzer.unit;

import com.melikecelen.stringdiffanalyzer.api.resource.CharacterResource;
import com.melikecelen.stringdiffanalyzer.api.resource.PrefixEnum;
import com.melikecelen.stringdiffanalyzer.service.StringDiffService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StringDiffServiceTest {

    private final StringDiffService stringDiffService = new StringDiffService();
    private final String s1 = "my&friend&Paul has heavy hats! &";
    private final String s2 = "my friend John has many many friends &";


    @Test
    void testMixBothInputsWithLowercaseChars() {
        String result = stringDiffService.mix(s1, s2);
        assertEquals("2:nnnnn/1:aaaa/1:hhh/2:mmm/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss", result);
    }

    @Test
    void testMixBothInputsWithLowercaseCharsWithoutOccurring() {
        String result = stringDiffService.mix("abc", "def");
        assertEquals("", result);
    }

    @Test
    void testMixWhenBothInputsAreEmptyOrDoesNotIncludeLowercaseChars() {
        assertEquals("", stringDiffService.mix("", ""));
        assertEquals("", stringDiffService.mix("ABC", "CDE"));
    }

    @Test
    void testMixWhenOneOfTheInputsIsEmpty() {
        String result = stringDiffService.mix("aabcd", "");
        assertEquals("1:aa", result);
    }

    @Test
    void testExtractLowercaseCharacters() {
        String result = stringDiffService.extractLowercaseCharacters(s1);
        assertEquals("myfriendaulhasheavyhats", result);

    }

    @Test
    void testCountCharacterOccurrences() {
        String result = stringDiffService.extractLowercaseCharacters(s1);
        Map<Character, Integer> characterIntegerMap = stringDiffService.countCharacterOccurrences(result);
        assertEquals(4, characterIntegerMap.get('a'));
        assertEquals(1, characterIntegerMap.get('i'));
        assertEquals(3, characterIntegerMap.get('h'));
        assertNull(characterIntegerMap.get('p'));
    }

    @Test
    void testCompareCharacterFrequency() {
        Map<Character, Integer> map1 = new HashMap<>();
        map1.put('a', 5);
        map1.put('b', 4);
        map1.put('c', 3);
        Map<Character, Integer> map2 = new HashMap<>();
        map2.put('a', 4);
        map2.put('b', 6);
        map2.put('c', 3);

        List<CharacterResource> resources = stringDiffService.compareCharacterFrequency(map1, map2);

        assertEquals('a', resources.get(0).getCharacter());
        assertEquals(5, resources.get(0).getCount());
        assertEquals('1', resources.get(0).getPrefix().getValue());

        assertEquals('b', resources.get(1).getCharacter());
        assertEquals(6, resources.get(1).getCount());
        assertEquals('2', resources.get(1).getPrefix().getValue());

        assertEquals('c', resources.get(2).getCharacter());
        assertEquals(3, resources.get(2).getCount());
        assertEquals('=', resources.get(2).getPrefix().getValue());
    }

    @Test
    void testConvertToString() {
        List<CharacterResource> resources = List.of(
                new CharacterResource(PrefixEnum.ONE, 'a', 5),
                new CharacterResource(PrefixEnum.TWO, 'b', 4),
                new CharacterResource(PrefixEnum.EQUAL, 'c', 3)
        );
        String result = stringDiffService.convertToString(resources);
        assertEquals("1:aaaaa/2:bbbb/=:ccc", result);
    }

    @Test
    void testMixInputsCanNotBeNull() {
        try {
            stringDiffService.mix(null, null);
        } catch (NullPointerException e){
            assertNotNull(e.getMessage());
        }
    }
}