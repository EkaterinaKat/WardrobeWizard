package com.katyshevtseva.ww.core;

import com.katyshevtseva.general.Page;
import com.katyshevtseva.ww.core.entity.CollageEntity;
import com.katyshevtseva.ww.core.entity.ComponentEntity;
import com.katyshevtseva.ww.core.entity.Outfit;
import com.katyshevtseva.ww.core.entity.Piece;
import com.katyshevtseva.ww.core.enums.Category;
import com.katyshevtseva.ww.core.enums.OutfitSeason;
import com.katyshevtseva.ww.core.enums.PieceState;
import com.katyshevtseva.ww.core.enums.PieceSubtype;

import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsFirst;

public class Service {
    private static final Repo repo = new Repo();

    public static List<Piece> getAllPieces() {
        return Dao.findAllPieces();
    }

    public static List<Piece> getActivePieces() {
        return repo.findByEndDateIsNull().stream()
                .sorted(Comparator.comparing(Piece::getStartDate, nullsFirst(naturalOrder())))
                .collect(Collectors.toList());
    }

    public static List<Piece> getPieces(PieceState pieceState, Category category) {
        return repo.getPieces(pieceState, category);
    }

    public static Page<Piece> getPiecePage(int pageNum, PieceType pieceType, PieceState pieceState, Category category) {
        return repo.getPiecePage(pageNum, pieceType, pieceState, category);
    }

    public static List<Piece> getPiecesToAddToOutfit(PieceType pieceType, Category category) {
        return repo.getPiecesToAddToOutfit(pieceType, category);
    }

    public static Page<Outfit> getOutfitPage(int pageNum, OutfitSeason season, Category category) {
        return repo.getOutfitPage(pageNum, season, category);
    }

    public static Page<Outfit> getOutfitsByPiece(int pageNum, Piece piece) {
        return repo.getOutfitsByPiece(pageNum, piece);
    }

    public static Piece savePiece(Piece existing,
                                  String description,
                                  String imageFileName,
                                  PieceSubtype type,
                                  Category category,
                                  Date start,
                                  Date end) {

        if (existing == null)
            existing = new Piece();
        existing.setImageFileName(imageFileName);
        existing.setDescription(description);
        existing.setStartDate(start);
        existing.setEndDate(end);
        existing.setType(type);
        existing.setCategory(category);

        return Dao.save(existing);
    }

    public static CollageEntity saveCollage(CollageEntity existing) {
        if (existing == null)
            existing = new CollageEntity();

        return Dao.save(existing);
    }

    //    @Transactional
    public static void saveComponents(List<ComponentEntity> components, CollageEntity collageEntity) {
        repo.deleteByCollageEntity(collageEntity);

        for (ComponentEntity componentEntity : components) {
            Dao.save(componentEntity);
        }
    }

    public static Outfit saveOutfit(Outfit existing, String comment,
                                    OutfitSeason season,
                                    CollageEntity collageEntity, Category category) {
        if (season == null) {
            throw new RuntimeException("Сезон не заполнены");
        }

        if (existing == null) {
            existing = new Outfit();
            existing.setCategory(category);
        }
        existing.setComment(comment != null ? comment.trim() : null);
        existing.setSeason(season);
        existing.setCollageEntity(collageEntity);
        existing.setCategory(category);

        return Dao.save(existing);
    }

    public static void deleteOutfit(Outfit outfit) {
        for (ComponentEntity componentEntity : outfit.getCollageEntity().getComponents()) {
            Dao.delete(componentEntity);
        }
        outfit.getCollageEntity().setComponents(new HashSet<>());
        Dao.delete(outfit);
        Dao.delete(outfit.getCollageEntity());
    }

    public static void archivePiece(Piece piece) {
        if (piece.getEndDate() != null) {
            throw new RuntimeException("Вещь уже архивирована");
        }

        piece.setEndDate(new Date());
        Dao.save(piece);
    }

    public static void returnToWork(Piece piece) {
        if (piece.getEndDate() == null) {
            throw new RuntimeException("Вещь уже в работе");
        }

        piece.setEndDate(null);
        Dao.save(piece);
    }

    public static OutfitSeason getPieceSeasonOrNull(Piece piece) {
        List<OutfitSeason> seasons = repo.findOutfitsByPiece(piece).stream()
                .map(Outfit::getSeason).distinct().collect(Collectors.toList());
        if (seasons.size() == 1)
            return seasons.get(0);
        return null;
    }
}
