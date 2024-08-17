package com.katyshevtseva.ww.view;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.fx.switchcontroller.AbstractSwitchController;
import com.katyshevtseva.fx.switchcontroller.Section;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

import static com.katyshevtseva.ww.view.utils.ViewConstants.WwNodeInfo.ITEMS;

public class MainController extends AbstractSwitchController implements WindowBuilder.FxController {

    @FXML
    private Pane mainPane;
    @FXML
    private VBox buttonBox;

    @FXML
    private void initialize() {
        init(getSections(), mainPane, this::placeButton);
    }

    private List<Section> getSections() {
        return Arrays.asList(
                new Section("Items", new ItemsController(),
                controller -> WindowBuilder.getNode(ITEMS, controller)),
                new Section("Items", new ItemsController(),
                        controller -> WindowBuilder.getNode(ITEMS, controller)));
    }

    private void placeButton(Button button) {
        FxUtils.setWidth(button, 180);
        buttonBox.getChildren().addAll(FxUtils.getPaneWithHeight(40), button);
    }
}
