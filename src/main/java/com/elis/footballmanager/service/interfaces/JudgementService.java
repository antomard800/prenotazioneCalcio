package com.elis.footballmanager.service.interfaces;

import com.elis.footballmanager.dto.review.JudgementCreationRequestDTO;
import com.elis.footballmanager.dto.review.JudgementCreationResponseDTO;
import com.elis.footballmanager.dto.review.JudgementDTO;
import com.elis.footballmanager.dto.review.JudgementListDTO;
import org.springframework.stereotype.Service;

@Service
public interface JudgementService {
    JudgementListDTO getTenantReviews(Long tenantId);

    JudgementListDTO getPlayerReviews(Long playerId);

    JudgementCreationResponseDTO createTenantReview(Long playerId, Long tenantId, JudgementCreationRequestDTO judgmentCreationRequestDTO);

    JudgementCreationResponseDTO updateTenantReview(Long reviewId, JudgementCreationRequestDTO judgmentCreationRequestDTO);

    JudgementDTO deleteTenantReview(Long reviewId);

    JudgementDTO getPlayerReview(Long reviewId);
}
