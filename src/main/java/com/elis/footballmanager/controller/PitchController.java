package com.elis.footballmanager.controller;

import com.elis.footballmanager.dto.pitch.PitchCreationRequestDTO;
import com.elis.footballmanager.dto.pitch.PitchCreationResponseDTO;
import com.elis.footballmanager.dto.pitch.PitchListDTO;
import com.elis.footballmanager.service.interfaces.PitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class PitchController {
    @Autowired
    PitchService pitchService;

    @GetMapping("/{tenantId}/getPitches")
    public PitchListDTO getTenantPitches(@PathVariable("tenantId") Long tenantId){
        return pitchService.getTenantPitches(tenantId);
    }

    @PostMapping("/{tenantId}/createPitch")
    public PitchCreationResponseDTO createTenantPitch(@PathVariable("tenantId") Long tenantId, @RequestBody PitchCreationRequestDTO pitchCreationRequestDTO){
        return pitchService.createTenantPitch(tenantId, pitchCreationRequestDTO);
    }
}
