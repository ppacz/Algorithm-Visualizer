package visualizer.algorithms;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Algorithm {
    protected ObservableList<Rectangle> rectList;
    protected int sleep;
    protected double width;
    protected double duration;
    private double startTime = System.currentTimeMillis();
    
    public Algorithm(Speed sleep, ObservableList<Rectangle> rectList){
        switch (sleep) {
            case Slow:
                this.sleep = 1000;
                break;
            case Normal:
                this.sleep = 250;
                break;
        
            case Fast:
                this.sleep = 50;
                break;
        }
        this.rectList = rectList;
        this.width = this.rectList.get(0).getWidth();
    }

    
    protected void colorElements(int[] elements, Color color){
        for (int i : elements) {
            rectList.get(i).setFill(color);
        }
    }

    protected void colorElements(int start, int end, Color color){
        for(int i = start; i < end; i++) {
            rectList.get(i).setFill(color);
        }
    }

    protected void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    protected void finishColoring(){
        for (Rectangle rectangle : rectList) {
            sleep(5);
            rectangle.setFill(Color.GREEN);
        }
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Algoritmus ukončen");
            alert.setContentText("Vyzualizace algoritmu byla ukončena za: " + duration + " sekund");
            alert.show();
        });
    }

    protected void algorithmDuration(double endTime){
        this.duration = (endTime-this.startTime)/1000;
    }

}
