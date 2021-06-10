package com.elis.prenotazioneCalcio.controller;

import com.elis.prenotazioneCalcio.dto.review.JudgementCreationRequestDTO;
import com.elis.prenotazioneCalcio.dto.review.JudgementCreationResponseDTO;
import com.elis.prenotazioneCalcio.dto.review.JudgementListDTO;
import com.elis.prenotazioneCalcio.service.interfaces.JudgementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class JudgementController {
    @Autowired
    JudgementService judgementService;

    //Judgement base queries

    @GetMapping("/getTenantReviews/{tenantId}")
    public JudgementListDTO getTenantReviews(@PathVariable("tenantId") Long tenantId) {
        return judgementService.getTenantReviews(tenantId);
    }

    @PostMapping("{playerId}/createReview/{tenantId}")
    public JudgementCreationResponseDTO createTenantReview(@PathVariable("playerId") Long playerId, @PathVariable("tenantId") Long tenantId, @RequestBody JudgementCreationRequestDTO judgmentCreationRequestDTO) {
        return judgementService.createTenantReview(playerId, tenantId, judgmentCreationRequestDTO);
    }
}
