package com.elis.prenotazioneCalcio.helper;

import com.elis.prenotazioneCalcio.dto.game.GameDTO;
import com.elis.prenotazioneCalcio.dto.game.GameListDTO;
import com.elis.prenotazioneCalcio.dto.player.PlayerCreationRequestDTO;
import com.elis.prenotazioneCalcio.dto.player.PlayerCreationResponseDTO;
import com.elis.prenotazioneCalcio.dto.player.PlayerDTO;
import com.elis.prenotazioneCalcio.dto.player.PlayerListDTO;
import com.elis.prenotazioneCalcio.model.Game;
import com.elis.prenotazioneCalcio.model.Player;
import com.elis.prenotazioneCalcio.model.Tenant;
import com.elis.prenotazioneCalcio.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.stream.Collectors;

@Slf4j
@Component
@Transactional
public class PlayerHelper {
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GameHelper gameHelper;

    public PlayerListDTO getTenantPlayers(Long tenantId) {
        //Create a list of playerDTO
        PlayerListDTO playerListDTO = new PlayerListDTO();
        //Use findPlayerByTenant_Id to find players of that tenant, then convert them in PlayerDTO and add them to playerDTO list
        playerListDTO.players = playerRepository.findPlayersByTenant_Id(tenantId).stream().map(PlayerDTO::of).collect(Collectors.toList());
        return playerListDTO;
    }

    public PlayerCreationResponseDTO createTenantPlayer(Tenant tenant, PlayerCreationRequestDTO playerCreationRequestDTO) {
        Player player = new Player();
        int strength = 10;

        //Check email existence
        if (playerRepository.existsByEmail(playerCreationRequestDTO.email)) {
            throw new RuntimeException("This email already exists");
        } else {
            //If email not already exists, save new player
            player.setName(playerCreationRequestDTO.name);
            player.setSurname(playerCreationRequestDTO.surname);
            player.setEmail(playerCreationRequestDTO.email);
            //Create new BCryptoPasswordEncoder and sat player password as the encoded one
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
            player.setPassword(bCryptPasswordEncoder.encode(playerCreationRequestDTO.password));
            player.setRating(playerCreationRequestDTO.rating);
            player.setRole(playerCreationRequestDTO.role);
            player.setTenant(tenant);

            playerRepository.save(player);

            return null;
        }
    }

    public PlayerDTO getTenantPlayer(Long tenantId, Long playerId) {
        //Find player by tenant and his id and convert it into playerDTO
        return PlayerDTO.of(playerRepository.findPlayerByTenant_IdAndId(tenantId, playerId).orElseThrow(() -> new RuntimeException("Player not found")));
    }

    public PlayerCreationResponseDTO updateTenantPlayer(Tenant tenant, Long playerId, PlayerCreationRequestDTO playerCreationRequestDTO) {
        Player player = new Player();
        int strength = 10;

        player.setId(playerId);
        player.setTenant(tenant);
        player.setEmail(playerCreationRequestDTO.email);
        if (playerCreationRequestDTO.name != null) {
            player.setName(playerCreationRequestDTO.name);
        } else {
            player.setName(playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), playerId).get().getName());
        }
        if (playerCreationRequestDTO.surname != null) {
            player.setSurname(playerCreationRequestDTO.surname);
        } else {
            player.setSurname(playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), playerId).get().getSurname());
        }
        if (playerCreationRequestDTO.rating != null) {
            player.setRating(playerCreationRequestDTO.rating);
        } else {
            player.setRating(playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), playerId).get().getRating());
        }
        if (playerCreationRequestDTO.role != null) {
            player.setRole(playerCreationRequestDTO.role);
        } else {
            player.setRole(playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), playerId).get().getRole());
        }
        if (playerCreationRequestDTO.password != null) {
            //Create new BCryptoPasswordEncoder and sat player password as the encoded one
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
            player.setPassword(bCryptPasswordEncoder.encode(playerCreationRequestDTO.password));
        } else {
            player.setPassword(playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), playerId).get().getPassword());
        }

        playerRepository.save(player);

        return null;
    }

    /*public PlayerDTO deleteTenantPlayer(Long playerId) {
        //If player exists, delete it
        if (playerRepository.existsById(playerId)) {
            playerRepository.deleteById(playerId);

            return null;
        } else {
            throw new RuntimeException("Player id not found");
        }
    }*/

    public PlayerDTO loginPlayer(String email, String password) {
        //Find player by email
        Player playerTmp = playerRepository.findByEmail(email);
        int strength = 10;

        //Create new BCryptoPasswordEncoder and match the inserted password with the password saved in DB associated with email
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength);
        if (bCryptPasswordEncoder.matches(password, playerTmp.getPassword())) {
            //Find player by email and password and convert it into playerDTO
            return PlayerDTO.of(playerRepository.findByEmailAndPassword(email, playerTmp.getPassword()).orElseThrow(() -> new RuntimeException("Wrong credentials")));
        } else {
            throw new RuntimeException("Wrong credentials");
        }
    }

    @Transactional
    public PlayerDTO signToMatch(Player player, Game game) {
        //Add player to the game
        //game.addPlayer(player);
        //player.getGames().add(game);
        player.addGame(game);
        log.info("Added player {} to game {}", player.getEmail(), game.getId());
        //gameHelper.saveOrUpdate(game);
        playerRepository.save(player);

        return new PlayerDTO();
    }

    public Player findByTenantIdAndPlayerId(Long tenantId, Long playerId) {
        //Find player by tenant and his id
        return playerRepository.findPlayerByTenant_IdAndId(tenantId, playerId).orElseThrow(() -> new RuntimeException("Player not found"));
    }

    public PlayerCreationResponseDTO updateTenantPlayerRating(Player player, PlayerCreationRequestDTO playerCreationRequestDTO) {
        //Update player's rating by average between his rating and the other players' one
        player.setRating((player.getRating() + playerCreationRequestDTO.rating) / 2);

        playerRepository.save(player);

        return null;
    }

    public Player findById(Long playerId) {
        //Find player by id
        return playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));
    }

    public GameListDTO getPlayerMatches(Player player) {
        GameListDTO gameListDTO = new GameListDTO();

        //Get player's games, convert them into gameDTO and add them into gameDTO list
        gameListDTO.matches = player.getGames().stream().map(GameDTO::of).collect(Collectors.toList());

        return gameListDTO;
    }
}
