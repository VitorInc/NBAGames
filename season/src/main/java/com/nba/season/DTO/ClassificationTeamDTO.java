package com.nba.season.DTO;

import lombok.Data;

@Data
public class ClassificationTeamDTO implements Comparable <ClassificationTeamDTO> {
    private String team;
    private Integer idTeam;
    private Integer position;
    private Integer points;
    private Integer games;
    private Integer victory;
    private Integer draw;
    private Integer losses;
    private Integer pointsScored;
    private Integer pointsSoffered;

    @Override
    public int compareTo(ClassificationTeamDTO o){
        return this.getPoints().compareTo(o.getPoints());
    }
}
