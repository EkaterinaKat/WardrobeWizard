package com.katyshevtseva.ww.view.utils;

import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.ww.core.CoreConstants;
import lombok.Getter;

public class ViewConstants {

    public static final Size CLOTHES_TYPE_SELECT_DIALOG_SIZE = new Size(550, 470);

    public static final String WARDROBE_IMAGES_LOCATION = "D:\\onedrive\\central_image_storage\\wardrobe\\";

    @Getter
    public enum WwDialogInfo implements WindowBuilder.DialogInfo {
        MAIN("/fxml/main.fxml", new Size(1000, 1700), CoreConstants.APP_NAME),
        PIECE_EDIT("/fxml/piece_dialog.fxml", new Size(700, 800), CoreConstants.APP_NAME),
        OUTFIT_EDIT("/fxml/outfit_dialog.fxml", new Size(1000, 1200), CoreConstants.APP_NAME);

        private final String fullFileName;
        private final Size size;
        private final String title;

        WwDialogInfo(String fullFileName, Size size, String title) {
            this.fullFileName = fullFileName;
            this.size = size;
            this.title = title;
        }
    }

    @Getter
    public enum WwNodeInfo implements WindowBuilder.NodeInfo {
        STATISTICS("/fxml/statistics.fxml"),
        PIECES("/fxml/pieces.fxml"),
        OUTFITS("/fxml/outfits.fxml"),
        OUTFIT_GRID("/fxml/outfit_grid.fxml");

        private final String fullFileName;

        WwNodeInfo(String fullFileName) {
            this.fullFileName = fullFileName;
        }
    }
}
