package com.nba.season.controller;

import com.nba.season.DTO.GameDTO;
import com.nba.season.entity.Game;
import com.nba.season.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public ResponseEntity<List<Game>> obtain() {
        gameService.generateGame(LocalDateTime.now());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/gerar-jogos")
    public ResponseEntity<List<Game>> generateGames() {
        return ResponseEntity.ok().body(gameService.obtainGames());
    }

    @PostMapping(value = "/finaliza/{id}")
        public ResponseEntity<GameDTO> end(@PathVariable Integer id,@RequestBody GameDTO gameDTO){
            return ResponseEntity.ok().body(gameService.endded(id, gameDTO));
    }

    /*@GetMapping(value = "/classificacao/{id}")
    public ResponseEntity<ClassificationDTO> classificaco() {
        return ResponseEntity.ok().body(gameService.obtainGames(id));
    }*/

    @GetMapping(value = "/jogo/{id}")
        public ResponseEntity<GameDTO> obtainGame(@RequestBody Integer id){
        return ResponseEntity.ok().body(gameService.obtainGames(id));

    }

}
