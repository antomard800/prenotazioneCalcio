package com.elis.footballmanager.controller;

import com.elis.footballmanager.dto.match.GameCreationRequestDTO;
import com.elis.footballmanager.dto.match.GameCreationResponseDTO;
import com.elis.footballmanager.dto.match.GameDTO;
import com.elis.footballmanager.dto.match.GameListDTO;
import com.elis.footballmanager.dto.player.PlayerListDTO;
import com.elis.footballmanager.service.interfaces.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class GameController {
    @Autowired
    GameService gameService;

    @GetMapping("/{tenantId}/getMatches")
    public GameListDTO getTenantMatches(@PathVariable("tenantId") Long tenantId){
        return gameService.getTenantMatches(tenantId);
    }

    @PostMapping("/{tenantId}/createMatch")
    public GameCreationResponseDTO createTenantMatch(@PathVariable("tenantId") Long tenantId, @RequestBody GameCreationRequestDTO gameCreationRequestDTO){
        return gameService.createTenantMatch(tenantId, gameCreationRequestDTO);
    }

    @GetMapping("/{tenantId}/getMatch/{gameId}")
    public GameDTO getTenantMatch(@PathVariable("tenantId") Long tenantId, @PathVariable("gameId") Long gameId){
        return gameService.getTenantMatch(tenantId, gameId);
    }

    @PatchMapping("/{tenantId}/updateMatch/{gameId}")
    public GameCreationResponseDTO updateTenantMatch(@PathVariable("tenantId") Long tenantId, @PathVariable("gameId") Long gameId, @RequestBody GameCreationRequestDTO gameCreationRequestDTO){
        return gameService.updateTenantMatch(tenantId, gameId, gameCreationRequestDTO);
    }

    @DeleteMapping("/{tenantId}/deleteMatch/{gameId}")
    public GameDTO deleteTenantMatch(@PathVariable("tenantId") Long tenantId, @PathVariable("gameId") Long gameId){
        return gameService.deleteTenantMatch(tenantId, gameId);
    }

    @GetMapping("/{tenantId}/getMatchPlayers/{gameId}")
    public PlayerListDTO getMatchPlayers(@PathVariable("tenantId") Long tenantId, @PathVariable("gameId") Long gameId){
        return gameService.getMatchPlayers(tenantId, gameId);
    }
    
    @PatchMapping("/{tenantId}/{gameId}/removePlayer/{playerId}")
    public GameCreationResponseDTO removePlayer(@PathVariable("tenantId") Long tenantId, @PathVariable("gameId") Long gameId, @PathVariable("playerId") Long playerId/*, @RequestBody GameCreationRequestDTO gameCreationRequestDTO*/){
        return gameService.removePlayer(tenantId, gameId, playerId/*, gameCreationRequestDTO*/);
    }
}
