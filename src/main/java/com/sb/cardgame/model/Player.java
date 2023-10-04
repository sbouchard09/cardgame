package com.sb.cardgame.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Player implements Serializable {

    public static final long serialVersionUID = -519145916985034790L;

    private Integer id;
    private String name;

    @Builder.Default
    private int cardValue = 0;
    private List<Card> cards;

    public void addCard(Card card) {
        if(CollectionUtils.isEmpty(cards)) {
            cards = new ArrayList<>();
        }

        cardValue = cardValue + card.getRank().getValue();

        cards.add(card);
    }
}
