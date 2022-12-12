package visualizer.algorithms;

import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Algorithm{
    protected ObservableList<Rectangle> list;
    protected int sleep;
    protected double width;
    
    public Algorithm(Speed sleep, ObservableList<Rectangle> list){
        switch (sleep) {
            case Slow:
                this.sleep = 1000;
                break;
            case Normal:
                this.sleep = 250;
                break;
        
            case Fast:
                this.sleep = 50;
                break;
        }
        this.list = list;
        this.width = this.list.get(0).getWidth();
    }

    
    protected void colorElements(int[] elements, Color color){
        for (int i : elements) {
            list.get(i).setFill(color);
        }
    }

    protected void colorElements(int start, int end, Color color){
        for(int i = start; i < end; i++) {
            list.get(i).setFill(color);
        }
    }

    protected void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    protected void finishColoring(){
        for (Rectangle rectangle : list) {
            sleep(5);
            rectangle.setFill(Color.GREEN);
        }
    }
}
