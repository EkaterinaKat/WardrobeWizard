package com.katyshevtseva.ww.core.entity;

import com.katyshevtseva.ww.core.enums.Category;
import com.katyshevtseva.ww.core.enums.OutfitSeason;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Outfit {
    @Enumerated(EnumType.STRING)
    OutfitSeason season;
    @Enumerated(EnumType.STRING)
    Category category;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    @OneToOne
    @JoinColumn(name = "collage_id")
    private CollageEntity collageEntity;

    public String getFullDesc() {
        StringBuilder fullDesc = new StringBuilder("(").append(id).append(") ").append(comment != null ? comment : "");

        if (season != null) {
            fullDesc.append("\n\n").append(season).append("\n");
        }

        return fullDesc.toString();
    }

//    public boolean containsArchivedPieces() {
//        for (Piece piece : collageEntity.getComponents().stream().map(ComponentEntity::getPiece).collect(Collectors.toList())) {
//            if (piece.getEndDate() != null) {
//                return true;
//            }
//        }
//        return false;
//    }
}
