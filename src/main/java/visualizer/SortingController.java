package visualizer;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import visualizer.algorithms.SortingOrder;
import visualizer.algorithms.Speed;
import visualizer.algorithms.sorting.BubbleSort;

public class SortingController implements Initializable {
    @FXML
    private Label numberOfValuesText;

    @FXML
    private HBox sortingPane;

    @FXML
    private Slider speedSlider;
    
    @FXML
    private Slider numberOfValuesSlider;

    @FXML
    private AnchorPane mainPane;
    
    private int numberOfValues;
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

    /*
     * Sorting will need to be done by untilizing tasks, workers and services
     */

    @FXML
    protected void generate(){
        if(generated) clearStage();
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
        generated = true;
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
        Runnable search = this.getAlgorithm(algoSpeed, (ObservableList) this.sortingPane.getChildren(), SortingOrder.ASC);
        if(search!=null){
            Thread thread = new Thread(search);
            thread.setName("Algorithm thread");
            thread.start();
        }
    }


    private Runnable getAlgorithm(Speed sleep, ObservableList<Rectangle> list, SortingOrder order){
        return new BubbleSort(sleep, list, order);         
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
}
