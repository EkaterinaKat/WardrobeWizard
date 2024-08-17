package com.katyshevtseva.ww.core.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "collage_entity")
public class CollageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToMany(mappedBy = "collageEntity", fetch = FetchType.EAGER)
//    private Set<ComponentEntity> components;
}
