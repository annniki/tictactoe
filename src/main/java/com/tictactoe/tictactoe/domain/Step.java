package com.tictactoe.tictactoe.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "STEP")
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "PLAYER_ID", nullable = false)
    private Player player;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "GAME_ID", nullable = false)
    private Game game;

    @Column(name = "BOARD_ROW", nullable = false)
    private int boardRow;

    @Column(name = "BOARD_COLUMN", nullable = false)
    private int boardColumn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED", nullable = false)
    private Date created;

    public Step() {
    }

    public Step(Player player, Game game, int boardRow, int boardColumn, Date created) {
        this.player = player;
        this.game = game;
        this.boardRow = boardRow;
        this.boardColumn = boardColumn;
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
