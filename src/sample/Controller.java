package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public AnchorPane mainpane;
    public AnchorPane drawpane;
    public ToolBar toprightbar;
    public ChoiceBox choice01;
    public ColorPicker color01;
    public Slider slider1;
    public Slider slider2;
    public Button topbtn3;
    public Button topbtn4;



    private Path currentPath;



    @FXML
    public void printHello(){
        System.out.println("hello");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        color01.setValue(Color.BLUE);
        drawpane.addEventFilter(MouseEvent.MOUSE_PRESSED, e->{
            if(e.getButton()== MouseButton.PRIMARY){
                switch ((String)choice01.getValue()){
                    case "Pen":{
                        startFreeDraw(new Path(),e.getX(),e.getY());
                        break;
                    }
                }
            }
            else if(e.getButton()== MouseButton.SECONDARY){

            }
            else if(e.getButton()== MouseButton.MIDDLE){

            }
        });
        drawpane.addEventFilter(MouseEvent.MOUSE_DRAGGED, e->{
            if(e.getButton()== MouseButton.PRIMARY){
                switch ((String)choice01.getValue()){
                    case "Pen":{
                        freeDrawing(e.getX(),e.getY());
                        break;
                    }
                }
            }
            else if(e.getButton()== MouseButton.SECONDARY){

            }
            else if(e.getButton()== MouseButton.MIDDLE){

            }
        });
        drawpane.addEventFilter(MouseEvent.MOUSE_RELEASED, e->{
            if(e.getButton()== MouseButton.PRIMARY){
                switch ((String)choice01.getValue()){
                    case "Pen":{
                        endFreeDraw();
                        break;
                    }
                }
            }
            else if(e.getButton()== MouseButton.SECONDARY){

            }
            else if(e.getButton()== MouseButton.MIDDLE){

            }
        });
    }

    public void startFreeDraw(Path currentPath,double x,double y){
        MoveTo moveTo = new MoveTo(x,y);
        this.currentPath = currentPath;
        //初始化笔刷：
        this.currentPath.getElements().add(moveTo);
        this.currentPath.setStroke(color01.getValue());
        this.currentPath.setStrokeWidth(slider1.getValue());
        this.currentPath.set;
        drawpane.getChildren().add(this.currentPath);
    }
    public void freeDrawing(double x,double y){
        LineTo lineTo = new LineTo(x,y);
        currentPath.getElements().add(lineTo);
    }
    public void endFreeDraw(){

    }
}
