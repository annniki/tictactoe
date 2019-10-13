package com.tictactoe.tictactoe.service;

import com.tictactoe.tictactoe.domain.Game;
import com.tictactoe.tictactoe.domain.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class GameLogic {

    private final Game game;

    public GameLogic(Game game) {
        this.game = game;
    }

    /**
     * Checks if the player wins
     *
     * @param positions Board positions from player steps retrieved from database
     * @return true or false if the player is a winner
     */
    static boolean isWinner(List<Position> positions) {

        return getWinningPositions().stream().anyMatch(positions::containsAll);
    }

    /**
     * Stores list of winning positions.
     *
     * @return the list of the winning position's list
     */
    static List<List<Position>> getWinningPositions() {
        List<List<Position>> winingPositions = new ArrayList<>();

        winingPositions.add(asList(new Position(1, 1), new Position(1, 2), new Position(1, 3)));
        winingPositions.add(asList(new Position(2, 1), new Position(2, 2), new Position(2, 3)));
        winingPositions.add(asList(new Position(3, 1), new Position(3, 2), new Position(3, 3)));

        winingPositions.add(asList(new Position(1, 1), new Position(2, 1), new Position(3, 1)));
        winingPositions.add(asList(new Position(1, 2), new Position(2, 2), new Position(3, 2)));
        winingPositions.add(asList(new Position(1, 3), new Position(2, 3), new Position(3, 3)));

        winingPositions.add(asList(new Position(1, 1), new Position(2, 2), new Position(3, 3)));
        winingPositions.add(asList(new Position(3, 1), new Position(2, 2), new Position(1, 3)));

        return winingPositions;
    }

    /**
     * @param numberOfFirstPlayerStepsInGame
     * @param numberOfSecondPlayerStepsInGame
     * @return true or false depending on the count of the player's moves
     */
    static boolean playerTurn(int numberOfFirstPlayerStepsInGame, int numberOfSecondPlayerStepsInGame, boolean for_first_player) {
        return (for_first_player && (numberOfFirstPlayerStepsInGame == numberOfSecondPlayerStepsInGame || numberOfFirstPlayerStepsInGame == 0)
                || ((!for_first_player && (numberOfFirstPlayerStepsInGame != numberOfSecondPlayerStepsInGame))));
    }

    static boolean isBoardIsFull(List<Position> takenPositions) {
        return takenPositions.size() == 9;
    }

}
