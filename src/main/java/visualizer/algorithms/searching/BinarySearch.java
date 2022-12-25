package visualizer.algorithms.searching;

import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import visualizer.SearchingController;
import visualizer.algorithms.Algorithm;
import visualizer.algorithms.Speed;

public class BinarySearch extends Algorithm implements Runnable {
    
    private int value;

    public BinarySearch(Speed sleep, ObservableList<Rectangle> rectList, int value) {
        super(sleep, rectList);
        this.value = value;
    }

    @Override
    public void run() {
        int high = this.rectList.size()-1;
        int low = 0;
        while(low<high && SearchingController.isRunning){
            int middle = low + (high-low)/2;
            colorElements(new int[] {middle}, Color.ORANGE);
            try {
                Thread.sleep(this.sleep);
            } catch (InterruptedException e) {
            }
            int testingValue = (int) rectList.get(middle).getHeight();
            System.out.println(testingValue);
            if(testingValue < this.value){
                this.colorElements(low, middle, Color.RED);
                low = middle + 1;
            }
            else if(testingValue > this.value){
                this.colorElements(middle, high, Color.RED);
                high = middle-1;
            }
            else{
                colorElements(new int[] {middle}, Color.GREEN);
                SearchingController.isRunning = false;
                return;
            }
        }
        this.colorElements(low, high, Color.RED);
    }

}
