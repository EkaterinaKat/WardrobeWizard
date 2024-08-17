package com.katyshevtseva.ww.view;

import com.katyshevtseva.fx.switchcontroller.SectionController;
import com.katyshevtseva.ww.core.Dao;
import javafx.fxml.FXML;

public class ItemsController implements SectionController {

    @FXML
    private void initialize() {
        System.out.println(Dao.getAllPiece());
    }
}
