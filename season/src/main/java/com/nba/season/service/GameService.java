package com.nba.season.service;

import com.nba.season.DTO.ClassificationDTO;
import com.nba.season.DTO.ClassificationTeamDTO;
import com.nba.season.DTO.GameDTO;
import com.nba.season.DTO.GameEnddedDTO;
import com.nba.season.entity.Game;
import com.nba.season.entity.Team;
import com.nba.season.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class GameService {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    TeamService teamService;

    public void generateGame(LocalDateTime firstRound){
        final List<Team> teams = teamService.findAll();
        List<Team> all1 = new ArrayList<>();
        List<Team> all2 = new ArrayList<>();
        all1.addAll(teams);
        all2.addAll(teams);

        gameRepository.deleteAll();

        List<Game> games = new ArrayList<>();

        int t = teams.size();
        int m = teams.size() / 2;
        LocalDateTime gameDate = firstRound;

        Integer round = 0;

        for (int i = 0; i < t - 1; i++){
            round = i + 1;

            for (int j = 0; j < m ; j++){
                Team team1;
                Team team2;
                if(j % 2 == 1 || i% 2 == 1 && j ==0) {
                    team1 = teams.get(t - j -1);
                    team2 = teams.get(j);
                } else  {
                    team1 = teams.get(j);
                    team2 = teams.get(t - j -1);
                }
                if (teams == null) {
                    System.out.println("Time 1 null");
                }
                games.add(generateGame(gameDate, round, team1, team2));
                gameDate = gameDate.plusDays(7);
            }
        teams.add(1, teams.remove(teams.size() - 1));
        }
        games.forEach(game -> System.out.println(game));

        gameRepository.saveAll(games);

        List<Game> games2 = new ArrayList<>();

        games.forEach(game -> {
            Team team1 = game.getHome();
            Team team2 = game.getVisitors();
            games2.add(generateGame(game.getGameDate().plusDays(7 * games.size()), game.getRound() + games.size(),team1,team2));
        });
        gameRepository.saveAll(games);
    }

    private Game generateGame(LocalDateTime gameDate, Integer round, Team team1, Team team2){
        Game game = new Game();
        game.setVisitors(team2);
        game.setHome(team1);
        game.setRound(round);
        game.setEndded(false);
        game.setHBasquet(0);
        game.setVBasquet(0);
        game.setCrowdPublic(0);
        return game;
    }
    private GameDTO entityToDTO(Game entity) {
        GameDTO dto = new GameDTO();
        dto.setId(entity.getId());
        dto.setGameDate(entity.getGameDate());
        dto.setEndded(entity.getEndded());
        dto.setRound(entity.getRound());
        dto.setCrowdPublic(entity.getCrowdPublic());
        dto.setHBasquet(entity.getHBasquet());
        dto.setVBasquet(entity.getVBasquet());
        dto.setHome(teamService.toDto(entity.getHome()));
        dto.setVisitors(teamService.toDto(entity.getVisitors()));
        return dto;
    }

    private Game dtoToEntity(GameDTO game) {
        Game entity = new Game();
        entity.setId(game.getId());
        entity.setGameDate(game.getGameDate());
        entity.setEndded(game.getEndded());
        entity.setHome(game.getHome());
        entity.setVisitors(game.getVisitors());
        entity.setRound(game.getRound());
        entity.setCrowdPublic(game.getCrowdPublic());
        entity.setHBasquet(game.getHBasquet());
        entity.setVBasquet(game.getVBasquet());
        return entity;
    }
    public List<GameDTO> listGames() {
        return gameRepository.findAll().stream().map(entity -> entityToDTO(entity)).collect(Collectors.toList());
    }
    public GameDTO endded( Integer id, GameEnddedDTO gameDTO) throws Exception {
    Optional <Game> optionalGame = gameRepository.findById(id);
        if(optionalGame.isPresent()){
           final Game game = optionalGame.get();
           game.setVBasquet(gameDTO.getVBasquet());
           game.setHBasquet(game.getHBasquet());
           game.setEndded(true);
           game.setCrowdPublic(gameDTO.getCrowdPublic());
           return entityToDTO(gameRepository.save(game));
        } else {
            throw new Exception("Don't exist");
        }
    }


   public ClassificationDTO obtainClassification() {
        ClassificationDTO classificationDTO = new ClassificationDTO();
        final List<Team> teams = teamService.findAll();
            teams.forEach(team -> {
               final List<Game> allGamesHome = gameRepository.findByHomeEndded(team, true);
               final List<Game> allGamesVisitant  = gameRepository.findByVisitantEndded(team, true);
                AtomicReference<Integer> victory = new AtomicReference<>(0);
                AtomicReference<Integer> draws = new AtomicReference<>(0);
                AtomicReference<Integer> losses = new AtomicReference<>(0);
                AtomicReference<Integer> MadePoints = new AtomicReference<>(0);
                AtomicReference<Integer> SofferedPoints = new AtomicReference<>(0);

                allGamesHome.forEach(game -> {
                    if(game.getHBasquet() > game.getVBasquet()) {
                        victory.getAndSet(victory.get() + 1);
                    } else if (game.getHBasquet() < game.getVBasquet()){
                        losses.getAndSet(losses.get() + 1);
                    } else {
                        draws.getAndSet(draws.get() + 1);
                    }
                    MadePoints.set(MadePoints.get() + game.getHBasquet());
                    SofferedPoints.set(SofferedPoints.get() + game.getVBasquet());
            });

                allGamesVisitant.forEach(game -> {
                    if(game.getHBasquet() < game.getVBasquet()) {
                        victory.getAndSet(victory.get() + 1);
                    } else if (game.getHBasquet() > game.getVBasquet()){
                        losses.getAndSet(losses.get() + 1);
                    } else {
                        draws.getAndSet(draws.get() + 1);
                    }
                    MadePoints.set(MadePoints.get() + game.getVBasquet());
                    SofferedPoints.set(SofferedPoints.get() + game.getHBasquet());

                    });
                    ClassificationTeamDTO classificationTeamDTO = new ClassificationTeamDTO();
                    classificationTeamDTO.setIdTeam(team.getId());
                    classificationTeamDTO.setTeam(team.getName());
                classificationTeamDTO.setLosses(losses.get());
                classificationTeamDTO.setDraw(draws.get());
                classificationTeamDTO.setVictory(victory.get() * 3);
                classificationTeamDTO.setPoints((victory.get() * 3) + draws.get());
                classificationTeamDTO.setPointsScored(classificationTeamDTO.getPointsScored());
                classificationTeamDTO.setPointsSoffered(classificationTeamDTO.getPointsSoffered());
                classificationTeamDTO.setGames(losses.get() + draws.get() + victory.get());
                classificationDTO.getTeams().add(classificationTeamDTO);
            });

       Collections.sort(classificationDTO.getTeams(), Collections.reverseOrder());
            int position = 0;
            for(ClassificationTeamDTO team : classificationDTO.getTeams()) {
            team.setPosition(position++);
        }
                return classificationDTO;
        }
            public GameDTO obtainGames(Integer id){
                return entityToDTO(gameRepository.findById(id).get());
    }



}
