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

    @GetMapping("/{tenantId}/getMatch/{matchId}")
    public GameDTO getTenantMatch(@PathVariable("tenantId") Long tenantId, @PathVariable("matchId") Long matchId){
        return gameService.getTenantMatch(tenantId, matchId);
    }

    @PatchMapping("/{tenantId}/updateMatch/{matchId}")
    public GameCreationResponseDTO updateTenantMatch(@PathVariable("tenantId") Long tenantId, @PathVariable("matchId") Long matchId, @RequestBody GameCreationRequestDTO gameCreationRequestDTO){
        return gameService.updateTenantMatch(tenantId, matchId, gameCreationRequestDTO);
    }

    @DeleteMapping("/{tenantId}/deleteMatch/{matchId}")
    public GameDTO deleteTenantMatch(@PathVariable("tenantId") Long tenantId, @PathVariable("matchId") Long matchId){
        return gameService.deleteTenantMatch(tenantId, matchId);
    }

    @GetMapping("/{tenantId}/getMatchPlayers/{matchId}")
    public PlayerListDTO getMatchPlayers(@PathVariable("tenantId") Long tenantId, @PathVariable("matchId") Long matchId){
        return gameService.getMatchPlayers(tenantId, matchId);
    }
}
