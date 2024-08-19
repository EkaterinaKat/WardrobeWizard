package com.katyshevtseva.ww.view;

import com.katyshevtseva.fx.FxImageCreationUtil;
import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.fx.component.ComponentBuilder;
import com.katyshevtseva.fx.component.controller.GalleryController;
import com.katyshevtseva.fx.component.controller.PaginationPaneController;
import com.katyshevtseva.fx.dialog.StandardDialogBuilder;
import com.katyshevtseva.fx.switchcontroller.SectionController;
import com.katyshevtseva.general.Page;
import com.katyshevtseva.image.ImageContainer;
import com.katyshevtseva.ww.core.PieceType;
import com.katyshevtseva.ww.core.Service;
import com.katyshevtseva.ww.core.entity.Piece;
import com.katyshevtseva.ww.core.enums.Category;
import com.katyshevtseva.ww.core.enums.OutfitSeason;
import com.katyshevtseva.ww.core.enums.PieceState;
import com.katyshevtseva.ww.view.utils.WrdImageUtils;
import com.katyshevtseva.ww.view.utils.WrdImageUtils.ImageAndPieceContainer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

import static com.katyshevtseva.fx.ImageSizeUtil.placeImageInSquare;
import static com.katyshevtseva.ww.view.utils.ViewConstants.CLOTHES_TYPE_SELECT_DIALOG_SIZE;
import static com.katyshevtseva.ww.view.utils.ViewConstants.WwDialogInfo.PIECE_EDIT;
import static com.katyshevtseva.ww.view.utils.ViewConstants.WwNodeInfo.OUTFIT_GRID;

public class PiecesController implements SectionController {
    private GalleryController galleryController;
    private PaginationPaneController<Piece> paginationPaneController;
    private PieceType pieceType;
    @FXML
    private Button pieceCreateButton;
    @FXML
    private Pane imagePane;
    @FXML
    private Label infoLabel;
    @FXML
    private Button editButton;
    @FXML
    private VBox galleryPane;
    @FXML
    private Pane paginationPane;
    @FXML
    private Label typeLabel;
    @FXML
    private Button clearTypeButton;
    @FXML
    private Button archiveButton;
    @FXML
    private Button showOutfitsButton;
    @FXML
    private ComboBox<PieceState> stateComboBox;
    @FXML
    private ComboBox<Category> categoryComboBox;

    @FXML
    private void initialize() {
        // stateComboBox
        FxUtils.setComboBoxItems(stateComboBox, PieceState.values(), PieceState.ACTIVE);
        stateComboBox.setOnAction(event -> paginationPaneController.loadPage());

        // categoryComboBox
        FxUtils.setComboBoxItems(categoryComboBox, Category.values(), Category.GOING_OUT);
        categoryComboBox.setOnAction(event -> paginationPaneController.loadPage());

        tunePiecesGallery();
        pieceCreateButton.setOnAction(event ->
                WindowBuilder.openDialog(PIECE_EDIT,
                        new PieceDialogController(null, piece -> {
                            paginationPaneController.loadPage();
                            showPieceFullInfo(piece);
                        })));
        tunePagination();
        adjustTypeLabel();
        clearTypeButton.setOnAction(event -> {
            typeLabel.setText("Select clothes type");
            pieceType = null;
            paginationPaneController.loadPage();
        });
    }

    private void adjustTypeLabel() {
        typeLabel.setOnMouseClicked(event -> new StandardDialogBuilder()
                .setSize(CLOTHES_TYPE_SELECT_DIALOG_SIZE)
                .setTitle("Select type")
                .openNoFxmlContainerDialog(
                        new TypeSelectDialogController(Service.getPieces(stateComboBox.getValue(), categoryComboBox.getValue()),
                                type -> {
                                    pieceType = type;
                                    typeLabel.setText(type.getTitle());
                                    paginationPaneController.loadPage();
                                })));
    }

    private void tunePagination() {
        ComponentBuilder.Component<PaginationPaneController<Piece>> component =
                new ComponentBuilder().getPaginationComponent(this::getPiecePage, this::setContent);
        paginationPaneController = component.getController();
        paginationPane.getChildren().add(component.getNode());
    }

    private Page<Piece> getPiecePage(int pageNum) {
        return Service.getPiecePage(pageNum, pieceType, stateComboBox.getValue(), categoryComboBox.getValue());
    }

    private void showPieceFullInfo(Piece piece) {
        ImageContainer imageContainer = WrdImageUtils.getImageContainer(piece);

        imagePane.getChildren().clear();
        imagePane.getChildren().add(placeImageInSquare(new ImageView(imageContainer.getImage()), 350));
        infoLabel.setText(piece.getFullDesc());
        editButton.setVisible(true);
        archiveButton.setVisible(true);
        showOutfitsButton.setVisible(true);
        showOutfitsButton.setOnAction(event -> {
            OutfitGridController outfitGridController = new OutfitGridController(pageNum -> Service.getOutfitsByPiece(pageNum, piece));
            new StandardDialogBuilder().setSize(920, 1200).openNodeContainerDialog(
                    WindowBuilder.getNode(OUTFIT_GRID, outfitGridController));
        });
        editButton.setOnAction(event ->
                WindowBuilder.openDialog(PIECE_EDIT,
                        new PieceDialogController(piece, savedPiece -> {
                            paginationPaneController.loadPage();
                            showPieceFullInfo(savedPiece);
                        })));
        if (piece.getEndDate() == null) {
            archiveButton.setText("Archive");
            archiveButton.setOnAction(event ->
                    new StandardDialogBuilder().openQuestionDialog("Archive?", b -> {
                        if (b) {
                            Service.archivePiece(piece);
                            paginationPaneController.loadPage();
                        }
                    })
            );
        } else {
            archiveButton.setText("Return to work");
            archiveButton.setOnAction(event -> {
                        Service.returnToWork(piece);
                        paginationPaneController.loadPage();
                    }
            );
        }
    }

    private void setContent(List<Piece> pieces) {
        galleryController.setImageContainers(WrdImageUtils.toImageUrlAndPieceContainers(pieces));
        clearPieceInfo();
    }

    private void clearPieceInfo() {
        imagePane.getChildren().clear();
        infoLabel.setText("");
        editButton.setVisible(false);
        archiveButton.setVisible(false);
        showOutfitsButton.setVisible(false);
    }

    private void tunePiecesGallery() {
        ComponentBuilder.Component<GalleryController> component = new ComponentBuilder().setSize(new Size(750, 750))
                .getGalleryComponent(3,
                        WrdImageUtils.toImageUrlAndPieceContainers(new ArrayList<>()),
                        imageContainer -> showPieceFullInfo(((ImageAndPieceContainer) imageContainer).getPiece()));

        galleryController = component.getController();
        galleryPane.getChildren().add(component.getNode());
        galleryController.setIconSupplier(this::getSeasonImageView);
    }

    private ImageView getSeasonImageView(ImageContainer imageContainer) {
        FxImageCreationUtil.IconPicture iconPicture = null;
        OutfitSeason season = Service.getPieceSeasonOrNull(((ImageAndPieceContainer) imageContainer).getPiece());

        if (season == null) {
            return null;
        }

        switch (season) {
            case W:
                iconPicture = FxImageCreationUtil.IconPicture.WINTER;
                break;
            case S:
                iconPicture = FxImageCreationUtil.IconPicture.SUMMER;
                break;
            case DS:
                iconPicture = FxImageCreationUtil.IconPicture.AUTUMN;
        }

        return new ImageView(FxImageCreationUtil.getIcon(iconPicture));
    }
}
