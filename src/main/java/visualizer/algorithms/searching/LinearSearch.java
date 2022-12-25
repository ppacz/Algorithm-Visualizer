package visualizer.algorithms.searching;

import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import visualizer.SearchingController;
import visualizer.algorithms.Algorithm;
import visualizer.algorithms.Speed;

public class LinearSearch extends Algorithm implements Runnable{
    private int value;

    public LinearSearch(Speed sleep, ObservableList<Rectangle> rectList, int value) {
        super(sleep, rectList);
        this.value = value;
    }

    @Override
    public void run(){
        for (Rectangle rectangle : this.rectList) {
            if(SearchingController.isRunning == false) return;
            if(rectangle.getHeight()==value){
                rectangle.setFill(Color.GREEN);
                SearchingController.isRunning = false;
                return;
            }else{
                rectangle.setFill(Color.RED);
            }
            try {
                Thread.sleep(this.sleep);
            } catch (InterruptedException e) {
            }
        }
        SearchingController.isRunning = false;
    }
}
