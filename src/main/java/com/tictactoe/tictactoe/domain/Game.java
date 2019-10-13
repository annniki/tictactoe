package com.tictactoe.tictactoe.domain;

import com.tictactoe.tictactoe.enums.GameStatus;
import com.tictactoe.tictactoe.enums.Piece;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "GAME")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FIRST_PLAYER_ID", nullable = false)
    private Player firstPlayer;

    @ManyToOne
    @JoinColumn(name = "SECOND_PLAYER_ID", nullable = true)
    private Player secondPlayer;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED", nullable = false)
    private Date created;

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    @Enumerated(EnumType.STRING)
    private Piece playerPieceCode;

    public Game() {
    }

    public Game(Player firstPlayer, Player secondPlayer, Date created, GameStatus gameStatus, Piece playerPieceCode) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.created = created;
        this.gameStatus = gameStatus;
        this.playerPieceCode = playerPieceCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
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
