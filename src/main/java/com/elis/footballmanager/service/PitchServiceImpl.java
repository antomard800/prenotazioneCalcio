package com.elis.footballmanager.service;

import com.elis.footballmanager.dto.pitch.PitchCreationRequestDTO;
import com.elis.footballmanager.dto.pitch.PitchCreationResponseDTO;
import com.elis.footballmanager.dto.pitch.PitchListDTO;
import com.elis.footballmanager.helper.PitchHelper;
import com.elis.footballmanager.helper.TenantHelper;
import com.elis.footballmanager.model.Tenant;
import com.elis.footballmanager.service.interfaces.PitchService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PitchServiceImpl implements PitchService {
    @Autowired
    PitchHelper pitchHelper;

    @Autowired
    TenantHelper tenantHelper;

    @Override
    public PitchListDTO getTenantPitches(Long tenantId) {
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        return pitchHelper.getTenantPitches(tenantId);
    }

    @Override
    public PitchCreationResponseDTO createTenantPitch(Long tenantId, PitchCreationRequestDTO pitchCreationRequestDTO) {
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(pitchCreationRequestDTO.width != null, "Pitch width cannot be null.");
        Preconditions.checkArgument(pitchCreationRequestDTO.length != null, "Pitch length cannot be null.");

        return pitchHelper.createTenantPitch(tenant, pitchCreationRequestDTO);
    }
}
