package visualizer.algorithms.sorting;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.shape.Rectangle;
import visualizer.SortingController;
import visualizer.algorithms.Algorithm;
import visualizer.algorithms.Speed;

public class InsertionSort extends Algorithm implements Runnable {

    private int j;

    public InsertionSort(Speed sleep, ObservableList<Rectangle> list) {
        super(sleep, list);

    }

    @Override
    public void run() {
        SortingController.isRunning = true;
		
        for(int i = 1; i < this.list.size(); i++) {
            int temp = (int) this.list.get(i).getHeight();
            j = i - 1;
                
            while(j >= 0 && this.list.get(j).getHeight() > temp) {
                Platform.runLater(()->{
                    this.list.set(j + 1,new Rectangle(this.width,this.list.get(j).getHeight()));
                });
                this.sleep(20);
                j--;
            }
            
            Platform.runLater(()->{
                this.list.set(j + 1,new Rectangle(this.width,temp));
            });
            this.sleep(20);
        }
        SortingController.isRunning = false;
        finishColoring();
    }
    
}
