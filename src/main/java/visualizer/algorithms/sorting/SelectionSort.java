package visualizer.algorithms.sorting;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
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
                colorElements(i, this.list.size(), Color.BLACK);
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
            sleep(this.sleep/10);
            colorElements(i, this.list.size(), Color.BLACK);
        }
        SortingController.isRunning = false;
        finishColoring();
        double duration = this.algorithmDuration(System.currentTimeMillis());
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Algoritmus ukončen");
            alert.setContentText("Algoritmus byl ukončet za: " + duration + " sekund");
            alert.show();
        });
    }
    
}
