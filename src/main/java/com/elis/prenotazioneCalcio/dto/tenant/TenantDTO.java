package com.elis.prenotazioneCalcio.dto.tenant;

import com.elis.prenotazioneCalcio.model.Tenant;


public class TenantDTO {
    //Attributes to send to front-end
    public Long id;
    public String name;
    public String city;
    public String address;
    public Integer cap;
    public String email;

    //Transform a Game into a GameDTO
    public static TenantDTO of(Tenant tenant) {
        TenantDTO tenantDTO = new TenantDTO();

        tenantDTO.id = tenant.getId();
        tenantDTO.name = tenant.getName();
        tenantDTO.city = tenant.getCity();
        tenantDTO.address = tenant.getAddress();
        tenantDTO.cap = tenant.getCap();
        tenantDTO.email = tenant.getEmail();

        return tenantDTO;
    }
}
