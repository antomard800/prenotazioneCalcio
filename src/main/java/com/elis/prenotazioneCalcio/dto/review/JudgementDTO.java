package com.elis.prenotazioneCalcio.dto.review;

import com.elis.prenotazioneCalcio.model.Judgement;

import java.time.LocalDate;

public class JudgementDTO {
    //Attributes to send to front-end
    public Long id;
    public Integer rating;
    public String comment;
    public LocalDate date;

    //Transform a Judgement into a JudgementDTO
    public static JudgementDTO of(Judgement judgement) {
        JudgementDTO judgementDTO = new JudgementDTO();

        judgementDTO.id = judgement.getId();
        judgementDTO.rating = judgement.getRating();
        judgementDTO.comment = judgement.getComment();
        judgementDTO.date = judgement.getDate();

        return judgementDTO;
    }
}
