package com.sb.cardgame.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Game implements Serializable {

    public static final long serialVersionUID = -5948395138293377792L;

    private List<Player> players;
    private GameDeck gameDeck;
    private List<Event> events;

    @Builder.Default
    private int lastPlayerId = 0;

    public void addPlayer(Player player) {
        if(CollectionUtils.isEmpty(players)) {
            players = new ArrayList<>();
        }

        lastPlayerId = player.getId();
        players.add(player);
    }

    public Integer getNbPlayers() {
        return CollectionUtils.isEmpty(players) ? 0 : players.size();
    }

    public Player getPlayer(int playerId) {
        if(CollectionUtils.isEmpty(players)) {
            return null;
        }

        return players.stream().filter(player -> player.getId().equals(playerId)).findFirst().orElse(null);
    }

    public boolean removePlayer(int playerId) {
        Player player = getPlayer(playerId);

        if(player != null) {
            players.remove(player);
            return true;
        }

        return false;
    }

    public void addEvent(Event event) {
        if(CollectionUtils.isEmpty(events)) {
            events = new ArrayList<>();
        }

        events.add(event);
    }
}
