package visualizer;

import java.net.URL;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;

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
import visualizer.algorithms.Speed;
import visualizer.algorithms.searching.BinarySearch;
import visualizer.algorithms.searching.LinearSearch;

public class SearchingController implements Initializable {

    @FXML
    private Label numberOfValuesText;

    @FXML
    private HBox searchingPane;
    
    @FXML
    private Slider numberOfValuesSlider;

    @FXML
    private RadioButton linear, binary;

    @FXML
    private Slider speedSlider;

    @FXML
    private AnchorPane mainPane;

    private boolean needToBeSorted = false;
    private int numberOfValues;
    private int minHeight = 10;
    private boolean generated = false;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        numberOfValuesSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                numberOfValues = (int) numberOfValuesSlider.getValue();
                numberOfValuesText.setText("Počet hodnot: " + numberOfValues);
            }
        });
        /*
         * TODO make thread killer
         * TODO make quality of life changes
         */
        mainPane.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.ENTER) {
                System.out.println("You pressed enter");
            }
      });
    }

    @FXML
    protected void generate(){
        if(generated) clearStage();
        int amount = numberOfValues;
        int maxHeight = (int) searchingPane.getHeight()-10;
        int maxWidth = (int) (searchingPane.getWidth()/amount*.9);
        double padding = (searchingPane.getWidth()/amount*.05)/2;
        this.searchingPane.setSpacing(padding);
        Random random = new Random();
        
        if(binary.isSelected()){
            for(int i=1; i<amount+1; i++){
                Rectangle rect = new Rectangle(maxWidth, (int) (i*(maxHeight-50)/amount)+50);
                this.searchingPane.getChildren().add(rect);
            }
            needToBeSorted = true;
        }else{
            for(int i=0; i<amount; i++){
                int randomNum = random.nextInt((maxHeight - minHeight) + 1) + minHeight;
                Rectangle rect = new Rectangle(maxWidth, randomNum);
                this.searchingPane.getChildren().add(rect);
            }
            needToBeSorted = false;
        }
        Collections.shuffle(this.searchingPane.getChildren());
        generated = true;
    }
    
    private void clearStage(){
        ObservableList<Rectangle> list = (ObservableList) this.searchingPane.getChildren();
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
        Runnable search = this.getAlgorithm(algoSpeed, (ObservableList) this.searchingPane.getChildren(), 84);
        if(search!=null){
            Thread thread = new Thread(search);
            thread.setName("Algorithm thread");
            thread.start();
        }
    }


    private Runnable getAlgorithm(Speed sleep, ObservableList<Rectangle> list, int value){
        if(linear.isSelected()){
            return new LinearSearch(sleep, list, value);
        }else if(binary.isSelected() && needToBeSorted){
            return new BinarySearch(sleep, list, value);
        }
        Alert alert = new Alert(AlertType.WARNING);
        alert.setHeaderText("Nastala chyba");
        alert.setContentText("Nebyl vybrán žádný algoritmus");
        alert.show();
        return null;         
    }
}
