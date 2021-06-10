package com.elis.prenotazioneCalcio.service;

import com.elis.prenotazioneCalcio.dto.tenant.TenantCreationRequestDTO;
import com.elis.prenotazioneCalcio.dto.tenant.TenantCreationResponseDTO;
import com.elis.prenotazioneCalcio.dto.tenant.TenantDTO;
import com.elis.prenotazioneCalcio.dto.tenant.TenantListDTO;
import com.elis.prenotazioneCalcio.helper.TenantHelper;
import com.elis.prenotazioneCalcio.service.interfaces.TenantService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantServiceImpl implements TenantService {
    @Autowired
    TenantHelper tenantHelper;

    @Override
    public TenantListDTO getTenants() {
        return tenantHelper.getTenants();
    }

    @Override
    public TenantCreationResponseDTO createTenant(TenantCreationRequestDTO tenantCreationRequestDTO) {
        //Check presence of all data
        Preconditions.checkArgument(tenantCreationRequestDTO.name != null, "Tenant name cannot be null.");
        Preconditions.checkArgument(tenantCreationRequestDTO.city != null, "Tenant city cannot be null.");
        Preconditions.checkArgument(tenantCreationRequestDTO.address != null, "Tenant address cannot be null.");
        Preconditions.checkArgument(tenantCreationRequestDTO.cap != null, "Tenant cap cannot be null.");
        Preconditions.checkArgument(tenantCreationRequestDTO.email != null, "Tenant email cannot be null.");
        Preconditions.checkArgument(tenantCreationRequestDTO.password != null, "Tenant password cannot be null.");

        return tenantHelper.createTenant(tenantCreationRequestDTO);
    }

    @Override
    public TenantDTO getTenant(Long tenantId) {
        Preconditions.checkArgument(tenantId != null, "Tenant id cannot be null.");

        return tenantHelper.getTenant(tenantId);
    }

    /*@Override
    public TenantCreationResponseDTO updateTenant(Long tenantId, TenantCreationRequestDTO tenantCreationRequestDTO) {
        Preconditions.checkArgument(tenantId != null, "Tenant id cannot be null.");

        Preconditions.checkArgument(tenantCreationRequestDTO.name != null, "Tenant name cannot be null.");
        Preconditions.checkArgument(tenantCreationRequestDTO.city != null, "Tenant city cannot be null.");
        Preconditions.checkArgument(tenantCreationRequestDTO.address != null, "Tenant address cannot be null.");
        Preconditions.checkArgument(tenantCreationRequestDTO.cap != null, "Tenant cap cannot be null.");
        Preconditions.checkArgument(tenantCreationRequestDTO.email != null, "Tenant email cannot be null.");
        Preconditions.checkArgument(tenantCreationRequestDTO.password != null, "Tenant password cannot be null.");

        return tenantHelper.updateTenant(tenantId, tenantCreationRequestDTO);
    }

    @Override
    public TenantDTO deleteTenant(Long tenantId) {
        Preconditions.checkArgument(tenantId != null, "Tenant id cannot be null.");

        return tenantHelper.deleteTenant(tenantId);
    }*/

    @Override
    public TenantDTO loginTenant(String email, String password) {
        Preconditions.checkArgument(email != null, "Tenant email cannot be null");
        Preconditions.checkArgument(password != null, "Tenant password cannot be null");

        return tenantHelper.loginTenant(email, password);
    }
}
