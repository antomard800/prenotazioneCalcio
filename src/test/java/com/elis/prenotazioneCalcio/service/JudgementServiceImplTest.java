package com.elis.prenotazioneCalcio.service;

import com.elis.prenotazioneCalcio.dto.review.JudgementCreationRequestDTO;
import com.elis.prenotazioneCalcio.model.Judgement;
import com.elis.prenotazioneCalcio.model.Player;
import com.elis.prenotazioneCalcio.model.Tenant;
import com.elis.prenotazioneCalcio.repository.GameRepository;
import com.elis.prenotazioneCalcio.repository.JudgementRepository;
import com.elis.prenotazioneCalcio.repository.PlayerRepository;
import com.elis.prenotazioneCalcio.repository.TenantRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
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

    //Delete data from local database before each test
    @Before
    public void setUp() {
        judgementRepository.deleteAll();
        playerRepository.deleteAll();
        gameRepository.deleteAll();
        tenantRepository.deleteAll();
    }

    @Test
    public void getTenantReviews() {
        //Create tenant using builder and save it into local database and in a local variable
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        //Create player using builder and save it into local database and in a local variable
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

        //Create judgement using builder and save it into local database and in a local variable
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

        //Call relative service test method
        judgementService.getTenantReviews(tenant.getId());

        //Check if judgement entity is not null
        assertNotNull("There are no judgements", judgementRepository.findReviewsByTenant_Id(tenant.getId()));
        //Check if judgement entity records are 2
        assertEquals("Judgements are not 2", 2, judgementRepository.findReviewsByTenant_Id(tenant.getId()).size());
    }

    @Test
    @Transactional
    public void createTenantReview() {
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

        //Create JudgementCreationRequestDTO variable to call service method createTenantReview()
        JudgementCreationRequestDTO judgementCreationRequestDTO = new JudgementCreationRequestDTO();
        judgementCreationRequestDTO.rating = judgement.getRating();
        judgementCreationRequestDTO.comment = judgement.getComment();

        judgementService.createTenantReview(player.getId(), tenant.getId(), judgementCreationRequestDTO);
        //Check if the judgement has been created
        assertNotNull("Judgement not created", judgementRepository.findById(judgement.getId()));
        //Check if data saved in database are equals to insert data
        assertEquals("Tenant is different", tenant, judgementRepository.findById(judgement.getId()).get().getTenant());
        assertEquals("Player is different", player, judgementRepository.findById(judgement.getId()).get().getPlayer());
        assertEquals("Rating is different", judgement.getRating(), judgementRepository.findById(judgement.getId()).get().getRating());
        assertEquals("Comment is different", judgement.getComment(), judgementRepository.findById(judgement.getId()).get().getComment());
        assertEquals("Date is different", LocalDate.now(), judgementRepository.findById(judgement.getId()).get().getDate());
    }
}
