# cardgame

This project is written using Java 17 and Spring Boot. No database was used (the game will restart when the app is stopped). It will run on port 8080 when it is launched. There are also unit tests for each of the methods in the GameService class.


It is a simple Rest application with the following 12 endpoints

`/cardGame/initGame`: used to initialize the game. Initializes one game with one deck and two players with 5 cards each

`cardGame/deleteGame`: deletes the game

`cardGame/initDeck`: initializes a deck

`cardGame/addPlayer`: adds a player and assigns an ID in sequence

`cardGame/getPlayer/{playerId}`: returns a player by ID with a list of their cards or null

`cardGame/getAllPlayers`: returns all players in a game with a list of their cards sorted in reverse by the total value of their hand

`cardGame/removePlayer/{playerId}`: removes a player by ID

`cardGame/getAvailableCards`: returns a map of available (not in play) cards by suit by deck

`cardGame/getNbAvailableCards`: returns a list of suits and the amount of cards left in that suit

`cardGame/dealCard/{playerId}`: deals a card at random to the player with the specified id

`cardGame/getPlayerCards/{playerId}`: returns a list of cards that a player specified by ID is holding

`cardGame/getEvents`: lists events by time
