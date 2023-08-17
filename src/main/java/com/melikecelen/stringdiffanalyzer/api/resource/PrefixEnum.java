package com.melikecelen.stringdiffanalyzer.api.resource;

public enum PrefixEnum {
    ONE('1'), TWO('2'), EQUAL('=');

    private char value;
    PrefixEnum(char value) {
        this.value = value;
    }
    public char getValue() {
        return value;
    }
}
