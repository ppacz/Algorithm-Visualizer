package visualizer.algorithms.searching;

import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import visualizer.algorithms.Algorithm;

public class LinearSearch extends Algorithm{
    private ObservableList<Rectangle> list;
    private float value;


    public LinearSearch(int sleep, ObservableList<Rectangle> list, float value) {
        super(sleep);
        this.list = list;
        this.value = value;
    }

    @Override
    public void run(){
        for (Rectangle rectangle : list) {
            if(rectangle.getHeight()==value){
                rectangle.setFill(Color.BEIGE);
                return;
            }else{
                rectangle.setFill(Color.RED);
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }
        }
    }
    
}
