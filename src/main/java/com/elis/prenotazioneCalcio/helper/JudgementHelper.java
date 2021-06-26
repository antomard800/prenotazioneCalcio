package com.elis.prenotazioneCalcio.helper;

import com.elis.prenotazioneCalcio.dto.review.JudgementCreationRequestDTO;
import com.elis.prenotazioneCalcio.dto.review.JudgementCreationResponseDTO;
import com.elis.prenotazioneCalcio.dto.review.JudgementDTO;
import com.elis.prenotazioneCalcio.dto.review.JudgementListDTO;
import com.elis.prenotazioneCalcio.model.Judgement;
import com.elis.prenotazioneCalcio.model.Player;
import com.elis.prenotazioneCalcio.model.Tenant;
import com.elis.prenotazioneCalcio.repository.JudgementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Component
@Transactional
public class JudgementHelper {
    @Autowired
    JudgementRepository judgementRepository;

    public JudgementListDTO getTenantReviews(Long tenantId) {
        //Create a list of judgementDTO
        JudgementListDTO judgementListDTO = new JudgementListDTO();

        //Use findReviewsByTenant_Id to find judgements of that tenant, then convert them into JudgementDTO and add them to judgementDTO list
        judgementListDTO.reviews = judgementRepository.findReviewsByTenant_Id(tenantId).stream().map(JudgementDTO::of).collect(Collectors.toList());

        return judgementListDTO;
    }

    /*public JudgementListDTO getPlayerReviews(Long playerId) {
        JudgementListDTO judgementListDTO = new JudgementListDTO();

        judgementListDTO.reviews = judgementRepository.findReviewsByPlayer_Id(playerId).stream().map(JudgementDTO::of).collect(Collectors.toList());

        return judgementListDTO;
    }*/

    public JudgementCreationResponseDTO createTenantReview(Player player, Tenant tenant, JudgementCreationRequestDTO judgmentCreationRequestDTO) {
        Judgement judgement = new Judgement();

        //Save new judgement
        judgement.setRating(judgmentCreationRequestDTO.rating);
        judgement.setComment(judgmentCreationRequestDTO.comment);
        judgement.setDate(LocalDate.now());
        judgement.setTenant(tenant);
        judgement.setPlayer(player);

        judgementRepository.save(judgement);

        return null;
    }

    /*public JudgementCreationResponseDTO updateTenantReview(Judgement judgement, JudgementCreationRequestDTO judgmentCreationRequestDTO) {
        if (judgmentCreationRequestDTO.rating != null) {
            judgement.setRating(judgmentCreationRequestDTO.rating);
        }
        if (judgmentCreationRequestDTO.comment != null) {
            judgement.setComment(judgmentCreationRequestDTO.comment);
        }
        judgement.setDate(LocalDate.now());

        judgementRepository.save(judgement);

        return null;
    }

    public JudgementDTO getPlayerReview(Long reviewId) {
        return JudgementDTO.of(judgementRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found")));
    }

    public JudgementDTO deleteTenantReview(Long reviewId) {
        judgementRepository.deleteById(reviewId);

        return null;
    }

    public Judgement findById(Long reviewId) {
        return judgementRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"));
    }*/
}
