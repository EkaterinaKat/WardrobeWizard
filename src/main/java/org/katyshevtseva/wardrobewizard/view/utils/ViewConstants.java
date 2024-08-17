package org.katyshevtseva.wardrobewizard.view.utils;

import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.WindowBuilder;
import lombok.Getter;
import org.katyshevtseva.wardrobewizard.core.CoreConstants;

public class ViewConstants {

    @Getter
    public enum WwDialogInfo implements WindowBuilder.DialogInfo {
        MAIN("/fxml/main.fxml", new Size(1000, 1700), CoreConstants.APP_NAME);

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
        ITEMS("/fxml/items.fxml");

        private final String fullFileName;

        WwNodeInfo(String fullFileName) {
            this.fullFileName = fullFileName;
        }
    }
}
