package com.katyshevtseva.ww.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "component_entity")
public class ComponentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "relative_x")
    private Double relativeX;

    @Column(name = "relative_y")
    private Double relativeY;

    private Integer z;

    @Column(name = "relative_width")
    private Double relativeWidth;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "front_piece_id", nullable = false)
    private Piece piece;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "collage_id", nullable = false)
    private CollageEntity collageEntity;

    @Override
    public String toString() {
        return "ComponentEntity{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentEntity that = (ComponentEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
