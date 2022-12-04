package visualizer.algorithms.sorting;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import visualizer.algorithms.Algorithm;
import visualizer.algorithms.SortingOrder;
import visualizer.algorithms.Speed;

public class BubbleSort extends Algorithm implements Runnable{

    private SortingOrder order;
    private int j;

    public BubbleSort(Speed sleep, ObservableList<Rectangle> list, SortingOrder order) {
        super(sleep, list);
        this.order = order;
    }

    @Override
    public void run() {
        int arrLength = list.size();
        for(int i = 0; i<=arrLength-1; i++){
            for(j = 0; j<arrLength-i-1; j++ ){
                Double rect1 = list.get(j).getHeight();
                Double rect2 = list.get(j+1).getHeight();
                colorElements(new int[] {j, j+1}, Color.YELLOW);
                try {
                    Thread.sleep(this.sleep/2);
                } catch (InterruptedException e) {
                }
                if(rect1 > rect2){
                    Platform.runLater(()->{
                        list.set(j, new Rectangle(list.get(0).getWidth(), rect2, Color.YELLOW));
                        list.set(j+1, new Rectangle(list.get(0).getWidth(), rect1, Color.YELLOW));
                    });
                }
                try {
                    Thread.sleep(this.sleep/2);
                } catch (InterruptedException e) {
                }
                colorElements(0, j+1, Color.BLACK);
            }
            colorElements(new int[] {list.size()-i-1}, Color.GREEN);
        }
    }

    private void colorElements(int[] elements, Color color){
        for (int i : elements) {
            list.get(i).setFill(color);
        }
    }

    private void colorElements(int start, int end, Color color){
        for(int i = start; i < end; i++) {
            list.get(i).setFill(color);
        }
    }
}
