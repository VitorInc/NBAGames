package com.nba.season.repository;

import com.nba.season.entity.Game;
import com.nba.season.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    List<Game> findByHomeEndded(Team home, Boolean endded);
    List<Game> findByVisitantEndded(Team visitant, Boolean endded);
    List<Game> findByEndded(Boolean endded);
}
