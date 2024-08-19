package com.katyshevtseva.ww.core;

import com.katyshevtseva.hierarchy.StaticHierarchySchemaLine;
import com.katyshevtseva.ww.core.enums.PieceSubtype;
import com.katyshevtseva.ww.core.enums.PieceSupertype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class WardrobeHierarchyUtil {

    public static List<StaticHierarchySchemaLine> getSchema() {
        List<StaticHierarchySchemaLine> lines = new ArrayList<>(getUnclassifiedTypes());
        for (PieceSupertype supertype : PieceSupertype.values()) {
            lines.add(supertype);
            lines.addAll(supertype.getTypes());
        }
        return lines;
    }

    private static List<PieceSubtype> getUnclassifiedTypes() {
        List<PieceSubtype> types = new LinkedList<>(Arrays.asList(PieceSubtype.values()));
        for (PieceSupertype supertype : PieceSupertype.values()) {
            types.removeAll(supertype.getTypes());
        }
        return types;
    }
}
