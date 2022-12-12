package visualizer.algorithms.sorting;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
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
		//TODO make compare coloring
        for(int i = 1; i < this.list.size(); i++) {
            int temp = (int) this.list.get(i).getHeight();
            j = i - 1;
                
            while(j >= 0 && this.list.get(j).getHeight() > temp) {
                colorElements(new int[] {j,i}, Color.YELLOW);
                this.sleep(this.sleep);
                colorElements(new int[] {j}, Color.YELLOW);
                Platform.runLater(()->{
                    this.list.set(j + 1,new Rectangle(this.width,this.list.get(j).getHeight(), Color.YELLOW));
                });
                this.sleep(this.sleep);
                j--;
                colorElements(0, this.list.size(), Color.BLACK);
            }
            
            Platform.runLater(()->{
                this.list.set(j + 1,new Rectangle(this.width,temp));
            });
            this.sleep(this.sleep);
        }
        SortingController.isRunning = false;
        finishColoring();
    }
    
}
