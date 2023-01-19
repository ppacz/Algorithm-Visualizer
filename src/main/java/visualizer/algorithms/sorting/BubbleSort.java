package visualizer.algorithms.sorting;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import visualizer.SortingController;
import visualizer.algorithms.Algorithm;
import visualizer.algorithms.Speed;

public class BubbleSort extends Algorithm implements Runnable{

    private int j;

    public BubbleSort(Speed sleep, ObservableList<Rectangle> rectList, boolean fromFile) {
        super(sleep, rectList, fromFile);
    }

    @Override
    public void run() {
        int arrLength = rectList.size();
        for(int i = 0; i<=arrLength-1; i++){
            for(j = 0; j<arrLength-i-1; j++ ){
                if(SortingController.isRunning == false) return;
                Double rect1 = rectList.get(j).getHeight();
                Double rect2 = rectList.get(j+1).getHeight();
                colorElements(new int[] {j, j+1}, Color.YELLOW);
                try {
                    Thread.sleep(this.sleep/2);
                } catch (InterruptedException e) {
                }
                if(rect1 > rect2){
                    Platform.runLater(()->{
                        rectList.set(j, new Rectangle(rectList.get(0).getWidth(), rect2, Color.YELLOW));
                        rectList.set(j+1, new Rectangle(rectList.get(0).getWidth(), rect1, Color.YELLOW));
                    });
                }
                try {
                    Thread.sleep(this.sleep/2);
                } catch (InterruptedException e) {
                }
                colorElements(new int[] {j,j+1}, Color.BLACK);
            }
        }
        SortingController.isRunning = false;
        this.algorithmDuration(System.currentTimeMillis());
        finishColoring();
    }

}
