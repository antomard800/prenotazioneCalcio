package com.elis.footballmanager.service;

import com.elis.footballmanager.model.Tenant;
import com.elis.footballmanager.repository.GameRepository;
import com.elis.footballmanager.repository.TenantRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class GameServiceImplTest {
    @Autowired
    GameServiceImpl matchService;

    @Autowired
    TenantRepository tenantRepository;

    @Autowired
    GameRepository gameRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getTenantMatches() {
        /*Tenant tenant = generateTenant();

        List<Match> matches = new ArrayList<>();

        Match match1 = matchRepository.save(Match.builder()
                .date(LocalDateTime.now())
                .tenant(tenant)
                .build());

        Match match2 = matchRepository.save(Match.builder()
                .date(LocalDateTime.now())
                .tenant(tenant)
                .build());

        matches.add(match1);
        matches.add(match2);

        tenant.setMatches(matches);

        Tenant newTenant = tenantRepository.saveAndFlush(tenant);*/
    }

    @Test
    void createTenantMatch() {
    }

    private Tenant generateTenant(){
        Tenant tenant = Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build();

        return tenantRepository.saveAndFlush(tenant);
    }
}