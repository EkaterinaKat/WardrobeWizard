package com.katyshevtseva.ww.view;

import com.katyshevtseva.fx.FxImageCreationUtil;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.fx.dialog.StandardDialogBuilder;
import com.katyshevtseva.general.OneArgKnob;
import com.katyshevtseva.image.ImageContainer;
import com.katyshevtseva.ww.core.Service;
import com.katyshevtseva.ww.core.entity.Piece;
import com.katyshevtseva.ww.core.enums.Category;
import com.katyshevtseva.ww.core.enums.PieceSubtype;
import com.katyshevtseva.ww.view.utils.WrdImageUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import static com.katyshevtseva.fx.FxUtils.*;
import static com.katyshevtseva.fx.ImageSizeUtil.placeImageInSquare;

class PieceDialogController implements FxController {
    private final Piece existing;
    private final OneArgKnob<Piece> onSaveListener;
    private ImageContainer selectedImage;
    @FXML
    private Pane imagePane;
    @FXML
    private TextArea descTextArea;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ComboBox<PieceSubtype> typeComboBox;
    @FXML
    private ComboBox<Category> categoryComboBox;
    @FXML
    private Button saveButton;

    PieceDialogController(Piece existing, OneArgKnob<Piece> onSaveListener) {
        this.existing = existing;
        this.onSaveListener = onSaveListener;
    }

    @FXML
    private void initialize() {
        associateButtonWithControls(saveButton, typeComboBox, descTextArea, categoryComboBox);
        saveButton.setOnAction(event -> save());
        setComboBoxItems(typeComboBox, PieceSubtype.getSortedByTitleValues());
        setComboBoxItems(categoryComboBox, Category.values());
        showImage(FxImageCreationUtil.getIcon(FxImageCreationUtil.IconPicture.GREY_PLUS));
        setExistingPieceInfo();
    }

    private void save() {
        Piece saved = Service.savePiece(
                existing,
                descTextArea.getText().trim(),
                selectedImage.getFileName(),
                typeComboBox.getValue(),
                categoryComboBox.getValue(),
                getDate(startDatePicker),
                getDate(endDatePicker));
        onSaveListener.execute(saved);
        closeWindowThatContains(typeComboBox);
    }

    private void openImageSelectionDialog() {
        new StandardDialogBuilder().openImageSelectionDialog(
                WrdImageUtils.getFreeImagesForPieceCreation(),
                imageContainer -> {
                    selectedImage = imageContainer;
                    showImage(selectedImage.getImage());
                });
    }

    private void showImage(Image image) {
        imagePane.getChildren().clear();
        Node imageNode = placeImageInSquare(new ImageView(image), 400);
        imageNode.setOnMouseClicked(event -> openImageSelectionDialog());
        imagePane.getChildren().add(imageNode);
    }

    private void setExistingPieceInfo() {
        if (existing != null) {
            ImageContainer imageContainer = WrdImageUtils.getImageContainer(existing);

            showImage(imageContainer.getImage());
            descTextArea.setText(existing.getDescription());
            typeComboBox.setValue(existing.getType());
            selectedImage = imageContainer;
            setDate(startDatePicker, existing.getStartDate());
            setDate(endDatePicker, existing.getEndDate());
            startDatePicker.setDisable(true);
            endDatePicker.setDisable(true);
        }
    }
}
