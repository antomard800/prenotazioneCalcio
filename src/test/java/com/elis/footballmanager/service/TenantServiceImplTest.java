package com.elis.footballmanager.service;

import com.elis.prenotazioneCalcio.dto.tenant.TenantCreationRequestDTO;
import com.elis.prenotazioneCalcio.model.Tenant;
import com.elis.prenotazioneCalcio.repository.TenantRepository;
import com.elis.prenotazioneCalcio.service.TenantServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TenantServiceImplTest {
    @Autowired
    TenantRepository tenantRepository;

    @Autowired
    TenantServiceImpl tenantService;

    @BeforeEach
    @Transactional
    void setup() {
        tenantRepository.deleteAll();
    }

    @Test
    void getTenants() {
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

        assertNotNull(tenantRepository.findAll(), "Tenants not found");
        assertEquals(tenantRepository.findAll().size(), 3, "Tenants are not 3");
    }

    @Test
    void createTenant() {
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

        Assertions.assertNotNull(tenant.getId(), "Id is null");
        Assertions.assertNotNull(tenantRepository.findById(tenant.getId()).get().getName(), "Name is null");
        Assertions.assertNotNull(tenantRepository.findById(tenant.getId()).get().getCity(), "City is null");
        Assertions.assertNotNull(tenantRepository.findById(tenant.getId()).get().getAddress(), "Address is null");
        Assertions.assertNotNull(tenantRepository.findById(tenant.getId()).get().getCap(), "Cap is null");
        Assertions.assertNotNull(tenantRepository.findById(tenant.getId()).get().getEmail(), "Email is null");
        Assertions.assertNotNull(tenantRepository.findById(tenant.getId()).get().getPassword(), "Password is null");

        assertEquals(tenant.getName(), tenantRepository.findById(tenant.getId()).get().getName(), "Name is different");
        assertEquals(tenant.getCity(), tenantRepository.findById(tenant.getId()).get().getCity(), "City is different");
        assertEquals(tenant.getAddress(), tenantRepository.findById(tenant.getId()).get().getAddress(), "Address is different");
        assertEquals(tenant.getCap(), tenantRepository.findById(tenant.getId()).get().getCap(), "Cap is different");
        assertEquals(tenant.getEmail(), tenantRepository.findById(tenant.getId()).get().getEmail(), "Email is different");
        assertEquals(tenant.getPassword(), tenantRepository.findById(tenant.getId()).get().getPassword(), "Password is different");
    }

    @Test
    void getTenant() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("elis@gmail.com")
                .password("ciao")
                .build());

        tenantService.getTenant(tenant.getId());

        Assertions.assertNotNull(tenantRepository.findById(tenant.getId()), "Tenant not found");
        assertEquals(tenant.getEmail(), tenantRepository.findById(tenant.getId()).get().getEmail(), "Tenant is different");
    }

    @Test
    void deleteTenant() {
        Tenant firstTenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
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

        tenantService.deleteTenant(firstTenant.getId());

        assertEquals(Optional.empty(), tenantRepository.findById(firstTenant.getId()), "Tenant not deleted");
        assertEquals(tenantRepository.findAll().size(), 1, "Tenant not well deleted");
    }

    @Test
    void loginTenant() {
        Tenant firstTenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
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

        tenantService.loginTenant(firstTenant.getEmail(), firstTenant.getPassword());

        assertEquals(firstTenant.getId(), tenantRepository.findByEmailAndPassword("ciao@gmail.com", "ciao").get().getId(), "Tenant login failed");
        Assertions.assertNotEquals(secondTenant.getId(), tenantRepository.findByEmailAndPassword("ciao@gmail.com", "ciao").get().getId(), "Tenant login success");
    }
}
