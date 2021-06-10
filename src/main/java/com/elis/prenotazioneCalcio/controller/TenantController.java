package com.elis.prenotazioneCalcio.controller;

import com.elis.prenotazioneCalcio.dto.tenant.TenantCreationRequestDTO;
import com.elis.prenotazioneCalcio.dto.tenant.TenantCreationResponseDTO;
import com.elis.prenotazioneCalcio.dto.tenant.TenantDTO;
import com.elis.prenotazioneCalcio.dto.tenant.TenantListDTO;
import com.elis.prenotazioneCalcio.service.interfaces.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class TenantController {
    @Autowired
    TenantService tenantService;

    //Tenant base queries

    @GetMapping("/getTenants")
    public TenantListDTO getTenants() {
        return tenantService.getTenants();
    }

    @PostMapping("/createTenant")
    public TenantCreationResponseDTO createTenant(@RequestBody TenantCreationRequestDTO tenantCreationRequestDTO) {
        return tenantService.createTenant(tenantCreationRequestDTO);
    }

    @GetMapping("/getTenant/{tenantId}")
    public TenantDTO getTenant(@PathVariable("tenantId") Long tenantId) {
        return tenantService.getTenant(tenantId);
    }

    //Query for tenants' login with email and password

    @PostMapping("loginTenant/{email}/{password}")
    public TenantDTO loginTenant(@PathVariable("email") String email, @PathVariable("password") String password) {
        return tenantService.loginTenant(email, password);
    }
}
