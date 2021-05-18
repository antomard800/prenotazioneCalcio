package com.elis.footballmanager.controller;

import com.elis.footballmanager.dto.review.JudgementCreationRequestDTO;
import com.elis.footballmanager.dto.review.JudgementCreationResponseDTO;
import com.elis.footballmanager.dto.review.JudgementDTO;
import com.elis.footballmanager.dto.review.JudgementListDTO;
import com.elis.footballmanager.service.interfaces.JudgementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class JudgementController {
    @Autowired
    JudgementService judgementService;

    @GetMapping("/getTenantReviews/{tenantId}")
    public JudgementListDTO getTenantReviews(@PathVariable("tenantId") Long tenantId){
        return judgementService.getTenantReviews(tenantId);
    }

    @GetMapping("/getPlayerReviews/{playerId}")
    public JudgementListDTO getPlayerReviews(@PathVariable("playerId") Long playerId){
        return judgementService.getPlayerReviews(playerId);
    }

    @PostMapping("{playerId}/createReview/{tenantId}")
    public JudgementCreationResponseDTO createTenantReview(@PathVariable("playerId") Long playerId, @PathVariable("tenantId") Long tenantId, @RequestBody JudgementCreationRequestDTO judgmentCreationRequestDTO){
        return judgementService.createTenantReview(playerId, tenantId, judgmentCreationRequestDTO);
    }

    @GetMapping("/getReview/{reviewId}")
    public JudgementDTO getPlayerReview(@PathVariable("reviewId") Long reviewId){
        return judgementService.getPlayerReview(reviewId);
    }

    @PatchMapping("{reviewId}/updateReview")
    public JudgementCreationResponseDTO updateTenantReview(@PathVariable("reviewId") Long reviewId, @RequestBody JudgementCreationRequestDTO judgmentCreationRequestDTO){
        return judgementService.updateTenantReview(reviewId, judgmentCreationRequestDTO);
    }

    @DeleteMapping("{reviewId}/deleteReview")
    public JudgementDTO deleteTenantReview(@PathVariable("reviewId") Long reviewId){
        return judgementService.deleteTenantReview(reviewId);
    }
}
