package com.elis.footballmanager.controller;

import com.elis.footballmanager.dto.tenant.TenantCreationRequestDTO;
import com.elis.footballmanager.dto.tenant.TenantCreationResponseDTO;
import com.elis.footballmanager.dto.tenant.TenantDTO;
import com.elis.footballmanager.dto.tenant.TenantListDTO;
import com.elis.footballmanager.service.interfaces.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class TenantController {
    @Autowired
    TenantService tenantService;

    @GetMapping("/getTenants")
    public TenantListDTO getTenants(){
        return tenantService.getTenants();
    }

    @PostMapping("/createTenant")
    public TenantCreationResponseDTO createTenant(@RequestBody TenantCreationRequestDTO tenantCreationRequestDTO){
        return tenantService.createTenant(tenantCreationRequestDTO);
    }

    @GetMapping("/getTenant/{tenantId}")
    public TenantDTO getTenant(@PathVariable("tenantId") Long tenantId){
        return tenantService.getTenant(tenantId);
    }

    @PutMapping("/updateTenant/{tenantId}")
    public TenantCreationResponseDTO updateTenant(@PathVariable("tenantId") Long tenantId, @RequestBody TenantCreationRequestDTO tenantCreationRequestDTO){
        return tenantService.updateTenant(tenantId, tenantCreationRequestDTO);
    }

    @DeleteMapping("/deleteTenant/{tenantId}")
    public TenantDTO deleteTenant(@PathVariable("tenantId") Long tenantId){
        return tenantService.deleteTenant(tenantId);
    }

    @PostMapping("loginTenant/{email}/{password}")
    public TenantDTO loginTenant(@PathVariable("email") String email, @PathVariable("password") String password){
        return tenantService.loginTenant(email, password);
    }
}
