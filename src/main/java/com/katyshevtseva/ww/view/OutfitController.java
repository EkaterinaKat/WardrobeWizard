package com.katyshevtseva.ww.view;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.fx.switchcontroller.SectionController;
import com.katyshevtseva.general.Page;
import com.katyshevtseva.ww.core.Service;
import com.katyshevtseva.ww.core.entity.Outfit;
import com.katyshevtseva.ww.core.enums.Category;
import com.katyshevtseva.ww.core.enums.OutfitSeason;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

import static com.katyshevtseva.ww.view.utils.ViewConstants.WwDialogInfo.OUTFIT_EDIT;
import static com.katyshevtseva.ww.view.utils.ViewConstants.WwNodeInfo.OUTFIT_GRID;

class OutfitController implements SectionController {
    @FXML
    private Button outfitCreateButton;
    @FXML
    private ComboBox<OutfitSeason> seasonComboBox;
    @FXML
    private Button clearSeasonButton;
    @FXML
    private Pane outfitGridContainer;
    @FXML
    private ComboBox<Category> categoryComboBox;
    private final OutfitGridController gridController = new OutfitGridController(this::getOutfitPage);

    @FXML
    private void initialize() {
        outfitCreateButton.setOnAction(event -> WindowBuilder.openDialog(OUTFIT_EDIT,
                new OutfitDialogController(categoryComboBox.getValue(), outfit -> gridController.loadPage())));
        tuneFilters();
        outfitGridContainer.getChildren().add(WindowBuilder.getNode(OUTFIT_GRID, gridController));
    }

    Page<Outfit> getOutfitPage(int pageNum) {
        return Service.getOutfitPage(pageNum, seasonComboBox.getValue(), categoryComboBox.getValue());
    }

    private void tuneFilters() {
        FxUtils.setComboBoxItems(seasonComboBox, OutfitSeason.values());
        seasonComboBox.setOnAction(event -> gridController.loadPage());
        clearSeasonButton.setOnAction(event -> {
            seasonComboBox.setValue(null);
            gridController.loadPage();
        });

        FxUtils.setComboBoxItems(categoryComboBox, Category.values(), Category.GOING_OUT);
        categoryComboBox.setOnAction(event -> gridController.loadPage());
    }
}
