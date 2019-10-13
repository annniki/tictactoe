package com.tictactoe.tictactoe;

import com.tictactoe.tictactoe.domain.Player;
import com.tictactoe.tictactoe.repository.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
public class TictactoeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TictactoeApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(PlayerRepository playerRepository) {
        return (args) -> {
            playerRepository.save(new Player("player1", new BCryptPasswordEncoder().encode("123"), "player1.com"));
            playerRepository.save(new Player("player2", new BCryptPasswordEncoder().encode("321"), "player2.com"));
        };
    }

}
