package com.katyshevtseva.ww.view;

import com.katyshevtseva.fx.Styler;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.fx.component.ComponentBuilder;
import com.katyshevtseva.fx.component.controller.PaginationPaneController;
import com.katyshevtseva.fx.dialog.StandardDialogBuilder;
import com.katyshevtseva.general.GeneralUtils;
import com.katyshevtseva.general.OneInOneOutKnob;
import com.katyshevtseva.general.Page;
import com.katyshevtseva.ww.core.Service;
import com.katyshevtseva.ww.core.entity.Outfit;
import com.katyshevtseva.ww.view.utils.CollageUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.katyshevtseva.fx.Styler.StandardColor.RED;
import static com.katyshevtseva.fx.Styler.ThingToColor.BORDER;
import static com.katyshevtseva.ww.view.utils.ViewConstants.WwDialogInfo.OUTFIT_EDIT;

@RequiredArgsConstructor
public class OutfitGridController implements FxController {
    private final OneInOneOutKnob<Integer, Page<Outfit>> outfitPageSupplier;
    private PaginationPaneController<Outfit> paginationPaneController;
    @FXML
    private Button outfitEditButton;
    @FXML
    private Button outfitDeleteButton;
    @FXML
    private GridPane gridPane;
    @FXML
    private Label infoLabel;
    @FXML
    private Pane paginationPane;

    @FXML
    private void initialize() {
        tunePagination();
    }

    void loadPage() {
        paginationPaneController.loadPage();
    }

    private void tunePagination() {
        ComponentBuilder.Component<PaginationPaneController<Outfit>> component =
                new ComponentBuilder().getPaginationComponent(outfitPageSupplier::execute, this::setContent);
        paginationPaneController = component.getController();
        paginationPane.getChildren().add(component.getNode());
    }

    private void showOutfitInfo(Outfit outfit) {
        infoLabel.setText(outfit.getFullDesc());
        outfitEditButton.setVisible(true);
        outfitDeleteButton.setVisible(true);
        outfitEditButton.setOnAction(event -> WindowBuilder.openDialog(OUTFIT_EDIT,
                new OutfitDialogController(outfit, savedOutfit -> {
                    paginationPaneController.loadPage();
                    showOutfitInfo(savedOutfit);
                })));
        outfitDeleteButton.setOnAction(event -> new StandardDialogBuilder().openQuestionDialog("Delete?",
                b -> {
                    if (b) {
                        Service.deleteOutfit(outfit);
                        paginationPaneController.loadPage();
                    }
                }));
    }

    private void clearOutfitInfo() {
        infoLabel.setText("");
        outfitEditButton.setVisible(false);
        outfitDeleteButton.setVisible(false);
    }

    private void setContent(List<Outfit> outfits) {
        gridPane.getChildren().clear();
        clearOutfitInfo();
        int index = 0;

        for (Outfit outfit : outfits) {
            Pane pane = CollageUtils.getCollagePreview(outfit.getCollageEntity());
            if (outfit.containsArchivedPieces()) {
                pane.setStyle(Styler.getColorfullStyle(BORDER, RED) + Styler.getBorderWidth(7));
            } else {
                pane.setStyle(Styler.getBlackBorderStyle());
            }
            pane.setOnMouseClicked(event -> showOutfitInfo(outfit));
            gridPane.add(pane, GeneralUtils.getColumnByIndexAndColumnNum(index, 2),
                    GeneralUtils.getRowByIndexAndColumnNum(index, 2));
            index++;
        }
    }
}
