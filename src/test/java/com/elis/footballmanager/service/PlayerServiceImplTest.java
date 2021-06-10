package com.elis.footballmanager.service;

import com.elis.prenotazioneCalcio.dto.player.PlayerCreationRequestDTO;
import com.elis.prenotazioneCalcio.helper.PlayerHelper;
import com.elis.prenotazioneCalcio.model.Game;
import com.elis.prenotazioneCalcio.model.Player;
import com.elis.prenotazioneCalcio.model.Tenant;
import com.elis.prenotazioneCalcio.repository.GameRepository;
import com.elis.prenotazioneCalcio.repository.PlayerRepository;
import com.elis.prenotazioneCalcio.repository.TenantRepository;
import com.elis.prenotazioneCalcio.service.PlayerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PlayerServiceImplTest {
    @Autowired
    TenantRepository tenantRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PlayerHelper playerHelper;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlayerServiceImpl playerService;

    @BeforeEach
    @Transactional
    void setup() {
        playerRepository.deleteAll();
        gameRepository.deleteAll();
        tenantRepository.deleteAll();
    }

    @Test
    void getTenantPlayers() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("elis@gmail.com")
                .password("ciao")
                .build());

        Player firstPlayer = playerRepository.save(Player.builder()
                .name("Antonio")
                .surname("DAddetta")
                .rating(4)
                .role("Centrocampista")
                .email("antonio@gmail.com")
                .password("antonio")
                .tenant(tenant)
                .build());
        Player secondPlayer = playerRepository.save(Player.builder()
                .name("Giacomo")
                .surname("Calianno")
                .rating(5)
                .role("Difensore")
                .email("giacomo@gmail.com")
                .password("giacomo")
                .tenant(tenant)
                .build());
        Player thirdPlayer = playerRepository.save(Player.builder()
                .name("Nelson")
                .surname("Dida")
                .rating(3)
                .role("Portiere")
                .email("dida@gmail.com")
                .password("dida")
                .tenant(tenant)
                .build());
        Player fourthPlayer = playerRepository.save(Player.builder()
                .name("Gianluca")
                .surname("Lapadula")
                .rating(2)
                .role("Attaccante")
                .email("gianluca@gmail.com")
                .password("gianluca")
                .tenant(tenant)
                .build());

        playerService.getTenantPlayers(tenant.getId());

        assertNotNull(playerRepository.findPlayersByTenant_Id(tenant.getId()), "Players not found");
        assertEquals(playerRepository.findPlayersByTenant_Id(tenant.getId()).size(), 4, "Players are not 4");
    }

    @Test
    @Transactional
    void createTenantPlayer() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Player player = Player.builder()
                .name("Antonio")
                .surname("DAddetta")
                .rating(4)
                .role("Centrocampista")
                .email("antonio@gmail.com")
                .password("antonio")
                .tenant(tenant)
                .build();

        PlayerCreationRequestDTO playerCreationRequestDTO = new PlayerCreationRequestDTO();
        playerCreationRequestDTO.name = player.getName();
        playerCreationRequestDTO.surname = player.getSurname();
        playerCreationRequestDTO.rating = player.getRating();
        playerCreationRequestDTO.role = player.getRole();
        playerCreationRequestDTO.email = player.getEmail();
        playerCreationRequestDTO.password = player.getPassword();

        playerService.createTenantPlayer(tenant.getId(), playerCreationRequestDTO);

        player = playerRepository.save(player);

        assertEquals(tenant, playerRepository.findById(player.getId()).get().getTenant(), "Tenant is different");
        assertEquals(player.getName(), playerRepository.findById(player.getId()).get().getName(), "Name is different");
        assertEquals(player.getSurname(), playerRepository.findById(player.getId()).get().getSurname(), "Surname is different");
        assertEquals(player.getRating(), playerRepository.findById(player.getId()).get().getRating(), "Rating is different");
        assertEquals(player.getRole(), playerRepository.findById(player.getId()).get().getRole(), "Role is different");
        assertEquals(player.getEmail(), playerRepository.findById(player.getId()).get().getEmail(), "Email is different");
        assertEquals(player.getPassword(), playerRepository.findById(player.getId()).get().getPassword(), "Password is different");

        assertNotNull(player.getId(), "Id is null");
        assertNotNull(player.getName(), "Name is null");
        assertNotNull(player.getSurname(), "Surname is null");
        assertNotNull(player.getRating(), "Rating is null");
        assertNotNull(player.getRole(), "Role is null");
        assertNotNull(player.getEmail(), "Email is null");
        assertNotNull(player.getPassword(), "Password is null");
        assertNotNull(player.getTenant(), "Tenant is null");

        assertEquals(player.getName(), "Antonio", "Name is different");
        assertEquals(player.getSurname(), "DAddetta", "Surname is different");
        assertEquals(player.getRating(), 4, "Rating is different");
        assertEquals(player.getRole(), "Centrocampista", "Role is different");
        assertEquals(player.getEmail(), "antonio@gmail.com", "Email is different");
        assertEquals(player.getPassword(), "antonio", "Password is different");
        assertEquals(player.getTenant(), tenant, "Tenant is different");
    }

    @Test
    void getTenantPlayer() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("elis@gmail.com")
                .password("ciao")
                .build());

        Player player = playerRepository.save(Player.builder()
                .name("Antonio")
                .surname("DAddetta")
                .rating(4)
                .role("Centrocampista")
                .email("antonio@gmail.com")
                .password("antonio")
                .tenant(tenant)
                .build());

        playerService.getTenantPlayer(tenant.getId(), player.getId());

        assertNotNull(playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), player.getId()), "Player not found");
        assertEquals(player.getEmail(), playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), player.getId()).get().getEmail(), "Player is different");
    }

    @Test
    void deleteTenantPlayer() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Player firstPlayer = playerRepository.save(Player.builder()
                .name("Antonio")
                .surname("DAddetta")
                .rating(4)
                .role("Centrocampista")
                .email("antonio@gmail.com")
                .password("antonio")
                .tenant(tenant)
                .build());
        Player secondPlayer = playerRepository.save(Player.builder()
                .name("Giacomo")
                .surname("Calianno")
                .rating(5)
                .role("Difensore")
                .email("giacomo@gmail.com")
                .password("giacomo")
                .tenant(tenant)
                .build());

        playerService.deleteTenantPlayer(tenant.getId(), firstPlayer.getId());

        assertEquals(Optional.empty(), playerRepository.findById(firstPlayer.getId()), "Player not deleted");
        assertEquals(playerRepository.findPlayersByTenant_Id(tenant.getId()).size(), 1, "Player not deleted from tenant");
    }

    @Test
    void loginPlayer() {
        Tenant tenant = Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build();

        Player firstPlayer = Player.builder()
                .name("Antonio")
                .surname("DAddetta")
                .rating(4)
                .role("Centrocampista")
                .email("antonio@gmail.com")
                .password("antonio")
                .build();
        Player secondPlayer = Player.builder()
                .name("Giacomo")
                .surname("Calianno")
                .rating(5)
                .role("Difensore")
                .email("giacomo@gmail.com")
                .password("giacomo")
                .build();

        tenant = tenantRepository.save(tenant);

        firstPlayer.setTenant(tenant);
        secondPlayer.setTenant(tenant);

        playerRepository.save(firstPlayer);
        playerRepository.save(secondPlayer);

        assertEquals(firstPlayer.getId(), playerRepository.findByEmailAndPassword("antonio@gmail.com", "antonio").get().getId(), "Player login failed");
        assertNotEquals(secondPlayer.getId(), playerRepository.findByEmailAndPassword("antonio@gmail.com", "antonio").get().getId(), "Player login success");
    }

    @Test
    @Transactional
    void signToMatch() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Player firstPlayer = playerRepository.save(Player.builder()
                .name("Antonio")
                .surname("DAddetta")
                .rating(4)
                .role("Centrocampista")
                .email("antonio@gmail.com")
                .password("antonio")
                .tenant(tenant)
                .build());
        Player secondPlayer = playerRepository.save(Player.builder()
                .name("Giacomo")
                .surname("Calianno")
                .rating(5)
                .role("Difensore")
                .email("giacomo@gmail.com")
                .password("giacomo")
                .tenant(tenant)
                .build());

        Game firstGame = gameRepository.save(Game.builder()
                .date("03/08/2021")
                .time("20:50")
                .tenant(tenant)
                .build());
        Game secondGame = gameRepository.save(Game.builder()
                .date("02/08/2021")
                .time("17:30")
                .tenant(tenant)
                .build());

        playerService.signToMatch(tenant.getId(), firstPlayer.getId(), firstGame.getId());
        playerService.signToMatch(tenant.getId(), secondPlayer.getId(), firstGame.getId());
        playerService.signToMatch(tenant.getId(), secondPlayer.getId(), secondGame.getId());

        firstGame.addPlayer(firstPlayer);
        firstGame.addPlayer(secondPlayer);
        secondGame.addPlayer(secondPlayer);

        assertEquals(gameRepository.findById(firstGame.getId()).get().getPlayers().size(), 2, "Registration failed");
        assertEquals(gameRepository.findById(secondGame.getId()).get().getPlayers().size(), 1, "Registration failed");
        assertEquals(playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), firstPlayer.getId()).get().getGames().size(), 1, "Registration failed");
        assertEquals(playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), secondPlayer.getId()).get().getGames().size(), 2, "Registration failed");
    }

    @Test
    @Transactional
    void updateTenantPlayerRating(){
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Player firstPlayer = playerRepository.save(Player.builder()
                .name("Antonio")
                .surname("DAddetta")
                .rating(4)
                .role("Centrocampista")
                .email("antonio@gmail.com")
                .password("antonio")
                .tenant(tenant)
                .build());
        Player secondPlayer = playerRepository.save(Player.builder()
                .name("Giacomo")
                .surname("Calianno")
                .rating(5)
                .role("Difensore")
                .email("giacomo@gmail.com")
                .password("giacomo")
                .tenant(tenant)
                .build());

        Game game = gameRepository.save(Game.builder()
                .date("03/08/2021")
                .time("20:50")
                .tenant(tenant)
                .build());

        playerService.signToMatch(tenant.getId(), secondPlayer.getId(), game.getId());

        PlayerCreationRequestDTO playerCreationRequestDTO = new PlayerCreationRequestDTO();
        playerCreationRequestDTO.rating = 1;

        playerService.updateTenantPlayerRating(tenant.getId(), game.getId(), secondPlayer.getId(), playerCreationRequestDTO);

        assertEquals(secondPlayer.getRating(), 3, "Rating update failed");
    }

    @Test
    @Transactional
    void getPlayerMatches() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Player firstPlayer = playerRepository.save(Player.builder()
                .name("Antonio")
                .surname("DAddetta")
                .rating(4)
                .role("Centrocampista")
                .email("antonio@gmail.com")
                .password("antonio")
                .tenant(tenant)
                .build());
        Player secondPlayer = playerRepository.save(Player.builder()
                .name("Giacomo")
                .surname("Calianno")
                .rating(5)
                .role("Difensore")
                .email("giacomo@gmail.com")
                .password("giacomo")
                .tenant(tenant)
                .build());

        Game firstGame = gameRepository.save(Game.builder()
                .date("03/08/2021")
                .time("20:50")
                .tenant(tenant)
                .build());
        Game secondGame = gameRepository.save(Game.builder()
                .date("02/08/2021")
                .time("17:30")
                .tenant(tenant)
                .build());

        playerService.signToMatch(tenant.getId(), firstPlayer.getId(), firstGame.getId());
        playerService.signToMatch(tenant.getId(), secondPlayer.getId(), firstGame.getId());
        playerService.signToMatch(tenant.getId(), secondPlayer.getId(), secondGame.getId());

        playerService.getPlayerMatches(tenant.getId(), firstPlayer.getId());
        playerService.getPlayerMatches(tenant.getId(), secondPlayer.getId());

        firstGame.addPlayer(firstPlayer);
        firstGame.addPlayer(secondPlayer);
        secondGame.addPlayer(secondPlayer);

        assertEquals(gameRepository.findById(firstGame.getId()).get().getPlayers().size(), 2, "Registration failed");
        assertEquals(gameRepository.findById(secondGame.getId()).get().getPlayers().size(), 1, "Registration failed");
        assertEquals(playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), firstPlayer.getId()).get().getGames().size(), 1, "Registration failed");
        assertEquals(playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), secondPlayer.getId()).get().getGames().size(), 2, "Registration failed");
    }
}
