package com.elis.footballmanager.dto.tenant;

import com.elis.footballmanager.model.Tenant;


public class TenantDTO {
    public Long id;
    public String name;
    public String city;
    public String address;
    public Integer cap;
    public String email;

    public static TenantDTO of(Tenant tenant){
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
