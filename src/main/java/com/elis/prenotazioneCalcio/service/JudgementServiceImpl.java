package com.elis.prenotazioneCalcio.service;

import com.elis.prenotazioneCalcio.dto.review.JudgementCreationRequestDTO;
import com.elis.prenotazioneCalcio.dto.review.JudgementCreationResponseDTO;
import com.elis.prenotazioneCalcio.dto.review.JudgementListDTO;
import com.elis.prenotazioneCalcio.helper.JudgementHelper;
import com.elis.prenotazioneCalcio.helper.PlayerHelper;
import com.elis.prenotazioneCalcio.helper.TenantHelper;
import com.elis.prenotazioneCalcio.model.Player;
import com.elis.prenotazioneCalcio.model.Tenant;
import com.elis.prenotazioneCalcio.service.interfaces.JudgementService;
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
        //Check tenant existence
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");

        return judgementHelper.getTenantReviews(tenantId);
    }

    /*@Override
    public JudgementListDTO getPlayerReviews(Long playerId) {
        //Check player existence
        Player player = playerHelper.findById(playerId);

        Preconditions.checkArgument(!Objects.isNull(player), "Player does not exist");

        return judgementHelper.getPlayerReviews(playerId);
    }*/

    @Override
    public JudgementCreationResponseDTO createTenantReview(Long playerId, Long tenantId, JudgementCreationRequestDTO judgmentCreationRequestDTO) {
        Tenant tenant = tenantHelper.findById(tenantId);
        //Check player existence
        Player player = playerHelper.findById(playerId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(!Objects.isNull(player), "Player does not exist");
        //Check presence of all data
        Preconditions.checkArgument(judgmentCreationRequestDTO.rating != null, "Review rating cannot be null");
        Preconditions.checkArgument(judgmentCreationRequestDTO.comment != null, "Review comment cannot be null");

        return judgementHelper.createTenantReview(player, tenant, judgmentCreationRequestDTO);
    }

    /*@Override
    public JudgementDTO getPlayerReview(Long reviewId) {
        //Check judgement existence
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
    }*/
}
