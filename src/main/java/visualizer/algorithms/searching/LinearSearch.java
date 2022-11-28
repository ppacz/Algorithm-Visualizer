package visualizer.algorithms.searching;

import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import visualizer.algorithms.Algorithm;

public class LinearSearch extends Algorithm implements Runnable{
    private int value;

    public LinearSearch(int sleep, ObservableList<Rectangle> list, int value) {
        super(sleep, list);
        this.value = value;
    }

    @Override
    public void run(){
        for (Rectangle rectangle : this.list) {
            if(rectangle.getHeight()==value){
                rectangle.setFill(Color.GREEN);
                return;
            }else{
                rectangle.setFill(Color.RED);
            }
            try {
                Thread.sleep(this.sleep);
            } catch (InterruptedException e) {
            }
        }
    }
}
