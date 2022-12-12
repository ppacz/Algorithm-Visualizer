package visualizer.algorithms.sorting;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import visualizer.SortingController;
import visualizer.algorithms.Algorithm;
import visualizer.algorithms.Speed;

public class QuickSort extends Algorithm implements Runnable{

    //musí být deklarovány jako globální, aby je bylo možné použít v anonymní fůnkci
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
        finishColoring();
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Algoritmus ukončen");
            alert.setContentText("Algoritmus byl ukončet za: " + " sekund");
            alert.show();
        });
    }

    private void quickSort(int start, int end){

        if(end<=start) return;
        int pivot = this.partition(start, end);
        this.sleep(this.sleep/10);
        quickSort(start, pivot-1);
        this.sleep(this.sleep/10);
        quickSort(pivot + 1, end);
        this.sleep(this.sleep/10);
    }

    private int partition(int start,int end){
        
        double pivot = this.list.get(end).getHeight();
        list.get(end).setFill(Color.YELLOW);
        this.sleep(10);
        i = start - 1;
        //TODO finish coloring of the rectangles so it is colored right way
        for(j = start; j<=end;j++){
            list.get(j).setFill(Color.YELLOW);
            if(this.list.get(j).getHeight() < pivot){
                i++;
                Platform.runLater(()->{
                    double temp = this.list.get(i).getHeight();
                    this.list.set(i, new Rectangle(this.width, this.list.get(j).getHeight()));
                    this.list.set(j, new Rectangle(this.width, temp, Color.YELLOW));
                });
            }
            // zde se musí Algorithm thread uspat, aby se stihl provést render a variable swap který je prováděn v JavaFx thread
            this.sleep(this.sleep);
            colorElements(new int[] {j}, Color.BLACK);
        }
        i++;
        colorElements(0, this.list.size(), Color.BLACK);

        Platform.runLater(()->{
            double temp = this.list.get(i).getHeight();
            this.list.set(i, new Rectangle(this.width, pivot));
            this.list.set(end, new Rectangle(this.width, temp));
        });
        return i;
    }
}
