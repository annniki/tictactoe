package com.tictactoe.tictactoe.repository;

import com.tictactoe.tictactoe.domain.Game;
import com.tictactoe.tictactoe.domain.Player;
import com.tictactoe.tictactoe.domain.Step;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StepRepository extends CrudRepository<Step, Long> {

    List<Step> findByGame(Game game);
    List<Step> findByGameAndPlayer(Game game, Player player);
    int countByGameAndPlayer(Game game, Player player);
}
