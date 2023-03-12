package visualizer.algorithms.searching;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import visualizer.SearchingController;
import visualizer.algorithms.Algorithm;
import visualizer.algorithms.Speed;

public class BinarySearch extends Algorithm implements Runnable {
    
    private int value;
    private boolean found = false;

    public BinarySearch(Speed sleep, ObservableList<Rectangle> rectList, int value, boolean fromFile, int multi) {
        super(sleep, rectList, fromFile, multi);
        this.value = value;
    }

    @Override
    public void run() {
        int high = this.rectList.size()-1;
        int low = 0;
        while(low<=high && SearchingController.isRunning){
            int middle = low + (high-low)/2;
            this.colorElements(new int[] {middle}, this.comparingColor);
            try {
                Thread.sleep(this.sleep);
            } catch (InterruptedException e) {
            }
            int testingValue = (int) rectList.get(middle).getHeight();
            if(testingValue < this.value){
                this.colorElements(low, middle+1, this.searchedColor);
                low = middle + 1;
            }
            else if(testingValue > this.value){
                this.colorElements(middle, high+1, this.searchedColor);
                high = middle-1;
            }
            else{
                this.colorElements(low, high, this.searchedColor);
                this.colorElements(new int[] {middle}, this.foundColor);
                SearchingController.isRunning = false;
                this.found = true;
                this.showAlert();
                return;
            }
        }
        SearchingController.isRunning = false;
        this.colorElements(low, high, this.searchedColor);
        this.showAlert();
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
