package com.elis.prenotazioneCalcio.service.interfaces;

import com.elis.prenotazioneCalcio.dto.tenant.TenantCreationRequestDTO;
import com.elis.prenotazioneCalcio.dto.tenant.TenantCreationResponseDTO;
import com.elis.prenotazioneCalcio.dto.tenant.TenantDTO;
import com.elis.prenotazioneCalcio.dto.tenant.TenantListDTO;
import org.springframework.stereotype.Service;

@Service
public interface TenantService {
    TenantListDTO getTenants();

    TenantCreationResponseDTO createTenant(TenantCreationRequestDTO tenantCreationRequestDTO);

    TenantDTO getTenant(Long tenantId);

    //TenantCreationResponseDTO updateTenant(Long tenantId, TenantCreationRequestDTO tenantCreationRequestDTO);

    //TenantDTO deleteTenant(Long tenantId);

    TenantDTO loginTenant(String email, String password);
}
