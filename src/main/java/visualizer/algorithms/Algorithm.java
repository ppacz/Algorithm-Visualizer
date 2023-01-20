package visualizer.algorithms;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Algorithm {
    protected ObservableList<Rectangle> rectList;
    protected int sleep;
    protected double width;
    protected double duration;
    private double startTime = System.currentTimeMillis();
    protected boolean fromFile;
    protected int multi;

    public Algorithm(Speed sleep, ObservableList<Rectangle> rectList, boolean fromFile, int multi){
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
        this.rectList = rectList;
        this.width = this.rectList.get(0).getWidth();
        this.fromFile = true;
        this.multi = multi;
    }

    
    protected void colorElements(int[] elements, Color color){
        for (int i : elements) {
            rectList.get(i).setFill(color);
        }
    }

    protected void colorElements(int start, int end, Color color){
        for(int i = start; i < end; i++) {
            rectList.get(i).setFill(color);
        }
    }

    protected void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    protected void finishSortAlgorithm(){
        this.finishColoring();
        String text = "Vyzualizace algoritmu byla ukončena za: " + duration + " sekund";
        String title = "Výsledky algoritmu";
        if(this.fromFile){
            File file = new File("results.txt");
            String output = "";
            for (Rectangle rectangle : this.rectList) {
                output += (int) rectangle.getHeight()/this.multi + ", ";
            }
            output = output.substring(0, output.length()-2);
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(output);
                title += " a informace o uložení";
                text += "\nVýsledky byly uloženy do projektu pod názvem results.txt";
                writer.close();
            } catch (IOException e) {
                this.showMessage("Soubor se nepodařilo uložit", "Chyba!");
                e.printStackTrace();
                return;
            }
        }
        this.showMessage(text, title);
    }


    private void showMessage(String texString, String title){
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setContentText(texString);
            alert.show();
        });
    }

    private void finishColoring(){
        for (Rectangle rectangle : rectList) {
            sleep(5);
            rectangle.setFill(Color.GREEN);
        }
    }

    protected void algorithmDuration(double endTime){
        this.duration = (endTime-this.startTime)/1000;
    }

}
