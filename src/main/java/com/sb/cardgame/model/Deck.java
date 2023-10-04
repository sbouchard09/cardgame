package com.sb.cardgame.model;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class Deck implements Serializable {

    public static final long serialVersionUID = 3772583141798190359L;
    private int deckId;
    private List<Card> cards;

    public List<Card> getAvailableCards() {
        if(CollectionUtils.isEmpty(cards)) {
            return null;
        }

        return cards.stream().filter(card -> !card.getIsInUse()).toList();
    }
}
