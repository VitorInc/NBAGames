package com.nba.season.controller;

import com.nba.season.DTO.TeamDTO;
import com.nba.season.entity.Team;
import com.nba.season.service.TeamService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/team")
public class TeamController {

       @Autowired
       private TeamService teamService;

       @GetMapping
       public ResponseEntity<List<TeamDTO>> getTeam(){
              return ResponseEntity.ok().body(teamService.listTeams());
        }

       @ApiOperation(value = "Obtem os dados um time")
       @GetMapping(value = "{id}")
       public ResponseEntity<TeamDTO> getTeam(@PathVariable Integer id){
              return ResponseEntity.ok().body(teamService.listPerId(id));
       }

       @PostMapping
       public ResponseEntity<TeamDTO> registerTeam(@Valid @RequestBody TeamDTO team) throws Exception {
              return ResponseEntity.ok().body(teamService.register(team));
       }


}
