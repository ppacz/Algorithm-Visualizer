package visualizer.algorithms.sorting;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.shape.Rectangle;
import visualizer.SortingController;
import visualizer.algorithms.Algorithm;
import visualizer.algorithms.Speed;

public class QuickSort extends Algorithm implements Runnable{

    //musí být deklarovány jako globální, aby je bylo možné použít v anonymní fůnkci
    private int i;
    private int j;

    public QuickSort(Speed sleep, ObservableList<Rectangle> rectList, boolean fromFile, int multi) {
        super(sleep, rectList, fromFile, multi);
    }
    
    @Override
    public void run(){
        SortingController.isRunning = true;
        this.quickSort(0, this.rectList.size()-1);
        if(SortingController.isRunning==false) return;
        SortingController.isRunning = false;
        this.algorithmDuration(System.currentTimeMillis());
        this.finishSortAlgorithm();
    }

    private void quickSort(int start, int end){
        if(SortingController.isRunning==false) return;
        if(end<=start) return;
        int pivot = this.partition(start, end);
        this.sleep(this.sleep/10);
        quickSort(start, pivot-1);
        this.sleep(this.sleep/10);
        quickSort(pivot + 1, end);
        this.sleep(this.sleep/10);
    }

    private int partition(int start,int end){
        
        double pivot = this.rectList.get(end).getHeight();
        rectList.get(end).setFill(this.comparingColor);
        this.sleep(10);
        i = start - 1;
        for(j = start; j<=end;j++){
            this.counter.increseBy(2);
            if(SortingController.isRunning==false) return 0;
            rectList.get(j).setFill(this.comparingColor);
            if(this.rectList.get(j).getHeight() < pivot){
                i++;
                Platform.runLater(()->{
                    double temp = this.rectList.get(i).getHeight();
                    this.rectList.set(i, new Rectangle(this.width, this.rectList.get(j).getHeight()));
                    this.rectList.set(j, new Rectangle(this.width, temp, this.comparingColor));
                });
            }
            // zde se musí Algorithm thread uspat, aby se stihl provést render a variable swap který je prováděn v JavaFx thread
            this.sleep(this.sleep);
            colorElements(new int[] {j}, this.defaultColor);
        }
        i++;
        colorElements(0, this.rectList.size(), this.defaultColor);

        Platform.runLater(()->{
            double temp = this.rectList.get(i).getHeight();
            this.rectList.set(i, new Rectangle(this.width, pivot));
            this.rectList.set(end, new Rectangle(this.width, temp));
        });
        this.updateTexts();
        return i;
    }

}
