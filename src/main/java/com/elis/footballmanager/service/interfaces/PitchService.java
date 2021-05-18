package com.elis.footballmanager.service.interfaces;

import com.elis.footballmanager.dto.pitch.PitchCreationRequestDTO;
import com.elis.footballmanager.dto.pitch.PitchCreationResponseDTO;
import com.elis.footballmanager.dto.pitch.PitchListDTO;
import org.springframework.stereotype.Service;

@Service
public interface PitchService {
    PitchListDTO getTenantPitches(Long tenantId);

    PitchCreationResponseDTO createTenantPitch(Long tenantId, PitchCreationRequestDTO pitchCreationRequestDTO);
}
