package com.elis.prenotazioneCalcio.helper;

import com.elis.prenotazioneCalcio.dto.player.PlayerDTO;
import com.elis.prenotazioneCalcio.dto.player.PlayerListDTO;
import com.elis.prenotazioneCalcio.dto.team.TeamCreationRequestDTO;
import com.elis.prenotazioneCalcio.dto.team.TeamCreationResponseDTO;
import com.elis.prenotazioneCalcio.dto.team.TeamDTO;
import com.elis.prenotazioneCalcio.dto.team.TeamListDTO;
import com.elis.prenotazioneCalcio.model.Team;
import com.elis.prenotazioneCalcio.model.Tenant;
import com.elis.prenotazioneCalcio.repository.PlayerRepository;
import com.elis.prenotazioneCalcio.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Component
@Transactional
public class TeamHelper {
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TenantHelper tenantHelper;

    @Autowired
    PlayerRepository playerRepository;

    public TeamListDTO getTenantTeams(Long tenantId) {
        TeamListDTO teamListDTO = new TeamListDTO();
        teamListDTO.teams = teamRepository.findTeamsByTenant_Id(tenantId).stream().map(TeamDTO::of).collect(Collectors.toList());
        return teamListDTO;
    }

    public TeamCreationResponseDTO createTenantTeam(Tenant tenant, TeamCreationRequestDTO teamCreationRequestDTO) {
        Team team = new Team();

        if(teamRepository.existsByName(teamCreationRequestDTO.name)){
            throw new RuntimeException("This team already exists");
        } else {
            team.setName(teamCreationRequestDTO.name);
            team.setColor(teamCreationRequestDTO.color);
            team.setTenant(tenant);

            teamRepository.save(team);

            return null;
        }
    }

    public TeamDTO getTenantTeam(Long tenantId, Long teamId) {
        return TeamDTO.of(teamRepository.findTeamByTenant_IdAndId(tenantId, teamId).orElseThrow(() -> new RuntimeException("Team not found")));
    }

    public TeamCreationResponseDTO updateTenantTeam(Tenant tenant, Long teamId, TeamCreationRequestDTO teamCreationRequestDTO) {
        Team team = new Team();

        team.setId(teamId);
        team.setName(teamCreationRequestDTO.name);
        team.setColor(teamCreationRequestDTO.color);
        team.setTenant(tenant);

        teamRepository.save(team);

        return null;
    }

    public TeamDTO deleteTenantTeam(Long tenantId, Long teamId) {
        /*final Boolean[] found = {false};
        teamRepository.findTeamByTenant_Id(tenantId).forEach(team -> {
            if (team.getId().equals(teamId)) {
                teamRepository.deleteById(teamId);
                found[0] = true;
            }
        });
        if (found[0]) {
            return null;
        }
        throw new RuntimeException("TEAM_DO_NOT_BELONG_TO_TENANT");*/
        if (teamRepository.existsById(teamId)) {
            teamRepository.deleteById(teamId);

            return null;
        } else {
            throw new RuntimeException("Team id not found");
        }
    }

    public Team findByTenant_IdAndId(Long tenantId, Long teamId) {
        return teamRepository.findTeamByTenant_IdAndId(tenantId, teamId).orElseThrow(() -> new RuntimeException("Team not found"));
    }

    public PlayerListDTO getTeamPlayers(Team team) {
        PlayerListDTO playerListDTO = new PlayerListDTO();

        playerListDTO.players = teamRepository.findById(team.getId()).get().getPlayers().stream().map(PlayerDTO::of).collect(Collectors.toList());

        return playerListDTO;
    }
}
