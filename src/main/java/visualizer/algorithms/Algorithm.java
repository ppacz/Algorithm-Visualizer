package visualizer.algorithms;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utils.Counter;

public abstract class Algorithm {
    protected ObservableList<Rectangle> rectList;
    protected int sleep;
    protected double width;
    protected double duration;
    private double startTime = System.currentTimeMillis();
    protected boolean fromFile;
    protected int multi;
    protected Counter counter;
    protected Label reads, time;
    protected Color defaultColor, searchedColor, foundColor, comparingColor, sortedColor;

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }


    public void setSearchedColor(Color searchedColor) {
        this.searchedColor = searchedColor;
    }


    public void setFoundColor(Color foundColor) {
        this.foundColor = foundColor;
    }


    public void setComparingColor(Color comparingColor) {
        this.comparingColor = comparingColor;
    }


    public void setSortedColor(Color sortedColor) {
        this.sortedColor = sortedColor;
    }


    public Algorithm(Speed sleep, ObservableList<Rectangle> rectList, boolean fromFile, int multi) {

        switch (sleep) {
            case Slow:
                this.sleep = 500;
                break;
            case Normal:
                this.sleep = 250;
                break;
        
            case Fast:
                this.sleep = 50;
                break;
        }
        this.counter = new Counter();
        this.rectList = rectList;
        this.width = this.rectList.get(0).getWidth();
        this.fromFile = fromFile;
        this.multi = multi;
    }

    
    protected void colorElements(int[] elements, Color color){
        for (int i : elements) {
            rectList.get(i).setFill(color);
        }
    }

    public void addLabels(Label time, Label reads){
        if (time != null) this.time = time;
        else this.time = null;
        if (reads!= null) this.reads = reads;
        else this.reads = null;
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
            rectangle.setFill(this.sortedColor);
        }
    }

    protected void algorithmDuration(double endTime){
        this.duration = (endTime-this.startTime)/1000;
    }

    protected void updateTexts(){
        Platform.runLater(()->{
            this.reads.setText(String.valueOf(this.counter.getCount()));
            this.time.setText(String.valueOf((System.currentTimeMillis()-this.startTime)/1000) +" s");
        });
    }

}
