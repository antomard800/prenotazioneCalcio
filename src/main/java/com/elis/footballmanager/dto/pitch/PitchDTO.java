package com.elis.footballmanager.dto.pitch;

import com.elis.footballmanager.model.Pitch;

public class PitchDTO {
    public Long id;
    public Float width;
    public Float length;
    public Long tenant;

    public static PitchDTO of(Pitch pitch){
        PitchDTO pitchDTO = new PitchDTO();

        pitchDTO.id = pitch.getId();
        pitchDTO.width = pitch.getWidth();
        pitchDTO.length = pitch.getLength();
        pitchDTO.tenant = pitch.getTenant().getId();

        return pitchDTO;
    }
}
