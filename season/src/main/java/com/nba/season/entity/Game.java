package com.nba.season.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Game {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Integer vBasquet;
  private Integer hBasquet;
  private Integer crowdPublic;
  private LocalDateTime gameDate;
  private Integer round;
  private Boolean endded;

  @ManyToOne
  @JoinColumn(name="visitors")
  private Team visitors;
  @ManyToOne
  @JoinColumn(name="home")
  private Team home;
}
