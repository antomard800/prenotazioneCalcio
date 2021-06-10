package com.elis.footballmanager.service;

import com.elis.prenotazioneCalcio.dto.team.TeamCreationRequestDTO;
import com.elis.prenotazioneCalcio.model.Game;
import com.elis.prenotazioneCalcio.model.Player;
import com.elis.prenotazioneCalcio.model.Team;
import com.elis.prenotazioneCalcio.model.Tenant;
import com.elis.prenotazioneCalcio.repository.GameRepository;
import com.elis.prenotazioneCalcio.repository.PlayerRepository;
import com.elis.prenotazioneCalcio.repository.TeamRepository;
import com.elis.prenotazioneCalcio.repository.TenantRepository;
import com.elis.prenotazioneCalcio.service.PlayerServiceImpl;
import com.elis.prenotazioneCalcio.service.TeamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TeamServiceImplTest {
    @Autowired
    TenantRepository tenantRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamServiceImpl teamService;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PlayerServiceImpl playerService;

    @BeforeEach
    void setUp() {
        playerRepository.deleteAll();
        teamRepository.deleteAll();
        gameRepository.deleteAll();
        tenantRepository.deleteAll();
    }

    @Test
    void getTenantTeams(){
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Team firstTeam = teamRepository.save(Team.builder()
                .name("Milan")
                .color("Red")
                .tenant(tenant)
                .build());
        Team secondTeam = teamRepository.save(Team.builder()
                .name("Inter")
                .color("Blue")
                .tenant(tenant)
                .build());

        teamService.getTenantTeams(tenant.getId());

        assertNotNull(teamRepository.findTeamsByTenant_Id(tenant.getId()), "There are no teams");
        assertEquals(2, teamRepository.findTeamsByTenant_Id(tenant.getId()).size(), "Teams are not 2");
    }

    @Test
    @Transactional
    void createTenantTeam(){
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Team team = teamRepository.save(Team.builder()
                .name("Milan")
                .color("Red")
                .tenant(tenant)
                .build());

        TeamCreationRequestDTO teamCreationRequestDTO = new TeamCreationRequestDTO();
        teamCreationRequestDTO.name = team.getName();
        teamCreationRequestDTO.color = team.getColor();

        teamService.createTenantTeam(tenant.getId(), teamCreationRequestDTO);

        assertEquals(tenant, teamRepository.findById(team.getId()).get().getTenant(), "Tenant is different");
        assertEquals(team.getName(), teamRepository.findById(team.getId()).get().getName(), "Name is different");
        assertEquals(team.getColor(), teamRepository.findById(team.getId()).get().getColor(), "Color is different");
    }

    @Test
    void getTenantTeam() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Team team = teamRepository.save(Team.builder()
                .name("Milan")
                .color("Red")
                .tenant(tenant)
                .build());

        teamService.getTenantTeam(tenant.getId(), team.getId());

        assertNotNull(teamRepository.findTeamByTenant_IdAndId(tenant.getId(), team.getId()).get().getId(), "There are no team");
        assertEquals(team.getId(), teamRepository.findTeamByTenant_IdAndId(tenant.getId(), team.getId()).get().getId(), "Team is different");
    }

    @Test
    void deleteTenantTeam() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Team firstTeam = teamRepository.save(Team.builder()
                .name("Milan")
                .color("Red")
                .tenant(tenant)
                .build());
        Team secondTeam = teamRepository.save(Team.builder()
                .name("Inter")
                .color("Blue")
                .tenant(tenant)
                .build());

        teamService.deleteTenantTeam(tenant.getId(), firstTeam.getId());

        assertEquals(Optional.empty(), teamRepository.findTeamByTenant_IdAndId(tenant.getId(), firstTeam.getId()), "Team not deleted");
        assertEquals(1, teamRepository.findTeamsByTenant_Id(tenant.getId()).size(), "Team not deleted");
    }

    @Test
    @Transactional
    void getTeamPlayers() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Game game = gameRepository.save(Game.builder()
                .date("05/07/2021")
                .time("20:30")
                .tenant(tenant)
                .build());

        Team team = teamRepository.save(Team.builder()
                .name("Milan")
                .color("Red")
                .game(game)
                .tenant(tenant)
                .build());

        Player firstPlayer = playerRepository.save(Player.builder()
                .name("Antonio")
                .surname("DAddetta")
                .rating(3)
                .role("Difensore")
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
                .rating(5)
                .role("Portiere")
                .email("nelson@gmail.com")
                .password("nelson")
                .tenant(tenant)
                .build());
        Player fourthPlayer = playerRepository.save(Player.builder()
                .name("Maarten")
                .surname("Stekelenburg")
                .rating(2)
                .role("Portiere")
                .email("maarten@gmail.com")
                .password("maarten")
                .tenant(tenant)
                .build());

        team.addPlayer(firstPlayer);
        team.addPlayer(secondPlayer);
        team.addPlayer(thirdPlayer);
        team.addPlayer(fourthPlayer);

        teamService.getTeamPlayers(tenant.getId(), game.getId(), team.getId());

        assertEquals(4, teamRepository.findTeamByTenant_IdAndId(tenant.getId(), team.getId()).get().getPlayers().size(), "There are not 4 players");
        assertEquals(15, teamRepository.findTeamByTenant_IdAndId(tenant.getId(), team.getId()).get().getPlayersTotalRating(), "Total rating is not 15");
    }
}
