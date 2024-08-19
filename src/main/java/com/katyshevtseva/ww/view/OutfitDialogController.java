package com.katyshevtseva.ww.view;

import com.katyshevtceva.collage.logic.Collage;
import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.fx.dialog.StandardDialogBuilder;
import com.katyshevtseva.general.OneArgKnob;
import com.katyshevtseva.ww.core.Service;
import com.katyshevtseva.ww.core.entity.Outfit;
import com.katyshevtseva.ww.core.enums.Category;
import com.katyshevtseva.ww.core.enums.OutfitSeason;
import com.katyshevtseva.ww.core.enums.PieceState;
import com.katyshevtseva.ww.view.utils.CollageUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import static com.katyshevtseva.fx.FxUtils.closeWindowThatContains;
import static com.katyshevtseva.ww.view.utils.CollageUtils.*;
import static com.katyshevtseva.ww.view.utils.ViewConstants.CLOTHES_TYPE_SELECT_DIALOG_SIZE;
import static com.katyshevtseva.ww.view.utils.WrdImageUtils.toCollageImages;

class OutfitDialogController implements FxController {
    private final Outfit existing;
    private final OneArgKnob<Outfit> onSaveListener;
    private final Category category;
    private Collage collage;
    @FXML
    private Pane collagePane;
    @FXML
    private Button componentAddButton;
    @FXML
    private TextArea commentTextArea;
    @FXML
    private ComboBox<OutfitSeason> seasonsComboBox;
    @FXML
    private Button saveButton;

    OutfitDialogController(Outfit existing, OneArgKnob<Outfit> onSaveListener) {
        this.existing = existing;
        category = existing.getCategory();
        this.onSaveListener = onSaveListener;
    }

    OutfitDialogController(Category category, OneArgKnob<Outfit> onSaveListener) {
        this.existing = null;
        this.category = category;
        this.onSaveListener = onSaveListener;
    }

    @FXML
    private void initialize() {
        adjustComboBoxes();
        saveButton.setOnAction(event -> save());
        setCollagePaneSize();
        tuneCollage();
        FxUtils.associateButtonWithControls(saveButton, seasonsComboBox);

        if (existing != null) {
            commentTextArea.setText(existing.getComment());
        }
    }

    private void tuneCollage() {
        collage = existing == null ? createEmptyCollage() : reproduceCollage(existing.getCollageEntity());
        collagePane.getChildren().add(collage.getPane());
        componentAddButton.setOnAction(event -> openPieceTypeSelectDialog());
    }

    private void openPieceTypeSelectDialog() {
        new StandardDialogBuilder()
                .setSize(CLOTHES_TYPE_SELECT_DIALOG_SIZE)
                .setTitle("Select type")
                .openNoFxmlContainerDialog(new TypeSelectDialogController(Service.getPieces(PieceState.ACTIVE, category),
                        type ->
                                collage.openImageToAddSelectionDialog(toCollageImages(Service.getPiecesToAddToOutfit(type, category)))));
    }

    private void setCollagePaneSize() {
        collagePane.setMinWidth(COLLAGE_SIZE);
        collagePane.setMinHeight(COLLAGE_SIZE);
        collagePane.setMaxWidth(COLLAGE_SIZE);
        collagePane.setMaxHeight(COLLAGE_SIZE);
    }

    private void save() {
        Outfit saved = Service.saveOutfit(
                existing,
                commentTextArea.getText(),
                seasonsComboBox.getValue(),
                CollageUtils.saveCollage(existing != null ? existing.getCollageEntity() : null, collage),
                category);
        onSaveListener.execute(saved);
        closeWindowThatContains(saveButton);
    }

    private void adjustComboBoxes() {
        FxUtils.setComboBoxItems(seasonsComboBox, OutfitSeason.values(), existing != null ? existing.getSeason() : null);
    }
}
