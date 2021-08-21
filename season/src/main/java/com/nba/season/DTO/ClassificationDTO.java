package com.nba.season.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClassificationDTO {

    private List<ClassificationTeamDTO> teams = new ArrayList<>();

}
