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
        //Create a list of teamDTO
        TeamListDTO teamListDTO = new TeamListDTO();
        //Use findTeamsByTenant_Id to find teams of that tenant, then convert them into TeamDTO and add them to teamDTO list
        teamListDTO.teams = teamRepository.findTeamsByTenant_Id(tenantId).stream().map(TeamDTO::of).collect(Collectors.toList());
        return teamListDTO;
    }

    public TeamCreationResponseDTO createTenantTeam(Tenant tenant, TeamCreationRequestDTO teamCreationRequestDTO) {
        Team team = new Team();

        //Check name existence
        if (teamRepository.existsByName(teamCreationRequestDTO.name)) {
            throw new RuntimeException("This team already exists");
        } else {
            //If name not already exists, save new team
            team.setName(teamCreationRequestDTO.name);
            team.setColor(teamCreationRequestDTO.color);
            team.setTenant(tenant);

            teamRepository.save(team);

            return null;
        }
    }

    public TeamDTO getTenantTeam(Long tenantId, Long teamId) {
        //Find team by tenant and his id and convert it into teamDTO
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
        //If team exist, delete it
        if (teamRepository.existsById(teamId)) {
            teamRepository.deleteById(teamId);

            return null;
        } else {
            throw new RuntimeException("Team id not found");
        }
    }

    public Team findByTenant_IdAndId(Long tenantId, Long teamId) {
        //Find team by tenant's id and his id
        return teamRepository.findTeamByTenant_IdAndId(tenantId, teamId).orElseThrow(() -> new RuntimeException("Team not found"));
    }

    public PlayerListDTO getTeamPlayers(Team team) {
        PlayerListDTO playerListDTO = new PlayerListDTO();

        //Get players in team, convert them into playerDTO and add them into playerDTO list
        playerListDTO.players = teamRepository.findById(team.getId()).get().getPlayers().stream().map(PlayerDTO::of).collect(Collectors.toList());

        return playerListDTO;
    }
}
