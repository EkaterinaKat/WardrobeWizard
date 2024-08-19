package com.katyshevtseva.ww.core.entity;

import com.katyshevtseva.hibernate.HasId;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "collage_entity")
public class CollageEntity implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "collageEntity", fetch = FetchType.EAGER)
    private Set<ComponentEntity> components;
}
