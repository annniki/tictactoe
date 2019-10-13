package com.tictactoe.tictactoe.service;


import com.tictactoe.tictactoe.DTO.GameDTO;
import com.tictactoe.tictactoe.domain.Game;
import com.tictactoe.tictactoe.domain.Player;
import com.tictactoe.tictactoe.enums.GameStatus;
import com.tictactoe.tictactoe.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GameService {

    private final GameRepository gameRepository;


    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createNewGame(Player player, GameDTO gameDTO) {
        Game game = new Game();
        game.setFirstPlayer(player);
        game.setPlayerPieceCode(gameDTO.getPiece());
        game.setGameStatus(GameStatus.WAITS_FOR_PLAYER);
        game.setCreated(new Date());
        gameRepository.save(game);

        return game;
    }

    public Game updateGameStatus(Game game, GameStatus gameStatus) {
        Game g = getGame(game.getId());
        g.setGameStatus(gameStatus);

        return g;
    }

    public List<Game> getGamesToJoin(Player player) {
        return gameRepository.findByGameStatus(
                GameStatus.WAITS_FOR_PLAYER).stream().filter(game -> game.getFirstPlayer() != player).collect(Collectors.toList());

    }


    public Game joinGame(Player player, GameDTO gameDTO) {
        Game game = getGame((long) gameDTO.getId());
        game.setSecondPlayer(player);
        gameRepository.save(game);

        updateGameStatus(game, GameStatus.IN_PROGRESS);

        return game;

    }

    public List<Game> getPlayerGames(Player player) {
        return gameRepository.findByGameStatus(
                GameStatus.IN_PROGRESS).stream().filter(game -> game.getFirstPlayer() == player || game.getSecondPlayer() == player).collect(Collectors.toList());
    }

    public Game getGame(Long id) {
        return gameRepository.findById(id).orElse(null);
    }

}
