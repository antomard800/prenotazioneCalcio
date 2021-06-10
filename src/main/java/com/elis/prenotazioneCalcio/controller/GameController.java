package com.elis.prenotazioneCalcio.controller;

import com.elis.prenotazioneCalcio.dto.game.GameCreationRequestDTO;
import com.elis.prenotazioneCalcio.dto.game.GameCreationResponseDTO;
import com.elis.prenotazioneCalcio.dto.game.GameDTO;
import com.elis.prenotazioneCalcio.dto.game.GameListDTO;
import com.elis.prenotazioneCalcio.dto.player.PlayerListDTO;
import com.elis.prenotazioneCalcio.service.interfaces.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class GameController {
    @Autowired
    GameService gameService;

    //Game base queries

    @GetMapping("/{tenantId}/getMatches")
    public GameListDTO getTenantMatches(@PathVariable("tenantId") Long tenantId) {
        return gameService.getTenantMatches(tenantId);
    }

    @PostMapping("/{tenantId}/createMatch")
    public GameCreationResponseDTO createTenantMatch(@PathVariable("tenantId") Long tenantId, @RequestBody GameCreationRequestDTO gameCreationRequestDTO) {
        return gameService.createTenantMatch(tenantId, gameCreationRequestDTO);
    }

    @GetMapping("/{tenantId}/getMatch/{gameId}")
    public GameDTO getTenantMatch(@PathVariable("tenantId") Long tenantId, @PathVariable("gameId") Long gameId) {
        return gameService.getTenantMatch(tenantId, gameId);
    }

    @PatchMapping("/{tenantId}/updateMatch/{gameId}")
    public GameCreationResponseDTO updateTenantMatch(@PathVariable("tenantId") Long tenantId, @PathVariable("gameId") Long gameId, @RequestBody GameCreationRequestDTO gameCreationRequestDTO) {
        return gameService.updateTenantMatch(tenantId, gameId, gameCreationRequestDTO);
    }

    @DeleteMapping("/{tenantId}/deleteMatch/{gameId}")
    public GameDTO deleteTenantMatch(@PathVariable("tenantId") Long tenantId, @PathVariable("gameId") Long gameId) {
        return gameService.deleteTenantMatch(tenantId, gameId);
    }

    //Query to get every player in the game

    @GetMapping("/{tenantId}/getMatchPlayers/{gameId}")
    public PlayerListDTO getMatchPlayers(@PathVariable("tenantId") Long tenantId, @PathVariable("gameId") Long gameId) {
        return gameService.getMatchPlayers(tenantId, gameId);
    }

    //Query to remove one player from the game

    @PatchMapping("/{tenantId}/{gameId}/removePlayer/{playerId}")
    public GameCreationResponseDTO removePlayer(@PathVariable("tenantId") Long tenantId, @PathVariable("gameId") Long gameId, @PathVariable("playerId") Long playerId/*, @RequestBody GameCreationRequestDTO gameCreationRequestDTO*/) {
        return gameService.removePlayer(tenantId, gameId, playerId/*, gameCreationRequestDTO*/);
    }

    //Query to remove all players from the game

    @PatchMapping("{tenantId}/{gameId}/removePlayers")
    public GameCreationResponseDTO removePlayers(@PathVariable("tenantId") Long tenantId, @PathVariable("gameId") Long gameId) {
        return gameService.removePlayers(tenantId, gameId);
    }

    //Query to build teams with players signed to the game

    @PatchMapping("/{tenantId}/{gameId}/buildTeams")
    public List<Long> buildTeams(@PathVariable("tenantId") Long tenantId, @PathVariable("gameId") Long gameId) {
        return gameService.buildTeams(tenantId, gameId);
    }
}
