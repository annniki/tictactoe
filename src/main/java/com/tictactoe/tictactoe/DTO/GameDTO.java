package com.tictactoe.tictactoe.DTO;

import com.tictactoe.tictactoe.enums.Piece;

import javax.persistence.*;

public class GameDTO {

    private Long id;
    private Piece piece;

    public GameDTO() {
    }

    public GameDTO(long id, Piece piece) {
        this.id = id;
        this.piece = piece;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
