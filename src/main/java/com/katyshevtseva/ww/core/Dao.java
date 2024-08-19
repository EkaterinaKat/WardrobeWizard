package com.katyshevtseva.ww.core;

import com.katyshevtseva.hibernate.CoreDao;
import com.katyshevtseva.ww.core.entity.CollageEntity;
import com.katyshevtseva.ww.core.entity.ComponentEntity;
import com.katyshevtseva.ww.core.entity.Outfit;
import com.katyshevtseva.ww.core.entity.Piece;

import java.util.List;

public class Dao {
    private static final CoreDao coreDao = new CoreDao();

    public static <T> void delete(T t) {
        coreDao.delete(t);
    }

    /////////////////////////////////////////////// PIECE ///////////////////////////////////////////////

    public static Piece save(Piece piece) {
        return coreDao.save(Piece.class, piece);
    }

    public static List<Piece> findAllPieces() {
        return coreDao.getAll(Piece.class.getSimpleName());
    }

    /////////////////////////////////////////////// COMPONENT_ENTITY ///////////////////////////////////////////////

    public static ComponentEntity save(ComponentEntity componentEntity) {
        return coreDao.save(ComponentEntity.class, componentEntity);
    }

    /////////////////////////////////////////////// COLLAGE_ENTITY ///////////////////////////////////////////////

    public static CollageEntity save(CollageEntity collageEntity) {
        return coreDao.save(CollageEntity.class, collageEntity);
    }

    /////////////////////////////////////////////// OUTFIT ///////////////////////////////////////////////

    public static Outfit save(Outfit outfit) {
        return coreDao.save(Outfit.class, outfit);
    }
}
