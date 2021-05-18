package com.elis.footballmanager.dto.review;

import com.elis.footballmanager.model.Judgement;

import java.time.LocalDate;

public class JudgementDTO {
    public Long id;
    public Integer rating;
    public String comment;
    public LocalDate date;

    public static JudgementDTO of(Judgement judgement){
        JudgementDTO judgementDTO = new JudgementDTO();

        judgementDTO.id = judgement.getId();
        judgementDTO.rating = judgement.getRating();
        judgementDTO.comment = judgement.getComment();
        judgementDTO.date = judgement.getDate();

        return judgementDTO;
    }
}
