package com.tictactoe.tictactoe.security;

import com.tictactoe.tictactoe.domain.Player;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.google.common.collect.ImmutableSet;

public class ContextUser extends org.springframework.security.core.userdetails.User {

    private final Player player;

    public ContextUser(Player player) {
        super(player.getUserName(),
                player.getPassword(),
                true,
                true,
                true,
                true,
                ImmutableSet.of(new SimpleGrantedAuthority("create")));

        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
