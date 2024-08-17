package com.katyshevtseva.ww.core;

import com.katyshevtseva.hibernate.CoreDao;
import com.katyshevtseva.ww.core.entity.Piece;

import java.util.List;

public class Dao {
    private static final CoreDao coreDao = new CoreDao();

    public static <T> void saveNew(T t) {
        coreDao.saveNew(t);
    }

    public static <T> void saveEdited(T t) {
        coreDao.update(t);
    }

    public static <T> void delete(T t) {
        coreDao.delete(t);
    }

    /////////////////////////////////////////////// PIECE ///////////////////////////////////////////////

    public static List<Piece> getAllPiece() {
        return coreDao.getAll(Piece.class.getSimpleName());
    }
}
