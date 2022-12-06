package visualizer.algorithms.sorting;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import visualizer.SortingController;
import visualizer.algorithms.Algorithm;
import visualizer.algorithms.Speed;

public class QuickSort extends Algorithm implements Runnable{

    private int i;
    private int j;

    public QuickSort(Speed sleep, ObservableList<Rectangle> list) {
        super(sleep, list);
    }
    
    @Override
    public void run(){
        SortingController.isRunning = true;
        this.quickSort(0, this.list.size()-1);
        SortingController.isRunning = false;
    }

    private void quickSort(int start, int end){

        if(end<=start) return;
        int pivot = this.partition(start, end);
        this.sleep(25);
        quickSort(start, pivot-1);
        this.sleep(25);
        quickSort(pivot + 1, end);
        this.sleep(10);
    }

    private int partition(int start,int end){
        
        double pivot = this.list.get(end).getHeight();
        this.sleep(5);
        i = start - 1;
        //TODO finish coloring of the rectangles so it is colored right way
        for(j = start; j<=end;j++){
            if(this.list.get(j).getHeight() < pivot){
                i++;
                Platform.runLater(()->{
                    double temp = this.list.get(i).getHeight();
                    this.list.set(i, new Rectangle(this.width, this.list.get(j).getHeight()));
                    this.list.set(j, new Rectangle(this.width, temp));
                });
            }
            // zde se musí Algorithm thread uspat, aby se stihl provést render a variable swam které je prováděn v JavaFx thread
            this.sleep(25);
        }
        i++;
        Platform.runLater(()->{
            double temp = this.list.get(i).getHeight();
            this.list.set(i, new Rectangle(this.width, pivot, Color.GREEN));
            this.list.set(end, new Rectangle(this.width, temp));
        });
        return i;
    }
}
