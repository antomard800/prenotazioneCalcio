package com.elis.prenotazioneCalcio.repository;

import com.elis.prenotazioneCalcio.model.Judgement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JudgementRepository extends JpaRepository<Judgement, Long> {
    List<Judgement> findReviewsByTenant_Id(Long tenantId);

    //List<Judgement> findReviewsByPlayer_Id(Long playerId);
}
