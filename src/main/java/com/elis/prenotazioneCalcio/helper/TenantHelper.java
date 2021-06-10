package com.elis.prenotazioneCalcio.helper;

import com.elis.prenotazioneCalcio.dto.tenant.TenantCreationRequestDTO;
import com.elis.prenotazioneCalcio.dto.tenant.TenantCreationResponseDTO;
import com.elis.prenotazioneCalcio.dto.tenant.TenantDTO;
import com.elis.prenotazioneCalcio.dto.tenant.TenantListDTO;
import com.elis.prenotazioneCalcio.model.Tenant;
import com.elis.prenotazioneCalcio.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.stream.Collectors;

@Component
@Transactional
public class TenantHelper {
    @Autowired
    TenantRepository tenantRepository;

    public Tenant findById(Long tenantId) {
        return tenantRepository.findById(tenantId).orElseThrow(() -> new RuntimeException("Tenant not found"));
    }

    public TenantListDTO getTenants() {
        TenantListDTO tenantListDTO = new TenantListDTO();
        tenantListDTO.tenants = tenantRepository.findAll().stream().map(TenantDTO::of).collect(Collectors.toList());
        return tenantListDTO;
    }

    public TenantCreationResponseDTO createTenant(TenantCreationRequestDTO tenantCreationRequestDTO) {
        Tenant tenant = new Tenant();
        int strength = 10;

        if (tenantRepository.existsByEmail(tenantCreationRequestDTO.email)) {
            throw new RuntimeException("This email already exists");
        } else {
            tenant.setName(tenantCreationRequestDTO.name);
            tenant.setCity(tenantCreationRequestDTO.city);
            tenant.setAddress(tenantCreationRequestDTO.address);
            tenant.setCap(tenantCreationRequestDTO.cap);
            tenant.setEmail(tenantCreationRequestDTO.email);
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
            tenant.setPassword(bCryptPasswordEncoder.encode(tenantCreationRequestDTO.password));

            tenantRepository.save(tenant);

            return null;
        }
    }

    public TenantDTO getTenant(Long tenantId) {
        return TenantDTO.of(tenantRepository.findById(tenantId).orElseThrow(() -> new RuntimeException("Tenant not found")));
    }

    /*public TenantCreationResponseDTO updateTenant(Long tenantId, TenantCreationRequestDTO tenantCreationRequestDTO) {
        Tenant tenant = new Tenant();
        int strength = 10;


        tenant.setId(tenantId);
        tenant.setName(tenantCreationRequestDTO.name);
        tenant.setCity(tenantCreationRequestDTO.city);
        tenant.setAddress(tenantCreationRequestDTO.address);
        tenant.setCap(tenantCreationRequestDTO.cap);
        tenant.setEmail(tenantCreationRequestDTO.email);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
        tenant.setPassword(bCryptPasswordEncoder.encode(tenantCreationRequestDTO.password));

        tenantRepository.save(tenant);

        return null;
    }

    public TenantDTO deleteTenant(Long tenantId) {
        if (tenantRepository.existsById(tenantId)) {
            tenantRepository.deleteById(tenantId);

            return null;
        } else {
            throw new RuntimeException("Tenant id not found");
        }
    }*/

    public TenantDTO loginTenant(String email, String password) {
        Tenant tenantTmp = tenantRepository.findByEmail(email);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (bCryptPasswordEncoder.matches(password, tenantTmp.getPassword())) {
            return TenantDTO.of(tenantRepository.findByEmailAndPassword(email, tenantTmp.getPassword()).orElseThrow(() -> new RuntimeException("Wrong credentials")));
        } else {
            throw new RuntimeException("Wrong credentials");
        }
    }
}
