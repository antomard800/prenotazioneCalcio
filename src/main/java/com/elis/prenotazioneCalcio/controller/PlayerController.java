package com.elis.prenotazioneCalcio.controller;

import com.elis.prenotazioneCalcio.dto.game.GameListDTO;
import com.elis.prenotazioneCalcio.dto.player.PlayerCreationRequestDTO;
import com.elis.prenotazioneCalcio.dto.player.PlayerCreationResponseDTO;
import com.elis.prenotazioneCalcio.dto.player.PlayerDTO;
import com.elis.prenotazioneCalcio.dto.player.PlayerListDTO;
import com.elis.prenotazioneCalcio.service.interfaces.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class PlayerController {
    @Autowired
    PlayerService playerService;

    //Player base queries

    @GetMapping("/{tenantId}/getPlayers")
    public PlayerListDTO getTenantPlayers(@PathVariable("tenantId") Long tenantId) {
        return playerService.getTenantPlayers(tenantId);
    }

    @PostMapping("/{tenantId}/createPlayer")
    public PlayerCreationResponseDTO createTenantPlayer(@PathVariable("tenantId") Long tenantId, @RequestBody PlayerCreationRequestDTO playerCreationRequestDTO) {
        return playerService.createTenantPlayer(tenantId, playerCreationRequestDTO);
    }

    @GetMapping("/{tenantId}/getPlayer/{playerId}")
    public PlayerDTO getTenantPlayer(@PathVariable("tenantId") Long tenantId, @PathVariable("playerId") Long playerId) {
        return playerService.getTenantPlayer(tenantId, playerId);
    }

    @PatchMapping("/{tenantId}/updatePlayer/{playerId}")
    public PlayerCreationResponseDTO updateTenantPlayer(@PathVariable("tenantId") Long tenantId, @PathVariable("playerId") Long playerId, @RequestBody PlayerCreationRequestDTO playerCreationRequestDTO) {
        return playerService.updateTenantPlayer(tenantId, playerId, playerCreationRequestDTO);
    }

    //Query for players' login with email and password

    @PostMapping("loginPlayer/{email}/{password}")
    public PlayerDTO loginPlayer(@PathVariable("email") String email, @PathVariable("password") String password) {
        return playerService.loginPlayer(email, password);
    }

    //Query for choosing the game

    @PatchMapping("/{tenantId}/{playerId}/signToMatch/{gameId}")
    public PlayerDTO signToMatch(@PathVariable("tenantId") Long tenantId, @PathVariable("playerId") Long playerId, @PathVariable("gameId") Long gameId) {
        return playerService.signToMatch(tenantId, playerId, gameId);
    }

    //Query to update player's rating based on other players ratings

    @PatchMapping("/{tenantId}/{gameId}/updatePlayerRating/{playerId}")
    public PlayerCreationResponseDTO updateTenantPlayerRating(@PathVariable("tenantId") Long tenantId, @PathVariable("gameId") Long gameId, @PathVariable("playerId") Long playerId, @RequestBody PlayerCreationRequestDTO playerCreationRequestDTO) {
        return playerService.updateTenantPlayerRating(tenantId, gameId, playerId, playerCreationRequestDTO);
    }

    //Query to see every game played

    @GetMapping("/{tenantId}/{playerId}/getMatches")
    public GameListDTO getPlayerMatches(@PathVariable("tenantId") Long tenantId, @PathVariable("playerId") Long playerId) {
        return playerService.getPlayerMatches(tenantId, playerId);
    }
}
