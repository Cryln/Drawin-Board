package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Translate;

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
    private Point2D lastP;



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
        System.out.println("panexy:"+drawpane.getLayoutX()+"**"+drawpane.getLayoutY());
        System.out.println("moveto:"+moveTo.getX()+"**"+moveTo.getY());
        this.currentPath = currentPath;
        //初始化笔刷：
        this.currentPath.getElements().add(moveTo);
        this.currentPath.setStroke(color01.getValue());
        this.currentPath.setStrokeWidth(slider1.getValue());
        //添加到容器
        drawpane.getChildren().add(this.currentPath);
        //同时设置一个动作过滤器，来实现移动
        Translate translate = new Translate(0,0);
        this.currentPath.getTransforms().add(translate);
        this.currentPath.addEventFilter(MouseEvent.MOUSE_DRAGGED, e2->{
            if(e2.getButton()==MouseButton.MIDDLE)
            {

//                System.out.println("s2l:"+currentP.getX()+"**"+currentP.getY());
                System.out.println("e2getx:"+e2.getX()+"**"+e2.getY());
//                System.out.println("e2getscene:"+e2.getSceneX()+"**"+e2.getSceneY());
                translate.setX(translate.getX()+(e2.getSceneX()-lastP.getX()));
                translate.setY(translate.getY()+(e2.getSceneY()-lastP.getY()));

//                Translate translate = new Translate(e2.getX()-lastP.getX(),e2.getY()-lastP.getY());
//                this.currentPath.getTransforms().add(translate);

                lastP = new Point2D(e2.getSceneX(), e2.getSceneY());
            }
        });
        this.currentPath.addEventFilter(MouseEvent.MOUSE_PRESSED, e2->{
            if(e2.getButton()==MouseButton.MIDDLE)
            {
                lastP = new Point2D(e2.getSceneX(), e2.getSceneY());
            }
        });
    }
    public void freeDrawing(double x,double y){
        LineTo lineTo = new LineTo(x,y);
        currentPath.getElements().add(lineTo);
    }
    public void endFreeDraw(){

    }
}
