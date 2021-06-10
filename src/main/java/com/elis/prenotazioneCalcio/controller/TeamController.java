package com.elis.prenotazioneCalcio.controller;

import com.elis.prenotazioneCalcio.dto.player.PlayerListDTO;
import com.elis.prenotazioneCalcio.dto.team.TeamCreationRequestDTO;
import com.elis.prenotazioneCalcio.dto.team.TeamCreationResponseDTO;
import com.elis.prenotazioneCalcio.dto.team.TeamDTO;
import com.elis.prenotazioneCalcio.dto.team.TeamListDTO;
import com.elis.prenotazioneCalcio.service.interfaces.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class TeamController {
    @Autowired
    TeamService teamService;

    //Team base queries

    @GetMapping("/{tenantId}/getTeams")
    public TeamListDTO getTenantTeams(@PathVariable("tenantId") Long tenantId) {
        return teamService.getTenantTeams(tenantId);
    }

    @PostMapping("/{tenantId}/createTeam")
    public TeamCreationResponseDTO createTenantTeam(@PathVariable("tenantId") Long tenantId, @RequestBody TeamCreationRequestDTO teamCreationRequestDTO) {
        return teamService.createTenantTeam(tenantId, teamCreationRequestDTO);
    }

    @GetMapping("/{tenantId}/getTeam/{teamId}")
    public TeamDTO getTenantTeam(@PathVariable("tenantId") Long tenantId, @PathVariable("teamId") Long teamId) {
        return teamService.getTenantTeam(tenantId, teamId);
    }

    @PutMapping("/{tenantId}/updateTeam/{teamId}")
    public TeamCreationResponseDTO updateTenantTeam(@PathVariable("tenantId") Long tenantId, @PathVariable("teamId") Long teamId, @RequestBody TeamCreationRequestDTO teamCreationRequestDTO) {
        return teamService.updateTenantTeam(tenantId, teamId, teamCreationRequestDTO);
    }

    @DeleteMapping("/{tenantId}/deleteTeam/{teamId}")
    public TeamDTO deleteTenantTeam(@PathVariable("tenantId") Long tenantId, @PathVariable("teamId") Long teamId) {
        return teamService.deleteTenantTeam(tenantId, teamId);
    }

    //Query to get team players

    @GetMapping("/{tenantId}/{gameId}/getTeamPlayers/{teamId}")
    public PlayerListDTO getTeamPlayers(@PathVariable("tenantId") Long tenantId, @PathVariable("gameId") Long gameId, @PathVariable("teamId") Long teamId) {
        return teamService.getTeamPlayers(tenantId, gameId, teamId);
    }
}
