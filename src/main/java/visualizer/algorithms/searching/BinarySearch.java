package visualizer.algorithms.searching;

import javafx.collections.ObservableList;
import javafx.scene.shape.Rectangle;
import visualizer.algorithms.Algorithm;

public class BinarySearch extends Algorithm implements Runnable {
    
    public BinarySearch(int sleep, ObservableList<Rectangle> list, int value) {
        super(sleep, list, value);
    }

    @Override
    public void run() {
        System.out.println("not done yets");
        
    }
    
}
