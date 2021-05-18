package com.elis.footballmanager.service;

import com.elis.footballmanager.dto.review.JudgementCreationRequestDTO;
import com.elis.footballmanager.dto.review.JudgementCreationResponseDTO;
import com.elis.footballmanager.dto.review.JudgementDTO;
import com.elis.footballmanager.dto.review.JudgementListDTO;
import com.elis.footballmanager.helper.PlayerHelper;
import com.elis.footballmanager.helper.JudgementHelper;
import com.elis.footballmanager.helper.TenantHelper;
import com.elis.footballmanager.model.Player;
import com.elis.footballmanager.model.Judgement;
import com.elis.footballmanager.model.Tenant;
import com.elis.footballmanager.service.interfaces.JudgementService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class JudgementServiceImpl implements JudgementService {
    @Autowired
    JudgementHelper judgementHelper;

    @Autowired
    TenantHelper tenantHelper;

    @Autowired
    PlayerHelper playerHelper;

    @Override
    public JudgementListDTO getTenantReviews(Long tenantId) {
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");

        return judgementHelper.getTenantReviews(tenantId);
    }

    @Override
    public JudgementListDTO getPlayerReviews(Long playerId) {
        Player player = playerHelper.findById(playerId);

        Preconditions.checkArgument(!Objects.isNull(player), "Player does not exist");

        return judgementHelper.getPlayerReviews(playerId);
    }

    @Override
    public JudgementCreationResponseDTO createTenantReview(Long playerId, Long tenantId, JudgementCreationRequestDTO judgmentCreationRequestDTO) {
        Tenant tenant = tenantHelper.findById(tenantId);
        Player player = playerHelper.findById(playerId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(!Objects.isNull(player), "Player does not exist");
        Preconditions.checkArgument(judgmentCreationRequestDTO.rating != null, "Review rating cannot be null");
        Preconditions.checkArgument(judgmentCreationRequestDTO.comment != null, "Review comment cannot be null");

        return judgementHelper.createTenantReview(player, tenant, judgmentCreationRequestDTO);
    }

    @Override
    public JudgementDTO getPlayerReview(Long reviewId) {
        Judgement judgement = judgementHelper.findById(reviewId);

        Preconditions.checkArgument(!Objects.isNull(judgement), "Review does not exist");

        return judgementHelper.getPlayerReview(reviewId);
    }

    @Override
    public JudgementCreationResponseDTO updateTenantReview(Long reviewId, JudgementCreationRequestDTO judgmentCreationRequestDTO) {
        Judgement judgement = judgementHelper.findById(reviewId);

        Preconditions.checkArgument(!Objects.isNull(judgement), "Review does not exist");

        return judgementHelper.updateTenantReview(judgement, judgmentCreationRequestDTO);
    }

    @Override
    public JudgementDTO deleteTenantReview(Long reviewId) {
        Judgement judgement = judgementHelper.findById(reviewId);

        Preconditions.checkArgument(!Objects.isNull(judgement), "Review does not exist");

        return judgementHelper.deleteTenantReview(reviewId);
    }
}
