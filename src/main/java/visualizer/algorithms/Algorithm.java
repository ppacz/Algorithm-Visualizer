package visualizer.algorithms;

import javafx.collections.ObservableList;
import javafx.scene.shape.Rectangle;

public abstract class Algorithm{
    protected ObservableList<Rectangle> list;
    protected int sleep;
    
    public Algorithm(Speed sleep, ObservableList<Rectangle> list){
        switch (sleep) {
            case Slow:
                this.sleep = 1000;
                break;
            case Normal:
                this.sleep = 250;
                break;
        
            case Fast:
                this.sleep = 25;
                break;
        }
        this.list = list;
    }
}
