package com.katyshevtseva.ww.view.utils;

import com.katyshevtceva.collage.logic.*;
import com.katyshevtseva.fx.Point;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.image.ImageContainer;
import com.katyshevtseva.ww.core.Service;
import com.katyshevtseva.ww.core.entity.CollageEntity;
import com.katyshevtseva.ww.core.entity.ComponentEntity;
import com.katyshevtseva.ww.core.entity.Piece;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.stream.Collectors;

import static com.katyshevtseva.ww.view.utils.WrdImageUtils.toCollageImages;

public class CollageUtils {
    public static final int COLLAGE_SIZE = 850;

    public static CollageEntity saveCollage(CollageEntity existing, Collage collage) {
        CollageEntity savedCollageEntity = Service.saveCollage(existing);

        List<ComponentEntity> componentEntities = collage.getComponents().stream()
                .map(component -> toEntity(component, savedCollageEntity)).collect(Collectors.toList());

        Service.saveComponents(componentEntities, savedCollageEntity);

        return savedCollageEntity;
    }

    private static ComponentEntity toEntity(Component component, CollageEntity collageEntity) {
        Piece piece = ((WrdImageUtils.ImageAndPieceContainer) component.getImageContainer()).getPiece();

        ComponentEntity entity = new ComponentEntity();
        entity.setRelativeX(component.getRelativeX());
        entity.setRelativeY(component.getRelativeY());
        entity.setRelativeWidth(component.getRelativeWidth());
        entity.setZ(component.getZ());
        entity.setPiece(piece);
        entity.setCollageEntity(collageEntity);

        return entity;
    }

    public static Pane getCollagePreview(CollageEntity collageEntity) {
        List<StaticComponent> staticComponents = collageEntity.getComponents().stream()
                .map(componentEntity -> new StaticComponent(
                        WrdImageUtils.getImageContainer(componentEntity.getPiece()),
                        new Point(componentEntity.getRelativeX(), componentEntity.getRelativeY()),
                        componentEntity.getRelativeWidth(),
                        componentEntity.getZ()))
                .collect(Collectors.toList());

        return CollagePreviewBuilder.buildPreview(new Size(370, 370), staticComponents);
    }

    public static Collage reproduceCollage(CollageEntity collageEntity) {
        if (collageEntity == null)
            return createEmptyCollage();

        Collage collage = new CollageBuilder()
                .allExistingImages(toCollageImages(Service.getActivePieces()))
                .height(COLLAGE_SIZE)
                .width(COLLAGE_SIZE)
                .build();

        for (ComponentEntity componentEntity : collageEntity.getComponents()) {
            collage.addComponent(entityToComponent(componentEntity, collage));
        }

        return collage;
    }

    private static Component entityToComponent(ComponentEntity componentEntity, Collage collage) {
        ImageContainer imageContainer = WrdImageUtils.toImageAndPieceContainer(componentEntity.getPiece());

        return new ComponentBuilder(collage, imageContainer)
                .relativeWidth(componentEntity.getRelativeWidth())
                .z(componentEntity.getZ())
                .relativePosition(new Point(componentEntity.getRelativeX(), componentEntity.getRelativeY()))
                .build();
    }

    public static Collage createEmptyCollage() {
        return new CollageBuilder()
                .height(COLLAGE_SIZE)
                .width(COLLAGE_SIZE)
                .allExistingImages(toCollageImages(Service.getActivePieces()))
                .build();
    }
}
