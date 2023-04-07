package visualizer.algorithms.sorting;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.shape.Rectangle;
import visualizer.SortingController;
import visualizer.algorithms.Algorithm;
import visualizer.algorithms.Speed;

public class InsertionSort extends Algorithm implements Runnable {

    private int j;

    public InsertionSort(Speed sleep, ObservableList<Rectangle> rectList, boolean fromFile, int multi) {
        super(sleep, rectList, fromFile, multi);

    }

    @Override
    public void run() {
        SortingController.isRunning = true;
        for(int i = 1; i < this.rectList.size(); i++) {
            int temp = (int) this.rectList.get(i).getHeight();
            j = i - 1;
                
            while(j >= 0 && this.rectList.get(j).getHeight() > temp) {
                if(SortingController.isRunning==false) return;
                this.counter.increseBy(2);;
                colorElements(new int[] {j,i}, this.comparingColor);
                this.sleep(this.sleep);
                colorElements(new int[] {j}, this.comparingColor);
                Platform.runLater(()->{
                    this.rectList.set(j + 1,new Rectangle(this.width,this.rectList.get(j).getHeight(), this.comparingColor));
                });
                this.sleep(this.sleep);
                j--;
                colorElements(0, this.rectList.size(), this.defaultColor);
            }
            this.updateTexts();
            Platform.runLater(()->{
                this.rectList.set(j + 1,new Rectangle(this.width,temp));
            });
            this.sleep(this.sleep);
        }
        SortingController.isRunning = false;
        this.algorithmDuration(System.currentTimeMillis());
        this.finishSortAlgorithm();
    }
    
}
