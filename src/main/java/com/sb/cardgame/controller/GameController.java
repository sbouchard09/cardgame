package com.sb.cardgame.controller;

import com.sb.cardgame.enums.Suit;
import com.sb.cardgame.model.Card;
import com.sb.cardgame.model.Event;
import com.sb.cardgame.model.Player;
import com.sb.cardgame.model.SuitContainer;
import com.sb.cardgame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GameController {

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("cardGame/initGame")
    public String initGame() {
        return gameService.initGame();
    }

    @GetMapping("cardGame/deleteGame")
    public String deleteGame() {
        gameService.deleteGame();
        return "Deleted game";
    }

    @GetMapping("cardGame/initDeck")
    public String initDeck() {
        return gameService.initDeck();
    }

    @GetMapping("cardGame/addPlayer")
    public String addPlayer(@RequestParam(name = "init", required = false, defaultValue = "false") Boolean initGame) {
        return gameService.addPlayer(initGame);
    }

    @GetMapping("cardGame/getPlayer/{playerId}")
    public ResponseEntity<Object> getPlayer(@PathVariable(value = "playerId") Integer playerId) {
        Player player = gameService.getPlayer(playerId);

        if(player != null) {
            return ResponseEntity.ok().body(player);
        }

        return ResponseEntity.ok().body(String.format("Player with ID %s does not exist", playerId));
    }

    @GetMapping("cardGame/getAllPlayers")
    public ResponseEntity<Object> getAllPlayers() {
        List<Player> players = gameService.getAllPlayers();

        if(!CollectionUtils.isEmpty(players)) {
            return ResponseEntity.ok().body(players);
        }

        return ResponseEntity.ok().body("No players in this game");
    }

    @GetMapping("cardGame/removePlayer/{playerId}")
    public String removePlayer(@PathVariable(value = "playerId") Integer playerId) {
        gameService.removePlayer(playerId);

        return String.format("Player with ID %s successfully deleted", playerId);
    }

    @GetMapping("cardGame/getAvailableCards")
    public ResponseEntity<Object> getAvailableCards() {
        Map<Integer, Map<Suit, List<Card>>> cardsMap = gameService.getRemainingCards();

        if(!CollectionUtils.isEmpty(cardsMap)) {
            return ResponseEntity.ok().body(cardsMap);
        }

        return ResponseEntity.ok().body("There are no available cards");
    }

    @GetMapping("cardGame/getNbAvailableCards")
    public ResponseEntity<Object> getNbAvailableCards() {
        List<SuitContainer> suitList = gameService.getNbCardsAvailablePerSuit();

        if(!CollectionUtils.isEmpty(suitList)) {
            return ResponseEntity.ok().body(suitList);
        }

        return ResponseEntity.ok().body("There are no available cards");
    }

    @GetMapping("cardGame/dealCard/{playerId}")
    public String dealCard(@PathVariable(value = "playerId") Integer playerId) {
        return gameService.dealCard(playerId);
    }

    @GetMapping("cardGame/getPlayerCards/{playerId}")
    public ResponseEntity<Object> getPlayerCards(@PathVariable(value = "playerId") Integer playerId) {
        List<Card> cards = gameService.getPlayerCards(playerId);

        if(!CollectionUtils.isEmpty(cards)) {
            return ResponseEntity.ok().body(cards);
        }

        return ResponseEntity.ok().body(String.format("The player with ID %s has no cards", playerId));
    }

    @GetMapping("cardGame/getEvents")
    public ResponseEntity<Object> getEvents() {
        List<Event> events = gameService.getEvents();

        if(!CollectionUtils.isEmpty(events)) {
            return ResponseEntity.ok().body(events);
        }

        return ResponseEntity.ok().body("No events have occurred");
    }

}
