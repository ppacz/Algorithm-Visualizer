package visualizer.algorithms;

import javafx.collections.ObservableList;
import javafx.scene.shape.Rectangle;

public abstract class Algorithm{
    protected ObservableList<Rectangle> list;
    protected int value;
    protected int sleep;
    
    public Algorithm(int sleep, ObservableList<Rectangle> list, int value){
        this.sleep = sleep;
        this.list = list;
        this.value = value;
    }
}
