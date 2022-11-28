package visualizer.algorithms;

import javafx.collections.ObservableList;
import javafx.scene.shape.Rectangle;

public abstract class Algorithm{
    protected ObservableList<Rectangle> list;
    protected int sleep;
    
    public Algorithm(int sleep, ObservableList<Rectangle> list){
        this.sleep = sleep;
        this.list = list;
    }
}
