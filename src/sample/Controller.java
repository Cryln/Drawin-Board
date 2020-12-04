package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    public ChoiceBox choice02;
    public RadioButton autofilling;

    private boolean shifted;
    private Color currentColor;
    private Shape currentShape;
    private Point2D lastP; //拖动时来计算移动向量

    private Rectangle guideEdge = new Rectangle();
    private Line guideLine = new Line();



    @FXML
    public void printHello(){
        System.out.println("hello");
    }
    public void penChoise(ActionEvent mouseEvent) {
        if(choice01.getValue().equals("Shape")){
            choice02.setVisible(true);
            autofilling.setSelected(true);
            autofilling.setDisable(true);
        }
        else if(choice01.getValue().equals("Pen"))
        {
            autofilling.setDisable(false);
            choice02.setVisible(false);
        }
        else choice02.setVisible(false);
    }
    public void updateColor(){
        currentColor = new Color(color01.getValue().getRed(),color01.getValue().getGreen(),
                color01.getValue().getBlue(),slider2.getValue()/100);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        color01.setValue(Color.WHITE);
        updateColor();
        drawpane.addEventFilter(MouseEvent.MOUSE_PRESSED, e->{
            if(e.getButton()== MouseButton.PRIMARY){
                switch ((String)choice01.getValue()){
                    case "Pen":{
                        startFreeDraw(e.getX(),e.getY());
                        break;
                    }
                    case "Shape":{
                        startShapeDraw(e.getX(),e.getY());
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
                    case "Shape":{
                        if(!shifted)
                            shapeDraw(e.getX(),e.getY());
                        else shapeDraw((e.getX()+e.getY()+lastP.getX()-lastP.getY())/2,
                                (e.getX()+e.getY()-lastP.getX()+lastP.getY())/2);
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
                    case "Shape":{
                        endShapeDraw();
                        break;
                    }
                }
            }
            else if(e.getButton()== MouseButton.SECONDARY){

            }
            else if(e.getButton()== MouseButton.MIDDLE){

            }
        });
        mainpane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode()== KeyCode.SHIFT){
                    shifted = false;
                }
            }
        });
        mainpane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode()== KeyCode.SHIFT){
                    shifted = true;
                }
            }
        });
        choice01.setValue("Pen");
        choice02.setVisible(false);
    }


    //自由绘图
    private void startFreeDraw(double x,double y){
        Path currentPath = new Path();
        MoveTo moveTo = new MoveTo(x,y);
//        System.out.println("panexy:"+drawpane.getLayoutX()+"**"+drawpane.getLayoutY());
//        System.out.println("moveto:"+moveTo.getX()+"**"+moveTo.getY());
        currentPath.getElements().add(moveTo);
        currentShape = currentPath;
        initShape(x,y);
    }

    private void freeDrawing(double x,double y){
        LineTo lineTo = new LineTo(x,y);
        Path currentPath = (Path)currentShape;
        currentPath.getElements().add(lineTo);
    }
    private void endFreeDraw(){

    }


    private void startShapeDraw(double x, double y) {
        switch ((String) choice02.getValue()) {
            case "Oval":
            case "Circle":
                currentShape = new Ellipse();
                break;
            case "Rectangle":
                currentShape = new Rectangle();
                break;
            default:
                break;
        }
        initShape(x,y);

        lastP = new Point2D(x,y);
        guideEdge.setX(x);
        guideEdge.setY(y);
        guideEdge.setWidth(0);
        guideEdge.setHeight(0);
        guideEdge.setFill(new Color(0,0,0,0.1));

        guideLine.setStartX(x);
        guideLine.setStartY(y);
        guideLine.setEndX(x);
        guideLine.setEndY(y);

        drawpane.getChildren().addAll(guideEdge,guideLine);

    }


    private void shapeDraw(double x, double y){
        guideEdge.setWidth(x-lastP.getX());
        guideEdge.setHeight(y-lastP.getY());

        guideLine.setEndX(x);
        guideLine.setEndY(y);
        switch ((String) choice02.getValue()) {
            case "Oval":
            case "Circle": {
                Ellipse currentEllipse = (Ellipse) currentShape;
                currentEllipse.setCenterX((x+lastP.getX())/2);
                currentEllipse.setCenterY((y+lastP.getY())/2);
                currentEllipse.setRadiusX((x-lastP.getX())/2);
                currentEllipse.setRadiusY((y-lastP.getY())/2);

                guideLine.setStartX((x+lastP.getX())/2);
                guideLine.setStartY((y+lastP.getY())/2);

            }
            case "Rectangle": {
                Rectangle currentRect = (Rectangle) currentShape;
                currentRect.setX(lastP.getX());
                currentRect.setY(lastP.getY());
                currentRect.setWidth(x-lastP.getX());
                currentRect.setHeight(y-lastP.getY());
            }
            default:
                break;
        }

    }
    private void endShapeDraw(){
        drawpane.getChildren().removeAll(guideEdge,guideLine);
    }

    private void initShape(double x,double y) {
        //初始化笔刷：
        if(autofilling.isSelected())
            currentShape.setFill(currentColor);
        currentShape.setStroke(currentColor);
        currentShape.setStrokeWidth(slider1.getValue()/10);
        //添加到容器
        drawpane.getChildren().add(currentShape);
        //同时设置一个动作过滤器，和一个转换器来实现移动
        Translate translate = new Translate(0, 0);
        currentShape.getTransforms().add(translate);
        currentShape.addEventFilter(MouseEvent.MOUSE_DRAGGED, e2 -> {
            if (e2.getButton() == MouseButton.MIDDLE) {

//                System.out.println("s2l:"+currentP.getX()+"**"+currentP.getY());
//                System.out.println("e2getx:"+e2.getX()+"**"+e2.getY());
//                System.out.println("e2getscene:"+e2.getSceneX()+"**"+e2.getSceneY());
                //更新转换器坐标
                translate.setX(translate.getX() + (e2.getSceneX() - lastP.getX()));
                translate.setY(translate.getY() + (e2.getSceneY() - lastP.getY()));

//                Translate translate = new Translate(e2.getX()-lastP.getX(),e2.getY()-lastP.getY());
//                this.currentShape.getTransforms().add(translate);

                //记录坐标
                lastP = new Point2D(e2.getSceneX(), e2.getSceneY());
//                System.out.println(choice01.getValue());
            }
        });
        currentShape.addEventFilter(MouseEvent.MOUSE_PRESSED, e2 -> {
            if (e2.getButton() == MouseButton.PRIMARY) {
                if (choice01.getValue().equals("Clear")) {//equals 这种bugs尽然一时没看出来。。。
                    Shape clearObject = (Shape) e2.getSource();
                    drawpane.getChildren().remove(clearObject);
                }
            }
            if (e2.getButton() == MouseButton.MIDDLE) {
                //必须用getScene坐标，如果用Shape自己的坐标会有卡顿的情况
                lastP = new Point2D(e2.getSceneX(), e2.getSceneY());
            }
        });
    }
}
