package visualizer.algorithms.searching;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.shape.Rectangle;
import visualizer.SearchingController;
import visualizer.algorithms.Algorithm;
import visualizer.algorithms.Speed;

public class LinearSearch extends Algorithm implements Runnable{
    private int value;
    private boolean found = false;

    public LinearSearch(Speed sleep, ObservableList<Rectangle> rectList, int value, boolean fromFile, int multi) {
        super(sleep, rectList, fromFile, multi);
        this.value = value;
    }

    @Override
    public void run(){
        for (Rectangle rectangle : this.rectList) {
            if(SearchingController.isRunning == false) return;
            if(rectangle.getHeight()==value){
                rectangle.setFill(this.foundColor);
                this.found = true;
                SearchingController.isRunning = false;
                showAlert();
                return;
            }else{
                rectangle.setFill(this.searchedColor);
            }
            try {
                Thread.sleep(this.sleep);
            } catch (InterruptedException e) {
            }
        }
        showAlert();
        SearchingController.isRunning = false;
    }

    private void showAlert(){
        this.algorithmDuration(System.currentTimeMillis());
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Algoritmus ukončen");
            this.value = this.value/this.multi; 
            String text = "Vyzualizace algoritmu byla ukončena za: " + this.duration + " sekund.\nČíslo " + this.value;
            if(this.found){
                text+= " bylo nalezeno.";
            }else{
                text+= " nebylo nalezeno.";
            }
            alert.setContentText(text);
            alert.show();
        });
    }
}
