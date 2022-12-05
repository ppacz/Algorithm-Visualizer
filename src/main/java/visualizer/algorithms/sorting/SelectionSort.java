package visualizer.algorithms.sorting;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import visualizer.SortingController;
import visualizer.algorithms.Algorithm;
import visualizer.algorithms.Speed;

public class SelectionSort extends Algorithm implements Runnable {

    private int min;
    private int i;

    public SelectionSort(Speed sleep, ObservableList<Rectangle> list) {
        super(sleep, list);
    }

    @Override
    public void run() {

        for(i = 0; i < this.list.size()-1;i++){
            min = i;
            for(int j = i + 1; j <this.list.size(); j++){
                if(SortingController.isRunning==false) return;
                colorElements(0, i, Color.GREEN);
                colorElements(i, j, Color.BLACK);
                colorElements(new int[] {j, min}, Color.YELLOW);
                if(list.get(j).getHeight()<list.get(min).getHeight()){
                    min = j;
                }
                try {
                    Thread.sleep(this.sleep/10*9);
                } catch (InterruptedException e) {
                }
            }
            Platform.runLater(()->{
                int temp = (int) list.get(min).getHeight();
                list.set(min, new Rectangle(list.get(0).getWidth(),list.get(i).getHeight()));
                list.set(i, new Rectangle(list.get(0).getWidth(),temp));
            });
            try {
                Thread.sleep(this.sleep/10);
            } catch (InterruptedException e) {
            }
            colorElements(i, list.size(), Color.BLACK);
        }
        colorElements(0, list.size(), Color.GREEN);
        SortingController.isRunning = false;
    }
    
}
