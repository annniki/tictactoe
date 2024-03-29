package com.tictactoe.tictactoe.controller;

import com.tictactoe.tictactoe.DTO.PlayerDTO;
import com.tictactoe.tictactoe.domain.Player;
import com.tictactoe.tictactoe.service.PlayerService;
import com.tictactoe.tictactoe.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    /**
     * Create user account
     * @param newPlayerDTO
     * @return Player
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Player createAccount(@RequestBody PlayerDTO newPlayerDTO) {
        Player newPlayer = playerService.createNewPlayer(newPlayerDTO);
        return newPlayer;
    }

    /**
     * get list of players
     */
    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public void getPlayers() {
        playerService.listPlayers();
    }

    /**
     * get logged player
     * @return logged player
     */
    @RequestMapping(value = "/logged", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Player> getLoggedPlayer() {
        return new Response<>(playerService.getLoggedUser(), Response.Status.CREATED);
    }

}
