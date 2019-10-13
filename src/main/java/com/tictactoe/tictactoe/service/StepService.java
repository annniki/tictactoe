package com.tictactoe.tictactoe.service;

import com.tictactoe.tictactoe.DTO.CreateStepDTO;
import com.tictactoe.tictactoe.DTO.StepDTO;
import com.tictactoe.tictactoe.domain.Game;
import com.tictactoe.tictactoe.domain.Player;
import com.tictactoe.tictactoe.domain.Position;
import com.tictactoe.tictactoe.domain.Step;
import com.tictactoe.tictactoe.enums.GameStatus;
import com.tictactoe.tictactoe.enums.Piece;
import com.tictactoe.tictactoe.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StepService {

    private final StepRepository stepRepository;

    @Autowired
    public StepService(StepRepository stepRepository) {
        this.stepRepository = stepRepository;
    }

    public Step createStep(Game game, Player player, CreateStepDTO createStepDTO) {
        Step step = new Step();
        step.setBoardColumn(createStepDTO.getBoardColumn());
        step.setBoardRow(createStepDTO.getBoardRow());
        step.setCreated(new Date());
        step.setPlayer(player);
        step.setGame(game);
        stepRepository.save(step);
        return step;

    }

    public Step autoCreatestep(Game game) {
        Step step = new Step();
        step.setBoardColumn(GameLogic.nextAutoStep(getTakenStepPositionsInGame(game)).getBoardColumn());
        step.setBoardRow(GameLogic.nextAutoStep(getTakenStepPositionsInGame(game)).getBoardRow());
        step.setCreated(new Date());
        step.setPlayer(null);
        step.setGame(game);

        stepRepository.save(step);

        return step;
    }

    public GameStatus checkCurrentGameStatus(Game game) {
        if (GameLogic.isWinner(getPlayerStepPositionsInGame(game, game.getFirstPlayer()))) {
            return GameStatus.FIRST_PLAYER_WON;
        } else if (GameLogic.isWinner(getPlayerStepPositionsInGame(game, game.getSecondPlayer()))) {
            return GameStatus.SECOND_PLAYER_WON;
        } else if (GameLogic.isBoardIsFull(getTakenStepPositionsInGame(game))) {
            return GameStatus.TIE;
        } else if (game.getSecondPlayer() == null) {
            return GameStatus.WAITS_FOR_PLAYER;
        } else {
            return GameStatus.IN_PROGRESS;
        }

    }

    public List<StepDTO> getStepsInGame(Game game) {

        List<Step> stepsInGame = stepRepository.findByGame(game);
        List<StepDTO> steps = new ArrayList<>();
        Piece currentPiece = game.getPlayerPieceCode();

        for (Step step : stepsInGame) {
            StepDTO stepDTO = new StepDTO();
            stepDTO.setBoardColumn(step.getBoardColumn());
            stepDTO.setBoardRow(step.getBoardRow());
            stepDTO.setCreated(step.getCreated());
            stepDTO.setGameStatus(step.getGame().getGameStatus());
            stepDTO.setUserName(step.getPlayer().getUserName());
            stepDTO.setPlayerPieceCode(currentPiece);
            steps.add(stepDTO);

            currentPiece = currentPiece == Piece.X ? Piece.O : Piece.X;
        }

        return steps;
    }

    public List<Position> getTakenStepPositionsInGame(Game game) {
        return stepRepository.findByGame(game).stream()
                .map(step -> new Position(step.getBoardRow(), step.getBoardColumn()))
                .collect(Collectors.toList());
    }

    public List<Position> getPlayerStepPositionsInGame(Game game, Player player) {

        return stepRepository.findByGameAndPlayer(game, player).stream()
                .map(step -> new Position(step.getBoardRow(), step.getBoardColumn()))
                .collect(Collectors.toList());
    }

    public int getTheNumberOfPlayerStepsInGame(Game game, Player player) {
        return stepRepository.countByGameAndPlayer(game, player);
    }

    public boolean isPlayerTurn(Game game, Player firstPlayer, Player secondPlayer, Player currentPlayer) {
        return GameLogic.playerTurn(getTheNumberOfPlayerStepsInGame(game, firstPlayer),
                getTheNumberOfPlayerStepsInGame(game, secondPlayer), currentPlayer == firstPlayer);
    }
}
