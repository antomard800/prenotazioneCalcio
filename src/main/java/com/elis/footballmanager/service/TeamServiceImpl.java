package com.elis.footballmanager.service;

import com.elis.footballmanager.dto.player.PlayerListDTO;
import com.elis.footballmanager.dto.team.TeamCreationRequestDTO;
import com.elis.footballmanager.dto.team.TeamCreationResponseDTO;
import com.elis.footballmanager.dto.team.TeamDTO;
import com.elis.footballmanager.dto.team.TeamListDTO;
import com.elis.footballmanager.helper.GameHelper;
import com.elis.footballmanager.helper.TeamHelper;
import com.elis.footballmanager.helper.TenantHelper;
import com.elis.footballmanager.model.Game;
import com.elis.footballmanager.model.Team;
import com.elis.footballmanager.model.Tenant;
import com.elis.footballmanager.service.interfaces.TeamService;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    TeamHelper teamHelper;

    @Autowired
    TenantHelper tenantHelper;

    @Autowired
    GameHelper gameHelper;

    @Override
    public TeamListDTO getTenantTeams(Long tenantId) {
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");

        return teamHelper.getTenantTeams(tenantId);
    }

    @Override
    public TeamCreationResponseDTO createTenantTeam(Long tenantId, TeamCreationRequestDTO teamCreationRequestDTO) {
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(teamCreationRequestDTO.name != null, "Team name cannot be null.");
        Preconditions.checkArgument(teamCreationRequestDTO.color != null, "Team color cannot be null.");

        return teamHelper.createTenantTeam(tenant, teamCreationRequestDTO);
    }

    @Override
    public TeamDTO getTenantTeam(Long tenantId, Long teamId) {
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(teamId != null, "Team id cannot be null");

        return teamHelper.getTenantTeam(tenantId, teamId);
    }

    @Override
    public TeamCreationResponseDTO updateTenantTeam(Long tenantId, Long teamId, TeamCreationRequestDTO teamCreationRequestDTO) {
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(teamId != null, "Team id cannot be null");
        Preconditions.checkArgument(teamCreationRequestDTO.name != null, "Team name cannot be null.");
        Preconditions.checkArgument(teamCreationRequestDTO.color != null, "Team color cannot be null.");

        return teamHelper.updateTenantTeam(tenant, teamId, teamCreationRequestDTO);
    }

    @Override
    public TeamDTO deleteTenantTeam(Long tenantId, Long teamId) {
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(teamId != null, "Team id cannot be null");

        return teamHelper.deleteTenantTeam(tenantId, teamId);
    }

    @Override
    public PlayerListDTO getTeamPlayers(Long tenantId, Long gameId, Long teamId) {
        Tenant tenant = tenantHelper.findById(tenantId);
        Game game = gameHelper.findByTenant_IdAndId(tenantId, gameId);
        Team team = teamHelper.findByTenant_IdAndId(tenantId, teamId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(!Objects.isNull(game), "Game does not exist");
        Preconditions.checkArgument(!Objects.isNull(team), "Team does not exist");

        return teamHelper.getTeamPlayers(team);
    }
}
