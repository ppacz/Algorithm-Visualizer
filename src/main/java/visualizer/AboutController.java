package visualizer;

import java.io.IOException;

import javafx.fxml.FXML;

public class AboutController{

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }
    
}
