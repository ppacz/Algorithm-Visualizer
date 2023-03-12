package visualizer.algorithms.sorting;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.shape.Rectangle;
import visualizer.SortingController;
import visualizer.algorithms.Algorithm;
import visualizer.algorithms.Speed;

public class SelectionSort extends Algorithm implements Runnable {

    private int min;
    private int i;

    public SelectionSort(Speed sleep, ObservableList<Rectangle> rectList, boolean fromFile, int multi) {
        super(sleep, rectList, fromFile, multi);
    }

    @Override
    public void run() {

        for(i = 0; i < this.rectList.size()-1;i++){
            min = i;
            for(int j = i + 1; j <this.rectList.size(); j++){
                if(SortingController.isRunning==false) return;
                colorElements(i, this.rectList.size(), this.defaultColor);
                colorElements(new int[] {j, min}, this.comparingColor);
                if(rectList.get(j).getHeight()<rectList.get(min).getHeight()){
                    min = j;
                }
                try {
                    Thread.sleep(this.sleep/10*9);
                } catch (InterruptedException e) {
                }
            }
            Platform.runLater(()->{
                int temp = (int) rectList.get(min).getHeight();
                rectList.set(min, new Rectangle(rectList.get(0).getWidth(),rectList.get(i).getHeight()));
                rectList.set(i, new Rectangle(rectList.get(0).getWidth(),temp));
            });
            sleep(this.sleep/10);
            this.counter.increseBy((this.rectList.size()-i+1)*2);
            colorElements(i, this.rectList.size(), this.defaultColor);
            this.updateTexts();
        }
        SortingController.isRunning = false;
        this.algorithmDuration(System.currentTimeMillis());
        this.finishSortAlgorithm();
    }
    
}
