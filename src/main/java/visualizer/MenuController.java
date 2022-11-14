package visualizer;

import java.io.IOException;

import javafx.fxml.FXML;

public class MenuController {
    
    @FXML
    private void switchToAbout() throws IOException {
        App.setRoot("about");
    }

    @FXML
    private void switchToSorting() throws IOException {
        App.setRoot("sorting");
    }

    @FXML
    private void switchToSearching() throws IOException {
        App.setRoot("searching");
    }
}
