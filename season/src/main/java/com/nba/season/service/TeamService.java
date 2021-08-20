package com.nba.season.service;

import com.nba.season.DTO.TeamDTO;
import com.nba.season.entity.Team;
import com.nba.season.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    public TeamDTO register(TeamDTO team) throws Exception{
        Team entity = toEntity(team);
        if(team.getId() == null){
            Integer newId = Math.toIntExact(teamRepository.count()+1);
            entity.setId(newId);
            entity = teamRepository.save(entity);
            return toDto(entity);
        }else{
            throw new Exception("Time já existe");
        }
    }

    public Team toEntity(TeamDTO team) {
        Team entity = new Team();
        entity.setId(team.getId());
        entity.setStadium(team.getStadium());
        entity.setInitials(team.getInitials());
        entity.setName(team.getName());
        entity.setState(team.getState());
        return entity;
    }



    public TeamDTO toDto(Team entity) {
        TeamDTO dto = new TeamDTO();
        dto.setId(entity.getId());
        dto.setStadium(entity.getStadium());
        dto.setInitials(entity.getInitials());
        dto.setName(entity.getName());
        dto.setState(entity.getState());
        return dto;
    }

    public List<TeamDTO> listTeams(){
       return teamRepository.findAll().stream().map(entity -> toDto(entity)).collect(Collectors.toList());
    }

    public TeamDTO listPerId(Integer id){
        return toDto(teamRepository.findById(id).get());
    }

    public List<Team> findAll(){
       return teamRepository.findAll();
    }


}
