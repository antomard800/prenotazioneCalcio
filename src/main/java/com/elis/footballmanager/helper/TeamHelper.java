package com.elis.footballmanager.helper;

import com.elis.footballmanager.dto.team.TeamCreationRequestDTO;
import com.elis.footballmanager.dto.team.TeamCreationResponseDTO;
import com.elis.footballmanager.dto.team.TeamDTO;
import com.elis.footballmanager.dto.team.TeamListDTO;
import com.elis.footballmanager.model.Player;
import com.elis.footballmanager.model.Team;
import com.elis.footballmanager.model.Tenant;
import com.elis.footballmanager.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TeamHelper {
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TenantHelper tenantHelper;

    public TeamListDTO getTenantTeams(Long tenantId) {
        TeamListDTO teamListDTO = new TeamListDTO();
        teamListDTO.teams = teamRepository.findTeamByTenant_Id(tenantId).stream().map(TeamDTO::of).collect(Collectors.toList());
        return teamListDTO;
    }

    public TeamCreationResponseDTO createTenantTeam(Tenant tenant, TeamCreationRequestDTO teamCreationRequestDTO) {
        Team team = new Team();

        team.setName(teamCreationRequestDTO.name);
        team.setColor(teamCreationRequestDTO.color);
        team.setTenant(tenant);

        teamRepository.save(team);

        return null;
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
        if(teamRepository.existsById(teamId)){
            teamRepository.deleteById(teamId);

            return null;
        } else {
            throw new RuntimeException("Team id not found");
        }
    }
    /*public TeamMemberCreationResponseDTO createTeamMember(Long teamId, TeamMemberDTO teamMemberDTO) {

        Team savedTeam = teamRepository.save(squadra);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedTeam.getId()).toUri();
        TeamMemberCreationResponseDTO response = new TeamMemberCreationResponseDTO();
        response.url = location;
        return response;
    }*/
}
