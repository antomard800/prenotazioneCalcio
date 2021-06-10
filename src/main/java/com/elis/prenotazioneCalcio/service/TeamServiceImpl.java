package com.elis.prenotazioneCalcio.service;

import com.elis.prenotazioneCalcio.dto.player.PlayerListDTO;
import com.elis.prenotazioneCalcio.dto.team.TeamCreationRequestDTO;
import com.elis.prenotazioneCalcio.dto.team.TeamCreationResponseDTO;
import com.elis.prenotazioneCalcio.dto.team.TeamDTO;
import com.elis.prenotazioneCalcio.dto.team.TeamListDTO;
import com.elis.prenotazioneCalcio.helper.GameHelper;
import com.elis.prenotazioneCalcio.helper.TeamHelper;
import com.elis.prenotazioneCalcio.helper.TenantHelper;
import com.elis.prenotazioneCalcio.model.Game;
import com.elis.prenotazioneCalcio.model.Team;
import com.elis.prenotazioneCalcio.model.Tenant;
import com.elis.prenotazioneCalcio.service.interfaces.TeamService;
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
        //Check tenant existence
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");

        return teamHelper.getTenantTeams(tenantId);
    }

    @Override
    public TeamCreationResponseDTO createTenantTeam(Long tenantId, TeamCreationRequestDTO teamCreationRequestDTO) {
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        //Check presence of all data
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
        //Check game and team existence
        Game game = gameHelper.findByTenantIdAndGameId(tenantId, gameId);
        Team team = teamHelper.findByTenant_IdAndId(tenantId, teamId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(!Objects.isNull(game), "Game does not exist");
        Preconditions.checkArgument(!Objects.isNull(team), "Team does not exist");

        return teamHelper.getTeamPlayers(team);
    }
}
