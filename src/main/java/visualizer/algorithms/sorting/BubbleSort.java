package visualizer.algorithms.sorting;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.shape.Rectangle;
import visualizer.SortingController;
import visualizer.algorithms.Algorithm;
import visualizer.algorithms.Speed;

public class BubbleSort extends Algorithm implements Runnable{

    private int j;

    public BubbleSort(Speed sleep, ObservableList<Rectangle> rectList, boolean fromFile,int multi) {
        super(sleep, rectList, fromFile, multi);
    }

    @Override
    public void run() {
        int arrLength = rectList.size();
        for(int i = 0; i<=arrLength-1; i++){
            boolean moved = false;
            for(j = 0; j<arrLength-i-1; j++ ){
                if(SortingController.isRunning == false) return;
                Double rect1 = rectList.get(j).getHeight();
                Double rect2 = rectList.get(j+1).getHeight();
                colorElements(new int[] {j, j+1}, this.comparingColor);
                try {
                    Thread.sleep(this.sleep/2);
                } catch (InterruptedException e) {
                }
                if(rect1 > rect2){
                    moved = true;
                    Platform.runLater(()->{
                        rectList.set(j, new Rectangle(rectList.get(0).getWidth(), rect2, this.comparingColor));
                        rectList.set(j+1, new Rectangle(rectList.get(0).getWidth(), rect1, this.comparingColor));
                    });
                }
                try {
                    Thread.sleep(this.sleep/2);
                } catch (InterruptedException e) {
                }
                colorElements(new int[] {j,j+1}, this.defaultColor);
            }
            if(!moved){
                this.counter.increseBy((arrLength-i-1)*2);
                this.updateTexts();
                SortingController.isRunning = false;
                this.algorithmDuration(System.currentTimeMillis());
                this.finishSortAlgorithm();
            }
            this.counter.increseBy((arrLength-i-1)*2);
            this.updateTexts();
        }
        SortingController.isRunning = false;
        this.algorithmDuration(System.currentTimeMillis());
        this.finishSortAlgorithm();
    }

}
