package com.katyshevtseva.ww.core;

import com.katyshevtseva.ww.core.enums.PieceSubtype;

public class StatisticsService {
    private static final Repo repo = new Repo();

    public static String getPieceStatistics() {
        return
                "Всего вещей: " + getAllCount() + "\n" +
                        "Архивированных вещей: " + getArchivedCount() + "\n" +
                        "Действующих вещей: " + getActiveCount() + "\n" +
                        "Используемых вещей: " + getUsedCount() + "\n" +
                        "Неиспользуемых вещей: " + getUnusedCount() + "\n\n" +
                        getClothesTypesStatistics();
    }

    private static String getClothesTypesStatistics() {
        StringBuilder stringBuilder = new StringBuilder("Категории вещей:\n");
        for (PieceSubtype pieceSubtype : PieceSubtype.getSortedByTitleValues()) {
            stringBuilder.append(" * ").append(pieceSubtype.getTitle()).append(": ").append(getCount(pieceSubtype)).append("\n");
        }
        return stringBuilder.toString();
    }

    private static int getCount(PieceSubtype type) {
        return repo.countPieceByType(type);
    }

    private static int getAllCount() {
        return repo.countAllPiece();
    }

    private static int getArchivedCount() {
        return repo.getArchivedPieceCount();
    }

    private static int getActiveCount() {
        return getAllCount() - getArchivedCount();
    }

    private static int getUsedCount() {
        return getActiveCount() - getUnusedCount();
    }

    private static int getUnusedCount() {
        return repo.getArchivedPieceCount();
    }
}
