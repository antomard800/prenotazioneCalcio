package com.elis.prenotazioneCalcio.service;

import com.elis.prenotazioneCalcio.dto.player.PlayerCreationRequestDTO;
import com.elis.prenotazioneCalcio.helper.PlayerHelper;
import com.elis.prenotazioneCalcio.model.Game;
import com.elis.prenotazioneCalcio.model.Player;
import com.elis.prenotazioneCalcio.model.Tenant;
import com.elis.prenotazioneCalcio.repository.GameRepository;
import com.elis.prenotazioneCalcio.repository.PlayerRepository;
import com.elis.prenotazioneCalcio.repository.TenantRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
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

    @Before
    @Transactional
    public void setup() {
        playerRepository.deleteAll();
        gameRepository.deleteAll();
        tenantRepository.deleteAll();
    }

    @Test
    public void getTenantPlayers() {
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

        assertNotNull("Players not found", playerRepository.findPlayersByTenant_Id(tenant.getId()));
        assertEquals("Players are not 4", 4, playerRepository.findPlayersByTenant_Id(tenant.getId()).size());
    }

    @Test
    @Transactional
    public void createTenantPlayer() {
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

        assertEquals("Tenant is different", tenant, playerRepository.findById(player.getId()).get().getTenant());
        assertEquals("Name is different", player.getName(), playerRepository.findById(player.getId()).get().getName());
        assertEquals("Surname is different", player.getSurname(), playerRepository.findById(player.getId()).get().getSurname());
        assertEquals("Rating is different", player.getRating(), playerRepository.findById(player.getId()).get().getRating());
        assertEquals("Role is different", player.getRole(), playerRepository.findById(player.getId()).get().getRole());
        assertEquals("Email is different", player.getEmail(), playerRepository.findById(player.getId()).get().getEmail());
        assertEquals("Password is different", player.getPassword(), playerRepository.findById(player.getId()).get().getPassword());

        assertNotNull("Id is null", player.getId());
        assertNotNull("Name is null", player.getName());
        assertNotNull("Surname is null", player.getSurname());
        assertNotNull("Rating is null", player.getRating());
        assertNotNull("Role is null", player.getRole());
        assertNotNull("Email is null", player.getEmail());
        assertNotNull("Password is null", player.getPassword());
        assertNotNull("Tenant is null", player.getTenant());

        assertEquals("Name is different", "Antonio", player.getName());
        assertEquals("Surname is different", "DAddetta", player.getSurname());
        assertEquals("Rating is different", Integer.valueOf(4), player.getRating());
        assertEquals("Role is different", "Centrocampista", player.getRole());
        assertEquals("Email is different", "antonio@gmail.com", player.getEmail());
        assertEquals("Password is different", "antonio", player.getPassword());
        assertEquals("Tenant is different", tenant, player.getTenant());
    }

    @Test
    public void getTenantPlayer() {
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

        assertNotNull("Player not found", playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), player.getId()));
        assertEquals("Player is different", player.getEmail(), playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), player.getId()).get().getEmail());
    }

    @Test
    @Transactional
    public void signToMatch() {
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

        assertEquals("Registration failed", 2, gameRepository.findById(firstGame.getId()).get().getPlayers().size());
        assertEquals("Registration failed", 1, gameRepository.findById(secondGame.getId()).get().getPlayers().size());
        assertEquals("Registration failed", 1, playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), firstPlayer.getId()).get().getGames().size());
        assertEquals("Registration failed", 2, playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), secondPlayer.getId()).get().getGames().size());
    }

    @Test
    @Transactional
    public void updateTenantPlayerRating() {
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

        assertEquals("Rating update failed", Integer.valueOf(3), secondPlayer.getRating());
    }

    @Test
    @Transactional
    public void getPlayerMatches() {
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

        assertEquals("Registration failed", 2, gameRepository.findById(firstGame.getId()).get().getPlayers().size());
        assertEquals("Registration failed", 1, gameRepository.findById(secondGame.getId()).get().getPlayers().size());
        assertEquals("Registration failed", 1, playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), firstPlayer.getId()).get().getGames().size());
        assertEquals("Registration failed", 2, playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), secondPlayer.getId()).get().getGames().size());
    }
}
