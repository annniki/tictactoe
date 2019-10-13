package com.tictactoe.tictactoe.DTO;

import javax.validation.constraints.NotNull;

public class CreateStepDTO {

    @NotNull
    int boardRow;
    @NotNull
    int boardColumn;

    public CreateStepDTO() {
    }

    public CreateStepDTO(int boardRow, int boardColumn) {
        this.boardRow = boardRow;
        this.boardColumn = boardColumn;
    }

    public int getBoardRow() {
        return boardRow;
    }

    public void setBoardRow(int boardRow) {
        this.boardRow = boardRow;
    }

    public int getBoardColumn() {
        return boardColumn;
    }

    public void setBoardColumn(int boardColumn) {
        this.boardColumn = boardColumn;
    }
}
