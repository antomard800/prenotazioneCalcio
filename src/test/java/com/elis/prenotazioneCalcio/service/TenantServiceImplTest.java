package com.elis.prenotazioneCalcio.service;

import com.elis.prenotazioneCalcio.dto.tenant.TenantCreationRequestDTO;
import com.elis.prenotazioneCalcio.model.Tenant;
import com.elis.prenotazioneCalcio.repository.TenantRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TenantServiceImplTest {
    @Autowired
    TenantRepository tenantRepository;

    @Autowired
    TenantServiceImpl tenantService;

    @Before
    @Transactional
    public void setup() {
        tenantRepository.deleteAll();
    }

    @Test
    public void getTenants() {
        Tenant firstTenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("elis@gmail.com")
                .password("ciao")
                .build());

        Tenant secondTenant = tenantRepository.save(Tenant.builder()
                .name("Safi")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("safi@gmail.com")
                .password("ciao")
                .build());

        Tenant thirdTenant = tenantRepository.save(Tenant.builder()
                .name("Milan")
                .city("Milano")
                .address("Via San Siro")
                .cap(718)
                .email("milan@gmail.com")
                .password("milan")
                .build());

        tenantService.getTenants();

        assertNotNull("Tenants not found", tenantRepository.findAll());
        assertEquals("Tenants are not 3", 3, tenantRepository.findAll().size());
    }

    @Test
    public void createTenant() {
        Tenant tenant = Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build();

        TenantCreationRequestDTO tenantCreationRequestDTO = new TenantCreationRequestDTO();
        tenantCreationRequestDTO.name = tenant.getName();
        tenantCreationRequestDTO.city = tenant.getCity();
        tenantCreationRequestDTO.address = tenant.getAddress();
        tenantCreationRequestDTO.cap = tenant.getCap();
        tenantCreationRequestDTO.email = tenant.getEmail();
        tenantCreationRequestDTO.password = tenant.getPassword();

        tenantService.createTenant(tenantCreationRequestDTO);

        tenant = tenantRepository.save(tenant);

        assertNotNull("Id is null", tenant.getId());
        assertNotNull("Name is null", tenantRepository.findById(tenant.getId()).get().getName());
        assertNotNull("City is null", tenantRepository.findById(tenant.getId()).get().getCity());
        assertNotNull("Address is null", tenantRepository.findById(tenant.getId()).get().getAddress());
        assertNotNull("Cap is null", tenantRepository.findById(tenant.getId()).get().getCap());
        assertNotNull("Email is null", tenantRepository.findById(tenant.getId()).get().getEmail());
        assertNotNull("Password is null", tenantRepository.findById(tenant.getId()).get().getPassword());

        assertEquals("Name is different", tenant.getName(), tenantRepository.findById(tenant.getId()).get().getName());
        assertEquals("City is different", tenant.getCity(), tenantRepository.findById(tenant.getId()).get().getCity());
        assertEquals("Address is different", tenant.getAddress(), tenantRepository.findById(tenant.getId()).get().getAddress());
        assertEquals("Cap is different", tenant.getCap(), tenantRepository.findById(tenant.getId()).get().getCap());
        assertEquals("Email is different", tenant.getEmail(), tenantRepository.findById(tenant.getId()).get().getEmail());
        assertEquals("Password is different", tenant.getPassword(), tenantRepository.findById(tenant.getId()).get().getPassword());
    }

    @Test
    public void getTenant() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("elis@gmail.com")
                .password("ciao")
                .build());

        tenantService.getTenant(tenant.getId());

        assertNotNull("Tenant not found", tenantRepository.findById(tenant.getId()));
        assertEquals("Tenant is different", tenant.getEmail(), tenantRepository.findById(tenant.getId()).get().getEmail());
    }
}
