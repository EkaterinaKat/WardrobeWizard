package com.katyshevtseva.ww;

import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.ww.view.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

import static com.katyshevtseva.ww.view.utils.ViewConstants.WwDialogInfo.MAIN;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        WindowBuilder.openDialog(MAIN, new MainController());
    }
}