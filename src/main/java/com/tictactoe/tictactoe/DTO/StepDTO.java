package com.tictactoe.tictactoe.DTO;

import com.tictactoe.tictactoe.enums.GameStatus;
import com.tictactoe.tictactoe.enums.Piece;

import java.util.Date;

public class StepDTO {

    private int boardColumn;
    private int boardRow;
    private Date created;
    private String userName;
    private GameStatus gameStatus;
    private Piece playerPieceCode;

    public StepDTO() {
    }

    public StepDTO(int boardColumn, int boardRow, Date created, String userName, GameStatus gameStatus, Piece playerPieceCode) {
        this.boardColumn = boardColumn;
        this.boardRow = boardRow;
        this.created = created;
        this.userName = userName;
        this.gameStatus = gameStatus;
        this.playerPieceCode = playerPieceCode;
    }

    public int getBoardColumn() {
        return boardColumn;
    }

    public void setBoardColumn(int boardColumn) {
        this.boardColumn = boardColumn;
    }

    public int getBoardRow() {
        return boardRow;
    }

    public void setBoardRow(int boardRow) {
        this.boardRow = boardRow;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Piece getPlayerPieceCode() {
        return playerPieceCode;
    }

    public void setPlayerPieceCode(Piece playerPieceCode) {
        this.playerPieceCode = playerPieceCode;
    }
}
