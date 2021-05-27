package com.elis.footballmanager.controller;

import com.elis.footballmanager.dto.match.GameCreationResponseDTO;
import com.elis.footballmanager.dto.player.PlayerListDTO;
import com.elis.footballmanager.dto.team.TeamCreationRequestDTO;
import com.elis.footballmanager.dto.team.TeamCreationResponseDTO;
import com.elis.footballmanager.dto.team.TeamDTO;
import com.elis.footballmanager.dto.team.TeamListDTO;
import com.elis.footballmanager.service.interfaces.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class TeamController {
    @Autowired
    TeamService teamService;

    //Team base query
    @GetMapping("/{tenantId}/getTeams")
    public TeamListDTO getTenantTeams(@PathVariable("tenantId") Long tenantId){
        return teamService.getTenantTeams(tenantId);
    }

    @PostMapping("/{tenantId}/createTeam")
    public TeamCreationResponseDTO createTenantTeam(@PathVariable("tenantId") Long tenantId, @RequestBody TeamCreationRequestDTO teamCreationRequestDTO){
        return teamService.createTenantTeam(tenantId, teamCreationRequestDTO);
    }

    @GetMapping("/{tenantId}/getTeam/{teamId}")
    public TeamDTO getTenantTeam(@PathVariable("tenantId") Long tenantId, @PathVariable("teamId") Long teamId){
        return teamService.getTenantTeam(tenantId, teamId);
    }

    @PutMapping("/{tenantId}/updateTeam/{teamId}")
    public TeamCreationResponseDTO updateTenantTeam(@PathVariable("tenantId") Long tenantId, @PathVariable("teamId") Long teamId, @RequestBody TeamCreationRequestDTO teamCreationRequestDTO){
        return teamService.updateTenantTeam(tenantId, teamId, teamCreationRequestDTO);
    }

    @DeleteMapping("/{tenantId}/deleteTeam/{teamId}")
    public TeamDTO deleteTenantTeam(@PathVariable("tenantId") Long tenantId, @PathVariable("teamId") Long teamId){
        return teamService.deleteTenantTeam(tenantId, teamId);
    }

    @GetMapping("/{tenantId}/{gameId}/getTeamPlayers/{teamId}")
    public PlayerListDTO getTeamPlayers(@PathVariable("tenantId") Long tenantId, @PathVariable("gameId") Long gameId, @PathVariable("teamId") Long teamId){
        return teamService.getTeamPlayers(tenantId, gameId, teamId);
    }
}
