package com.nba.season.DTO;

import com.nba.season.entity.Team;
import lombok.Data;

@Data
public class TeamDTO extends Team {
    private Integer id;
    private String name;
    private String initials;
    private String state;
    private String stadium;
}
