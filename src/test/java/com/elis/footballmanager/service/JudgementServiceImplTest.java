package com.elis.footballmanager.service;

import com.elis.prenotazioneCalcio.dto.review.JudgementCreationRequestDTO;
import com.elis.prenotazioneCalcio.model.Judgement;
import com.elis.prenotazioneCalcio.model.Player;
import com.elis.prenotazioneCalcio.model.Tenant;
import com.elis.prenotazioneCalcio.repository.GameRepository;
import com.elis.prenotazioneCalcio.repository.JudgementRepository;
import com.elis.prenotazioneCalcio.repository.PlayerRepository;
import com.elis.prenotazioneCalcio.repository.TenantRepository;
import com.elis.prenotazioneCalcio.service.JudgementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class JudgementServiceImplTest {
    @Autowired
    TenantRepository tenantRepository;

    @Autowired
    JudgementServiceImpl judgementService;

    @Autowired
    JudgementRepository judgementRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlayerRepository playerRepository;

    @BeforeEach
    void setUp(){
        judgementRepository.deleteAll();
        playerRepository.deleteAll();
        gameRepository.deleteAll();
        tenantRepository.deleteAll();
    }

    @Test
    void getTenantReviews(){
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

        Judgement firstJudgement = judgementRepository.save(Judgement.builder()
                .rating(4)
                .comment("Bella struttura")
                .player(firstPlayer)
                .tenant(tenant)
                .build());
        Judgement secondJudgement = judgementRepository.save(Judgement.builder()
                .rating(2)
                .comment("Brutta struttura")
                .player(secondPlayer)
                .tenant(tenant)
                .build());

        judgementService.getTenantReviews(tenant.getId());

        assertNotNull(judgementRepository.findReviewsByTenant_Id(tenant.getId()), "There are no judgements");
        assertEquals(2, judgementRepository.findReviewsByTenant_Id(tenant.getId()).size(), "Judgements are not 2");
    }

    @Test
    void getPlayerReviews(){
        Tenant firstTenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());
        Tenant secondTenant = tenantRepository.save(Tenant.builder()
                .name("Safi")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("safi@gmail.com")
                .password("safi")
                .build());

        Player player = playerRepository.save(Player.builder()
                .name("Antonio")
                .surname("DAddetta")
                .rating(4)
                .role("Centrocampista")
                .email("antonio@gmail.com")
                .password("antonio")
                .tenant(firstTenant)
                .build());

        Judgement firstJudgement = judgementRepository.save(Judgement.builder()
                .rating(4)
                .comment("Bella struttura")
                .player(player)
                .tenant(firstTenant)
                .build());
        Judgement secondJudgement = judgementRepository.save(Judgement.builder()
                .rating(2)
                .comment("Brutta struttura")
                .player(player)
                .tenant(secondTenant)
                .build());

        judgementService.getPlayerReviews(player.getId());

        assertNotNull(judgementRepository.findReviewsByPlayer_Id(player.getId()), "There are no judgements");
        assertEquals(2, judgementRepository.findReviewsByPlayer_Id(player.getId()).size(), "Judgements are not 2");
    }

    @Test
    @Transactional
    void createTenantReview(){
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
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

        Judgement judgement = judgementRepository.save(Judgement.builder()
                .rating(4)
                .comment("Bella struttura")
                .date(LocalDate.now())
                .player(player)
                .tenant(tenant)
                .build());

        JudgementCreationRequestDTO judgementCreationRequestDTO = new JudgementCreationRequestDTO();
        judgementCreationRequestDTO.rating = judgement.getRating();
        judgementCreationRequestDTO.comment = judgement.getComment();

        judgementService.createTenantReview(player.getId(), tenant.getId(), judgementCreationRequestDTO);

        assertNotNull(judgementRepository.findById(judgement.getId()), "Judgement not created");
        assertEquals(tenant, judgementRepository.findById(judgement.getId()).get().getTenant(), "Tenant is different");
        assertEquals(player, judgementRepository.findById(judgement.getId()).get().getPlayer(), "Player is different");
        assertEquals(judgement.getRating(), judgementRepository.findById(judgement.getId()).get().getRating(), "Rating is different");
        assertEquals(judgement.getComment(), judgementRepository.findById(judgement.getId()).get().getComment(), "Comment is different");
        assertEquals(LocalDate.now(), judgementRepository.findById(judgement.getId()).get().getDate(), "Date is different");
    }
}
