package com.sb.cardgame.service;

import com.sb.cardgame.enums.Rank;
import com.sb.cardgame.enums.Suit;
import com.sb.cardgame.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class GameService {

    private Game game;

    public String initGame() {
        if(game == null) {
            String message = "Initialized new game";
            Event event = Event.builder().message(message).time(LocalDateTime.now()).build();

            GameDeck gameDeck = new GameDeck();

            game = Game.builder().gameDeck(gameDeck).build();
            game.addEvent(event);

            initDeck();
            IntStream.range(0, 2).forEach(idx -> addPlayer(true));

            return message;
        }

        return "Game already in progress";
    }

    public void deleteGame() {
        game = null;
    }

    public String initDeck() {
        if(game != null) {
            List<Card> cards = new ArrayList<>();

            Arrays.stream(Suit.values()).forEach(suit -> initSuit(suit, cards));

            Deck deck = Deck.builder().cards(cards).build();
            game.getGameDeck().addDeck(deck);

            String message = "New deck initialized";
            Event event = Event.builder().message(message).time(LocalDateTime.now()).build();
            game.addEvent(event);

            return message;
        }

        return "Game does not exist - unable to initalize deck. Call initGame api and try again.";
    }

    public String addPlayer(boolean initGame) {
        if(game == null) {
            return "Game does not exist - unable to add player. Call initGame api and try again.";
        }

        int nbPlayers = game.getLastPlayerId();
        Player player = Player.builder()
                .id(nbPlayers + 1)
                .name(String.format("Player %s", (nbPlayers + 1)))
                .cards(new ArrayList<>())
                .build();

        game.addPlayer(player);

        String message = String.format("Added new player with ID %s", player.getId());

        Event event = Event.builder().message(message).time(LocalDateTime.now()).build();
        game.addEvent(event);

        if(initGame) {
            IntStream.range(0, 5).forEach(idx -> {
                dealCard(player.getId());
            });
        }

        return message;
    }

    public List<Player> getAllPlayers() {
        List<Player> players = game != null ? game.getPlayers() : new ArrayList<>();

        if(!CollectionUtils.isEmpty(players)) {
            Collections.sort(players, Comparator.comparing(Player::getCardValue).reversed());
        }

        return players;
    }

    public Player getPlayer(int playerId) {
        return game != null ? game.getPlayer(playerId) : null;
    }

    public void removePlayer(int playerId) {
        if(game != null) {
            if(game.removePlayer(playerId)) {
                String message = String.format("Removed player with ID %s", playerId);
                Event event = Event.builder().message(message).time(LocalDateTime.now()).build();
                game.addEvent(event);
            }
        }
    }

    public Map<Integer, Map<Suit, List<Card>>> getRemainingCards() {
        Map<Integer, Map<Suit, List<Card>>> availableCardsBySuitByDeck = new HashMap<>();
        if(game != null && game.getGameDeck() != null && !CollectionUtils.isEmpty(game.getGameDeck().getDecks())) {
            List<Deck> decks = game.getGameDeck().getDecks();

            decks.forEach(deck -> {
                List<Card> cards = deck.getAvailableCards();
                Map<Suit, List<Card>> cardsBySuit = new HashMap<>();

                if(!CollectionUtils.isEmpty(cards)) {
                    cardsBySuit = cards.stream().collect(Collectors.groupingBy(Card::getSuit));
                    availableCardsBySuitByDeck.put(deck.getDeckId(), cardsBySuit);
                }
            });
        }

        return availableCardsBySuitByDeck;
    }

    public String dealCard(int playerId) {
        Player player = getPlayer(playerId);

        if(player != null && game != null && game.getGameDeck() != null && !CollectionUtils.isEmpty(game.getGameDeck().getDecks())) {
            List<Deck> decks = game.getGameDeck().getDecks();
            List<Card> cards = new ArrayList<>();

            for(Deck deck : decks) {
                if(!CollectionUtils.isEmpty(deck.getAvailableCards())) {
                    cards = deck.getAvailableCards();
                    break;
                }
            }

            if(!CollectionUtils.isEmpty(cards)) {
                Random random = new Random();
                int idx = random.nextInt(0, cards.size());

                cards.get(idx).setIsInUse(true);
                player.addCard(cards.get(idx));

                String message = String.format("Added card %s of %s to player with ID %s", cards.get(idx).getRank(), cards.get(idx).getSuit(), playerId);
                Event event = Event.builder().message(message).time(LocalDateTime.now()).build();
                game.addEvent(event);

                return message;
            }
        }

        if(player != null) {
            return "There are no available cards";
        }

        return String.format("Player with ID %s does not exist", playerId);
    }

    public List<SuitContainer> getNbCardsAvailablePerSuit() {
        List<SuitContainer> suitContainerList = new ArrayList<>();

        if(game != null && game.getGameDeck() != null && !CollectionUtils.isEmpty(game.getGameDeck().getDecks())) {
            List<Card> cards = new ArrayList<>();

            game.getGameDeck().getDecks().forEach(deck -> {
                List<Card> deckCards = deck.getAvailableCards();

                if(!CollectionUtils.isEmpty(deckCards)) {
                    cards.addAll(deckCards);
                }
            });

            if(!CollectionUtils.isEmpty(cards)) {
                Map<Suit, List<Card>> cardsBySuit = cards.stream().collect(Collectors.groupingBy(Card::getSuit));

                Arrays.stream(Suit.values()).forEach(suit -> {
                    List<Card> suitCards = cardsBySuit.get(suit);
                    SuitContainer suitContainer = SuitContainer.builder()
                            .suit(suit)
                            .build();

                    if(!CollectionUtils.isEmpty(suitCards)) {
                        suitContainer.setNbCardsLeft(suitCards.size());
                    } else {
                        suitContainer.setNbCardsLeft(0);
                    }

                    suitContainerList.add(suitContainer);
                });
            }
        }

        return suitContainerList;
    }

    public List<Card> getPlayerCards(int playerId) {
        Player player = getPlayer(playerId);

        if(player != null) {
            return player.getCards();
        }

        return null;
    }

    public List<Event> getEvents() {
        if(game != null) {
            List<Event> events = game.getEvents();
            Collections.sort(events, Comparator.comparing(Event::getTime));

            return events;
        }

        return null;
    }

    private void initSuit(Suit suit, List<Card> cards) {
        IntStream.range(1, 14).forEach(idx -> {
            Card card = Card.builder()
                    .suit(suit)
                    .rank(Rank.getRank(idx))
                    .isInUse(false)
                    .build();

            cards.add(card);
        });
    }
}
