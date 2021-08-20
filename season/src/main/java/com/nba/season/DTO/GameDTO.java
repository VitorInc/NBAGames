package com.nba.season.DTO;

import com.nba.season.entity.Team;
import lombok.Data;


import java.time.LocalDateTime;
@Data
public class GameDTO {
    private Integer id;
    private Integer vBasquet;
    private Integer hBasquet;
    private Integer crowdPublic;
    private LocalDateTime gameDate;
    private Integer round;
    private Boolean endded;
    private Team visitors;
    private Team home;
}
