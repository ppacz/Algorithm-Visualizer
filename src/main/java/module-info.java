module visualizer {
    requires transitive javafx.controls;
    requires javafx.fxml;

    opens visualizer to javafx.fxml;
    exports visualizer;
}
