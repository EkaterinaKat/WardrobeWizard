package org.katyshevtseva.wardrobewizard;

import com.katyshevtseva.fx.WindowBuilder;
import javafx.application.Application;
import javafx.stage.Stage;
import org.katyshevtseva.wardrobewizard.view.MainController;

import static org.katyshevtseva.wardrobewizard.view.utils.ViewConstants.WwDialogInfo.MAIN;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        WindowBuilder.openDialog(MAIN, new MainController());
    }
}