package com.tictactoe.tictactoe.repository;

import com.tictactoe.tictactoe.domain.Game;
import com.tictactoe.tictactoe.enums.GameStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {

    List<Game> findByGameStatus(GameStatus gameStatus);

}
