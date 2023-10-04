package com.sb.cardgame.model;

import com.sb.cardgame.enums.Rank;
import com.sb.cardgame.enums.Suit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class Card implements Serializable {

    public static final long serialVersionUID = -4954211349317476880L;

    private Suit suit;
    private Rank rank;
    private Boolean isInUse;
}
