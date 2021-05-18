package com.elis.footballmanager.helper;

import com.elis.footballmanager.dto.pitch.PitchCreationRequestDTO;
import com.elis.footballmanager.dto.pitch.PitchCreationResponseDTO;
import com.elis.footballmanager.dto.pitch.PitchDTO;
import com.elis.footballmanager.dto.pitch.PitchListDTO;
import com.elis.footballmanager.model.Pitch;
import com.elis.footballmanager.model.Tenant;
import com.elis.footballmanager.repository.PitchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PitchHelper {
    @Autowired
    PitchRepository pitchRepository;

    public PitchListDTO getTenantPitches(Long tenantId) {
        PitchListDTO pitchListDTO = new PitchListDTO();
        pitchListDTO.pitches = pitchRepository.findPitchesByTenant_Id(tenantId).stream().map(PitchDTO::of).collect(Collectors.toList());
        return pitchListDTO;
    }

    public PitchCreationResponseDTO createTenantPitch(Tenant tenant, PitchCreationRequestDTO pitchCreationRequestDTO) {
        Pitch pitch = new Pitch();

        pitch.setWidth(pitchCreationRequestDTO.width);
        pitch.setLength(pitchCreationRequestDTO.length);
        pitch.setTenant(tenant);

        pitchRepository.save(pitch);

        return null;
    }
}
