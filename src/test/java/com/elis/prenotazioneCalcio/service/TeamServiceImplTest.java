package com.elis.prenotazioneCalcio.service;

import com.elis.prenotazioneCalcio.dto.team.TeamCreationRequestDTO;
import com.elis.prenotazioneCalcio.model.Game;
import com.elis.prenotazioneCalcio.model.Player;
import com.elis.prenotazioneCalcio.model.Team;
import com.elis.prenotazioneCalcio.model.Tenant;
import com.elis.prenotazioneCalcio.repository.GameRepository;
import com.elis.prenotazioneCalcio.repository.PlayerRepository;
import com.elis.prenotazioneCalcio.repository.TeamRepository;
import com.elis.prenotazioneCalcio.repository.TenantRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
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

    @Before
    public void setUp() {
        playerRepository.deleteAll();
        teamRepository.deleteAll();
        gameRepository.deleteAll();
        tenantRepository.deleteAll();
    }

    @Test
    public void getTenantTeams(){
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

        assertNotNull("There are no teams" ,teamRepository.findTeamsByTenant_Id(tenant.getId()));
        assertEquals("Teams are not 2", 2, teamRepository.findTeamsByTenant_Id(tenant.getId()).size());
    }

    @Test
    @Transactional
    public void createTenantTeam(){
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Team team = Team.builder()
                .name("Milan")
                .color("Red")
                .tenant(tenant)
                .build();

        TeamCreationRequestDTO teamCreationRequestDTO = new TeamCreationRequestDTO();
        teamCreationRequestDTO.name = team.getName();
        teamCreationRequestDTO.color = team.getColor();

        teamService.createTenantTeam(tenant.getId(), teamCreationRequestDTO);

        team = teamRepository.save(team);

        assertEquals("Tenant is different", tenant, teamRepository.findById(team.getId()).get().getTenant());
        assertEquals("Name is different", team.getName(), teamRepository.findById(team.getId()).get().getName());
        assertEquals("Color is different", team.getColor(), teamRepository.findById(team.getId()).get().getColor());
    }

    @Test
    public void getTenantTeam() {
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

        assertNotNull("There are no team", teamRepository.findTeamByTenant_IdAndId(tenant.getId(), team.getId()).get().getId());
        assertEquals("Team is different", team.getId(), teamRepository.findTeamByTenant_IdAndId(tenant.getId(), team.getId()).get().getId());
    }

    @Test
    public void deleteTenantTeam() {
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

        assertEquals("Team not deleted", Optional.empty(), teamRepository.findTeamByTenant_IdAndId(tenant.getId(), firstTeam.getId()));
        assertEquals("Team not deleted", 1, teamRepository.findTeamsByTenant_Id(tenant.getId()).size());
    }

    @Test
    @Transactional
     public void getTeamPlayers() {
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

        assertEquals("There are not 4 players", 4, teamRepository.findTeamByTenant_IdAndId(tenant.getId(), team.getId()).get().getPlayers().size());
        assertEquals("Total rating is not 15", Integer.valueOf(15), teamRepository.findTeamByTenant_IdAndId(tenant.getId(), team.getId()).get().getPlayersTotalRating());
    }
}
