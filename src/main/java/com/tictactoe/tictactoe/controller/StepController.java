package com.tictactoe.tictactoe.controller;

import com.tictactoe.tictactoe.DTO.CreateStepDTO;
import com.tictactoe.tictactoe.DTO.StepDTO;
import com.tictactoe.tictactoe.domain.Game;
import com.tictactoe.tictactoe.domain.Position;
import com.tictactoe.tictactoe.domain.Step;
import com.tictactoe.tictactoe.service.GameService;
import com.tictactoe.tictactoe.service.PlayerService;
import com.tictactoe.tictactoe.service.StepService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/step")
public class StepController {

    @Autowired
    private StepService stepService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameService gameService;

    @Autowired
    private HttpSession httpSession;

    Logger logger = LoggerFactory.getLogger(StepController.class);

    /**
     * Method for create step
     * @param createStepDTO
     * @return Step
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Step createStep(@RequestBody CreateStepDTO createStepDTO) {
        Long gameId = (Long) httpSession.getAttribute("gameId");
        logger.info("step to insert:" + createStepDTO.getBoardColumn() + createStepDTO.getBoardRow());

        Step step = stepService.createStep(gameService.getGame(gameId), playerService.getLoggedUser(), createStepDTO);
        Game game = gameService.getGame(gameId);
        gameService.updateGameStatus(gameService.getGame(gameId), stepService.checkCurrentGameStatus(game));

        return step;
    }

    /**
     * Get list steps for current game
     * @return list steps in game
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<StepDTO> getStepsInGame() {

        Long gameId = (Long) httpSession.getAttribute("gameId");

        return stepService.getStepsInGame(gameService.getGame(gameId));
    }

    /**
     * Get list position
     * @return list positions steps
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public List<Position> validateSteps() {
        Long gameId = (Long) httpSession.getAttribute("gameId");
        return stepService.getPlayerStepPositionsInGame(gameService.getGame(gameId), playerService.getLoggedUser());
    }

    /**
     * Сhecks if the current player can make a step
     * @return true or false for step
     */
    @RequestMapping(value = "/turn", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean isPlayerTurn() {
        Long gameId = (Long) httpSession.getAttribute("gameId");
        return stepService.isPlayerTurn(gameService.getGame(gameId), gameService.getGame(gameId).getFirstPlayer(),
                gameService.getGame(gameId).getSecondPlayer(), playerService.getLoggedUser());
    }

}
