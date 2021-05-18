package com.elis.footballmanager.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pitch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float width;
    private Float length;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
}
