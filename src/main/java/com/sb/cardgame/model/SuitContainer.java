package com.sb.cardgame.model;

import com.sb.cardgame.enums.Suit;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuitContainer {

    private Suit suit;
    private int nbCardsLeft;
}
