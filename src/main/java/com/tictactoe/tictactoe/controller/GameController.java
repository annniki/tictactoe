package com.tictactoe.tictactoe.controller;


import com.tictactoe.tictactoe.DTO.GameDTO;
import com.tictactoe.tictactoe.domain.Game;
import com.tictactoe.tictactoe.service.GameService;
import com.tictactoe.tictactoe.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    PlayerService playerService;

    @Autowired
    HttpSession httpSession;

    Logger logger = LoggerFactory.getLogger(GameController.class);

    /**
     * Create game
     * @param gameDTO
     * @return Game
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Game createNewGame(@RequestBody GameDTO gameDTO) {

        Game game = gameService.createNewGame(playerService.getLoggedUser(), gameDTO);
        httpSession.setAttribute("gameId", game.getId());

        logger.info("new game id: " + httpSession.getAttribute("gameId") + " stored in session");

        return game;
    }

    /**
     * Get list of games available for joining
     * @return list of games
     */
    @RequestMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Game> getGamesToJoin() {
        return gameService.getGamesToJoin(playerService.getLoggedUser());
    }

    /**
     * Method for join to existing game
     * @param gameDTO
     * @return Game
     */
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public Game joinGame(@RequestBody GameDTO gameDTO) {
        Game game = gameService.joinGame(playerService.getLoggedUser(), gameDTO);
        return game;
    }

    /**
     * List of games for player
     * @return list of games
     */
    @RequestMapping(value = "/player/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Game> getPlayerGames() {
        return gameService.getPlayerGames(playerService.getLoggedUser());
    }

    /**
     * Game properties
     * @param id
     * @return Game
     */
    @RequestMapping(value = "/{id}")
    public Game getGameProperties(@PathVariable Long id) {
        httpSession.setAttribute("gameId", id);
        return gameService.getGame(id);
    }
}
