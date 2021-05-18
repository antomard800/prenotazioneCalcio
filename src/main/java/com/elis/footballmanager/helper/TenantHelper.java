package com.elis.footballmanager.helper;

import com.elis.footballmanager.dto.player.PlayerDTO;
import com.elis.footballmanager.dto.player.PlayerListDTO;
import com.elis.footballmanager.dto.tenant.TenantCreationRequestDTO;
import com.elis.footballmanager.dto.tenant.TenantCreationResponseDTO;
import com.elis.footballmanager.dto.tenant.TenantDTO;
import com.elis.footballmanager.dto.tenant.TenantListDTO;
import com.elis.footballmanager.model.Tenant;
import com.elis.footballmanager.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TenantHelper {
    @Autowired
    TenantRepository tenantRepository;

    public Tenant findById(Long tenantId){
        return tenantRepository.findById(tenantId).orElseThrow(()-> new RuntimeException("Tenant not found"));
    }

    public Boolean existsById(Long id){
        return tenantRepository.existsById(id);
    }

    public TenantListDTO getTenants() {
        TenantListDTO tenantListDTO = new TenantListDTO();
        tenantListDTO.tenants = tenantRepository.findAll().stream().map(TenantDTO::of).collect(Collectors.toList());
        return tenantListDTO;
    }

    public TenantCreationResponseDTO createTenant(TenantCreationRequestDTO tenantCreationRequestDTO) {
        Tenant tenant = new Tenant();

        if(tenantRepository.existsByEmail(tenantCreationRequestDTO.email)){
            throw new RuntimeException("This email already exist");
        } else {
            tenant.setName(tenantCreationRequestDTO.name);
            tenant.setCity(tenantCreationRequestDTO.city);
            tenant.setAddress(tenantCreationRequestDTO.address);
            tenant.setCap(tenantCreationRequestDTO.cap);
            tenant.setEmail(tenantCreationRequestDTO.email);
            tenant.setPassword(tenantCreationRequestDTO.password);

            tenantRepository.save(tenant);

            return null;
        }
    }

    public TenantDTO getTenant(Long tenantId) {
        return TenantDTO.of(tenantRepository.findById(tenantId).orElseThrow(()-> new RuntimeException("Tenant not found")));
    }

    public TenantCreationResponseDTO updateTenant(Long tenantId, TenantCreationRequestDTO tenantCreationRequestDTO) {
        Tenant tenant = new Tenant();

        tenant.setId(tenantId);
        tenant.setName(tenantCreationRequestDTO.name);
        tenant.setCity(tenantCreationRequestDTO.city);
        tenant.setAddress(tenantCreationRequestDTO.address);
        tenant.setCap(tenantCreationRequestDTO.cap);
        tenant.setEmail(tenantCreationRequestDTO.email);
        tenant.setPassword(tenantCreationRequestDTO.password);

        tenantRepository.save(tenant);

        return null;
    }

    public TenantDTO deleteTenant(Long tenantId) {
        if(tenantRepository.existsById(tenantId)){
            tenantRepository.deleteById(tenantId);

            return null;
        } else {
            throw new RuntimeException("Tenant id not found");
        }
    }

    public TenantDTO loginTenant(String email, String password) {
        return TenantDTO.of(tenantRepository.findByEmailAndPassword(email, password).orElseThrow(()-> new RuntimeException("Wrong credentials")));
    }
}
