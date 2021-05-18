package com.elis.footballmanager.service;

import com.elis.footballmanager.dto.match.GameCreationRequestDTO;
import com.elis.footballmanager.dto.match.GameCreationResponseDTO;
import com.elis.footballmanager.dto.match.GameDTO;
import com.elis.footballmanager.dto.match.GameListDTO;
import com.elis.footballmanager.dto.player.PlayerListDTO;
import com.elis.footballmanager.helper.GameHelper;
import com.elis.footballmanager.helper.TenantHelper;
import com.elis.footballmanager.model.Game;
import com.elis.footballmanager.model.Tenant;
import com.elis.footballmanager.service.interfaces.GameService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    GameHelper gameHelper;

    @Autowired
    TenantHelper tenantHelper;

    @Override
    public GameListDTO getTenantMatches(Long tenantId) {
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        return gameHelper.getTenantMatches(tenantId);
    }

    @Override
    public GameCreationResponseDTO createTenantMatch(Long tenantId, GameCreationRequestDTO gameCreationRequestDTO) {
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(gameCreationRequestDTO.date != null, "Match date cannot be null");
        //Preconditions.checkArgument(matchCreationRequestDTO.time != null, "Match time cannot be null");

        return gameHelper.createTenantMatch(tenant, gameCreationRequestDTO);
    }

    @Override
    public GameDTO getTenantMatch(Long tenantId, Long matchId) {
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(matchId != null, "Match id cannot be null");

        return gameHelper.getTenantMatch(tenantId, matchId);
    }

    @Override
    public GameCreationResponseDTO updateTenantMatch(Long tenantId, Long matchId, GameCreationRequestDTO gameCreationRequestDTO) {
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(matchId != null, "Match id cannot be null");

        return gameHelper.updateTenantMatch(tenant, matchId, gameCreationRequestDTO);
    }

    @Override
    public GameDTO deleteTenantMatch(Long tenantId, Long matchId) {
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(matchId != null, "Match id cannot be null");

        return gameHelper.deleteTenantMatch(tenantId, matchId);
    }

    @Override
    public PlayerListDTO getMatchPlayers(Long tenantId, Long matchId) {
        Tenant tenant = tenantHelper.findById(tenantId);
        Game game = gameHelper.findByTenant_IdAndId(tenantId, matchId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(!Objects.isNull(game), "Game does not exist");

        return gameHelper.getMatchPlayers(game);
    }
}
