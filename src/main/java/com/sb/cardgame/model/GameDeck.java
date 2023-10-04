package com.sb.cardgame.model;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class GameDeck implements Serializable {

    public static final long serialVersionUID = -5839876375121116851L;

    private List<Deck> decks;

    public void addDeck(Deck deck) {
        if(CollectionUtils.isEmpty(decks)) {
            decks = new ArrayList<>();
        }

        int nbDecks = decks.size();
        deck.setDeckId(nbDecks);

        decks.add(deck);
    }
}
