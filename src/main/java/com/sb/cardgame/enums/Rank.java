package com.sb.cardgame.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Rank {
    ACE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13);

    @Getter
    private int value;

    @Getter
    private static Map<Integer, Rank> rankMap = new HashMap<>();

    static {
        Arrays.stream(Rank.values()).forEach(rank -> rankMap.put(rank.value, rank));
    }

    Rank(int value) {
        this.value = value;
    }

    public static Rank getRank(int value) {
        return rankMap.get(value);
    }
}
