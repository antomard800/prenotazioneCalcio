package com.elis.footballmanager.helper;

import com.elis.footballmanager.dto.review.JudgementCreationRequestDTO;
import com.elis.footballmanager.dto.review.JudgementCreationResponseDTO;
import com.elis.footballmanager.dto.review.JudgementDTO;
import com.elis.footballmanager.dto.review.JudgementListDTO;
import com.elis.footballmanager.model.Player;
import com.elis.footballmanager.model.Judgement;
import com.elis.footballmanager.model.Tenant;
import com.elis.footballmanager.repository.JudgementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Component
public class JudgementHelper {
    @Autowired
    JudgementRepository judgementRepository;

    public JudgementListDTO getTenantReviews(Long tenantId) {
        JudgementListDTO judgementListDTO = new JudgementListDTO();

        judgementListDTO.reviews = judgementRepository.findReviewsByTenant_Id(tenantId).stream().map(JudgementDTO::of).collect(Collectors.toList());

        return judgementListDTO;
    }

    public JudgementListDTO getPlayerReviews(Long playerId) {
        JudgementListDTO judgementListDTO = new JudgementListDTO();

        judgementListDTO.reviews = judgementRepository.findReviewsByUser_Id(playerId).stream().map(JudgementDTO::of).collect(Collectors.toList());

        return judgementListDTO;
    }

    public JudgementCreationResponseDTO createTenantReview(Player player, Tenant tenant, JudgementCreationRequestDTO judgmentCreationRequestDTO) {
        Judgement judgement = new Judgement();

        judgement.setRating(judgmentCreationRequestDTO.rating);
        judgement.setComment(judgmentCreationRequestDTO.comment);
        judgement.setDate(LocalDate.now());
        judgement.setTenant(tenant);
        judgement.setUser(player);

        judgementRepository.save(judgement);

        return null;
    }

    public JudgementCreationResponseDTO updateTenantReview(Judgement judgement, JudgementCreationRequestDTO judgmentCreationRequestDTO) {
        if(judgmentCreationRequestDTO.rating != null){
            judgement.setRating(judgmentCreationRequestDTO.rating);
        }
        if(judgmentCreationRequestDTO.comment != null){
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
    }
}
