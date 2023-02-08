package visualizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import visualizer.algorithms.Algorithm;
import visualizer.algorithms.Speed;
import visualizer.algorithms.sorting.BubbleSort;
import visualizer.algorithms.sorting.InsertionSort;
import visualizer.algorithms.sorting.MergeSort;
import visualizer.algorithms.sorting.QuickSort;
import visualizer.algorithms.sorting.SelectionSort;

public class SortingController implements Initializable {
    @FXML
    private Label numberOfValuesText, algorithmTime, numberOfReads;

    @FXML
    private HBox sortingPane;

    @FXML
    private Slider speedSlider;
    
    @FXML
    private Slider numberOfValuesSlider;

    @FXML
    private AnchorPane mainPane;
    
    @FXML
    private RadioButton bubble, insertion, selection, merge, quick;
    
    private int numberOfValues;
    private boolean generated = false;
    public static boolean isRunning;
    private boolean fromFile = false;
    private int multi;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        numberOfValuesSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                numberOfValues = (int) numberOfValuesSlider.getValue();
                numberOfValuesText.setText("Počet hodnot: " + numberOfValues);
            }
            
        });

        mainPane.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.R) {
                if(SortingController.isRunning)SortingController.isRunning = false;
                else System.out.println("No algorithm running");
            }else if(key.getCode() == KeyCode.ESCAPE){
                try {
                    this.isRunning = false;
                    App.setRoot("menu");
                } catch (IOException e) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Chyba");
                    alert.setContentText("Vypněte a znovu zapněte aplikaci, došlo k interní chybě");
                    alert.show();
                }
            }
      });
        
    }

    /*
     * Sorting will need to be done by untilizing tasks, workers and services
     */

    @FXML
    protected void generate(){
        if(generated) this.clearStage();
        int amount = numberOfValues;
        int maxHeight = (int) sortingPane.getHeight()-10;
        int maxWidth = (int) (sortingPane.getWidth()/amount*.9);
        double padding = (sortingPane.getWidth()/amount*.05)/2;
        this.sortingPane.setSpacing(padding);
        for(int i=1; i<amount+1; i++){
            Rectangle rect = new Rectangle(maxWidth, (int) (i*(maxHeight-50)/amount)+50);
            this.sortingPane.getChildren().add(rect);
        }
        this.shuffle();
        this.multi = 1;
        generated = true;
    }

    private void generateFromFile(int[] numbers){
        System.out.println("is called");
        if(generated) this.clearStage();
        this.fromFile = true;
        this.multi = (int) (sortingPane.getHeight()-10)/100;
        int maxWidth = (int) (sortingPane.getWidth()/(numbers.length)*.9);
        double padding = (sortingPane.getWidth()/numbers.length*.05)/2;
        this.sortingPane.setSpacing(padding);
        for(int i=1; i<numbers.length+1; i++){
            int height = (int) numbers[i-1]*this.multi;
            Rectangle rect = new Rectangle(maxWidth, height);
            this.sortingPane.getChildren().add(rect);
            System.out.println("is called");
        }
        numberOfValuesSlider.setValue(numbers.length);
        numberOfValuesText.setText("Počet hodnot: " + numbers.length);
    }
    
    private void clearStage(){
        ObservableList<Rectangle> list = (ObservableList)this.sortingPane.getChildren();
        list.clear();
    }

    // TODO findout how to send back message when done
    @FXML
    private void search(){
        Speed algoSpeed;
        switch ((int) speedSlider.getValue()) {
            case 1:
                algoSpeed = Speed.Slow;
                break;
            case 2:
                algoSpeed = Speed.Normal;
                break;
            case 3:
                algoSpeed = Speed.Fast;
                break;
            default:
                algoSpeed = Speed.Slow;
            }
        Runnable search = this.getAlgorithm(algoSpeed, (ObservableList) this.sortingPane.getChildren());
        if(search!=null){
            
            SortingController.isRunning = true;
            Thread thread = new Thread(search);
            thread.setName("Algorithm thread");
            thread.start();
        }
    }


    private Runnable getAlgorithm(Speed sleep, ObservableList<Rectangle> list){
        Algorithm algo = null;
        if(bubble.isSelected()){
            algo = new BubbleSort(sleep, list, this.fromFile, this.multi);
        }else if(selection.isSelected()){
            algo = new SelectionSort(sleep, list, this.fromFile, this.multi);
        }else if(quick.isSelected()){
            algo = new QuickSort(sleep, list, this.fromFile, this.multi);
        }else if(insertion.isSelected()){
            algo = new InsertionSort(sleep, list, this.fromFile, this.multi);
        }else if(merge.isSelected()){
            algo = new MergeSort(sleep, list, this.fromFile, this.multi);
        }
        if(algo!=null){
            algo.addLabels(this.algorithmTime, this.numberOfReads);
            return (Runnable) algo;
        }
        Alert alert = new Alert(AlertType.WARNING);
        alert.setHeaderText("Nastala chyba");
        alert.setContentText("Nebyl vybrán žádný algoritmus");
        alert.show();
        return null;         
    }

    private void shuffle(){
        Random random = new Random();
        ObservableList<Rectangle> list = (ObservableList)this.sortingPane.getChildren();
        for(int i = 0; i < list.size() - 1; i++){
            int randomIndexToSwap = random.nextInt(list.size());
			Double rect1 = list.get(randomIndexToSwap).getHeight();
            Double rect2 = list.get(i).getHeight();
			list.set(randomIndexToSwap, new Rectangle(list.get(0).getWidth(), rect2));
			list.set(i, new Rectangle(list.get(0).getWidth(), rect1));
        }
    }

    @FXML
    private void getFile(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Vyberte soubor s daty:");
        Stage stage = (Stage) mainPane.getScene().getWindow();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("textový soubor", "*.txt"));
        File file = chooser.showOpenDialog(stage);
        try {
            Scanner scanner = new Scanner(file);
            String text = scanner.nextLine();
            String[] numberText = text.split(",");
            int[] numbers = new int[numberText.length];
            for(int i = 0; i<numberText.length;i++){
                numbers[i] = Integer.parseInt(numberText[i]);
            }
            this.generateFromFile(numbers);
            scanner.close();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Chyba");
            alert.setContentText("Při načítání souboru nastala chyba");
            e.printStackTrace();
        }

    }

}
