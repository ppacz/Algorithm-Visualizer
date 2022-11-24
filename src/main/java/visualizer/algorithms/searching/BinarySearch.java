package visualizer.algorithms.searching;

import java.net.HttpURLConnection;

import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import visualizer.algorithms.Algorithm;

public class BinarySearch extends Algorithm implements Runnable {
    
    public BinarySearch(int sleep, ObservableList<Rectangle> list, int value) {
        super(sleep, list, value);
    }

    @Override
    public void run() {
        int high = this.list.size()-1;
        int low = 0;
        while(low<high){
            int middle = low + (high-low)/2;
            list.get(middle).setFill(Color.ORANGE);
            try {
                Thread.sleep(this.sleep);
            } catch (InterruptedException e) {
            }
            int testingValue = (int) list.get(middle).getHeight();
            System.out.println(testingValue);
            if(testingValue < this.value){
                this.badValues(low, middle);
                low = middle + 1;
            }
            else if(testingValue > this.value){
                this.badValues(middle, high);
                high = middle-1;
            }
            else{
                list.get(middle).setFill(Color.GREEN);
                return;
            }
        }
        this.badValues(low, high);
    }

    public void badValues(int start, int end){
        for(int i = start; i <= end; i++){
            this.list.get(i).setFill(Color.RED);
        }
    }
}
