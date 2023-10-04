package com.sb.cardgame;

import com.sb.cardgame.enums.Suit;
import com.sb.cardgame.model.Card;
import com.sb.cardgame.model.Event;
import com.sb.cardgame.model.Player;
import com.sb.cardgame.model.SuitContainer;
import com.sb.cardgame.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class GameServiceTest {

    private GameService gameService;

    @BeforeEach
    public void setup() {
        gameService = new GameService();
    }

    @Test
    public void initGameTest() {
        String message = gameService.initGame();
        assertEquals("Initialized new game", message);

        message = gameService.initGame();
        assertEquals("Game already in progress", message);
    }

    @Test
    public void initDeckTest() {
        String message = gameService.initDeck();
        assertEquals("Game does not exist - unable to initalize deck. Call initGame api and try again.", message);

        gameService.initGame();
        message = gameService.initDeck();
        assertEquals("New deck initialized", message);
    }

    @Test
    public void addPlayerTest() {
        String message = gameService.addPlayer(false);
        assertEquals("Game does not exist - unable to add player. Call initGame api and try again.", message);

        gameService.initGame();
        message = gameService.addPlayer(false);
        assertEquals("Added new player with ID 3", message);
    }

    @Test
    public void getAllPlayersTest() {
        List<Player> players = gameService.getAllPlayers();
        assertTrue(CollectionUtils.isEmpty(players));

        gameService.initGame();
        players = gameService.getAllPlayers();
        assertEquals(2, players.size());
    }

    @Test
    public void getPlayerTest() {
        Player player = gameService.getPlayer(1);
        assertNull(player);

        gameService.initGame();
        player = gameService.getPlayer(1);
        assertEquals(1, player.getId());
    }

    @Test
    public void getRemainingCardsTest() {
        Map<Integer, Map<Suit, List<Card>>> availableCardsBySuitByDeck = gameService.getRemainingCards();
        assertTrue(CollectionUtils.isEmpty(availableCardsBySuitByDeck));

        gameService.initGame();
        availableCardsBySuitByDeck = gameService.getRemainingCards();

        assertFalse(CollectionUtils.isEmpty(availableCardsBySuitByDeck));
    }

    @Test
    public void dealCardTest() {
        String message = gameService.dealCard(1);
        assertEquals("Player with ID 1 does not exist", message);

        gameService.initGame();
        message = gameService.dealCard(1);
        assertTrue(message.startsWith("Added card"));
        assertTrue(message.endsWith("to player with ID 1"));
    }

    @Test
    public void getNbCardsAvailablePerSuitTest() {
        List<SuitContainer> suitList = gameService.getNbCardsAvailablePerSuit();
        assertTrue(CollectionUtils.isEmpty(suitList));

        gameService.initGame();
        suitList = gameService.getNbCardsAvailablePerSuit();
        assertEquals(4, suitList.size());
    }

    @Test
    public void getPlayerCardsTest() {
        List<Card> cards = gameService.getPlayerCards(1);
        assertTrue(CollectionUtils.isEmpty(cards));

        gameService.initGame();
        cards = gameService.getPlayerCards(1);
        assertEquals(5, cards.size());
    }

    @Test
    public void getEventsTest() {
        List<Event> events = gameService.getEvents();
        assertTrue(CollectionUtils.isEmpty(events));

        gameService.initGame();
        events = gameService.getEvents();
        assertEquals(14, events.size());
    }
}
