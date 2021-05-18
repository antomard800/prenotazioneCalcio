package com.elis.footballmanager.repository;

import com.elis.footballmanager.model.Judgement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JudgementRepository extends JpaRepository<Judgement, Long> {
    List<Judgement> findReviewsByTenant_Id(Long tenantId);

    List<Judgement> findReviewsByUser_Id(Long playerId);
}
