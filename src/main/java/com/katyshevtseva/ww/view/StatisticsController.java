package com.katyshevtseva.ww.view;

import com.katyshevtseva.fx.switchcontroller.SectionController;
import com.katyshevtseva.ww.core.StatisticsService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StatisticsController implements SectionController {
    @FXML
    private Label label;

    @Override
    public void update() {
        label.setText(StatisticsService.getPieceStatistics());
    }
}
