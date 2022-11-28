package visualizer.algorithms.sorting;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import visualizer.algorithms.Algorithm;
import visualizer.algorithms.SearchingOrder;

public class BubbleSort extends Algorithm implements Runnable{

    private SearchingOrder order;
    private int j;

    public BubbleSort(int sleep, ObservableList<Rectangle> list, SearchingOrder order) {
        super(sleep, list);
        this.order = order;
    }

    @Override
    public void run() {
        int arrLength = list.size();
        for(int i = 0; i<=arrLength-1; i++){
            for(j = 0; j<arrLength-i-1; j++ ){
                Rectangle rect1 = list.get(j);
                Rectangle rect2 = list.get(j+1);
                colorElements(new int[] {j, j+1}, Color.YELLOW);
                if(rect1.getHeight() > rect2.getHeight()){
                    
                    Platform.runLater(()->{
                        double temp = rect1.getHeight();
                        rect1.setHeight(rect2.getHeight());
                        rect2.setHeight(temp);
                        list.set(j, rect2);
                        list.set(j+1, rect1);
                    });
                    
                }
                try {
                    Thread.sleep(this.sleep);
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
