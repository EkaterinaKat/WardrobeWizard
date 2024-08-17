package com.katyshevtseva.ww.core.enums;

import com.katyshevtseva.hierarchy.StaticHierarchySchemaLine;
import com.katyshevtseva.ww.core.PieceType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum PieceSupertype implements StaticHierarchySchemaLine, PieceType {
    UP("Верх", Arrays.asList(PieceSubtype.OTHER_UP, PieceSubtype.TOP, PieceSubtype.HOODIE, PieceSubtype.JUMPER, PieceSubtype.T_SHORT, PieceSubtype.SHIRT, PieceSubtype.SWEATSHIRT, PieceSubtype.SWEATER, PieceSubtype.CARDIGAN)),
    DOWN("Низ", Arrays.asList(PieceSubtype.OTHER_TROUSERS, PieceSubtype.SKIRT, PieceSubtype.PANTS, PieceSubtype.JEANS, PieceSubtype.LEGGINGS, PieceSubtype.SPORTS_TROUSERS, PieceSubtype.SHORTS)),
    FOOTWEAR("Обувь", Arrays.asList(PieceSubtype.OTHER_FOOTWEAR, PieceSubtype.SHOES, PieceSubtype.LOW_BOOTS, PieceSubtype.SNEAKERS, PieceSubtype.BOOTS)),
    ACCESSORIES("Аксессуары", Arrays.asList(PieceSubtype.HEADDRESS, PieceSubtype.SCARF, PieceSubtype.GLOVES, PieceSubtype.BACKPACK, PieceSubtype.BAG));

    private final String title;
    private final List<PieceSubtype> types;

    PieceSupertype(String title, List<PieceSubtype> types) {
        this.title = title;
        this.types = types;
    }

    public static List<PieceSupertype> getSupertypesBySubtype(PieceSubtype subtype) {
        return Arrays.stream(PieceSupertype.values())
                .filter(supertype -> supertype.getTypes().contains(subtype))
                .collect(Collectors.toList());
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public String getDescription() {
        return title;
    }

    public List<PieceSubtype> getTypes() {
        return types;
    }

    @Override
    public List<PieceSubtype> getSubtypes() {
        return getTypes();
    }
}
