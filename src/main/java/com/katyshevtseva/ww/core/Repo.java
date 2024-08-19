package com.katyshevtseva.ww.core;

import com.katyshevtseva.general.Page;
import com.katyshevtseva.ww.core.entity.CollageEntity;
import com.katyshevtseva.ww.core.entity.Outfit;
import com.katyshevtseva.ww.core.entity.Piece;
import com.katyshevtseva.ww.core.enums.Category;
import com.katyshevtseva.ww.core.enums.OutfitSeason;
import com.katyshevtseva.ww.core.enums.PieceState;
import com.katyshevtseva.ww.core.enums.PieceSubtype;

import java.util.List;

public class Repo {

    List<Piece> findByEndDateIsNull() {
        return null;
    }

    public List<Piece> getPieces(PieceState pieceState, Category category) {
//        Specification<Piece> pieceSpec = new PieceSpec(null, pieceState, category);
//        return pieceRepo.findAll(pieceSpec);
        return null;
    }

    public void deleteByCollageEntity(CollageEntity collageEntity) {

    }

    public Page<Piece> getPiecePage(int pageNum, PieceType pieceType, PieceState pieceState, Category category) {
//        Pageable pageable = PageRequest.of(pageNum, 9, Sort.by("id").descending());
//        Specification<Piece> pieceSpec = new PieceSpec(pieceType, pieceState, category);
//        org.springframework.data.domain.Page<Piece> piecePage = repo.findAll(pieceSpec, pageable);
//        return new Page<>(piecePage.getContent(), pageNum, piecePage.getTotalPages());
        return null;
    }

    public List<Piece> getPiecesToAddToOutfit(PieceType pieceType, Category category) {
//        Specification<Piece> pieceSpec = new PieceSpec(pieceType, PieceState.ACTIVE, category);
//        return repo.findAll(pieceSpec, Sort.by("id").descending());
        return null;
    }

    public Page<Outfit> getOutfitPage(int pageNum, OutfitSeason season, Category category) {
//        Pageable pageable = PageRequest.of(pageNum, 4, Sort.by("id").descending());
//        org.springframework.data.domain.Page<Outfit> outfitPage =
//                outfitRepo.findAll(new OutfitSpec(season, category), pageable);
//        return new Page<>(outfitPage.getContent(), pageNum, outfitPage.getTotalPages());
        return null;
    }

    public Page<Outfit> getOutfitsByPiece(int pageNum, Piece piece) {
//        Pageable pageable = PageRequest.of(pageNum, 10, Sort.by("id").descending());
//        org.springframework.data.domain.Page<Outfit> outfitPage = outfitRepo.findOutfitsByPiece(piece, pageable);
//        return new Page<>(outfitPage.getContent(), pageNum, outfitPage.getTotalPages());
        return null;
    }

    public List<Outfit> findOutfitsByPiece(Piece piece) {
        return null;
    }

    public int countPieceByType(PieceSubtype type) {
        return 0;
    }

    public int countAllPiece() {
        return 0;
    }

    public int getArchivedPieceCount() {
        return 0;
    }

    private int getUnusedPieceCount() {
        return 0;
    }
}
