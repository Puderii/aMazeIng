/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backup;



import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;

import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;

import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;


/**
 *
 * @author Petri
 */
public class AMazeIng extends Application {
  int xMaara = 600;
    int yMaara = 600;
    int siirtymat = 50;
    int maaliX = 0;
    int maaliY = 0;
    int paikkaMaali;
    long aikaYhteensa;
    long aikaAlku;
    long aikaLoppu;
    boolean maalissa = false;
    
    
    int paikkaTaulukossa;
    int paikkaLiikkeissa;
    boolean ulkona;
    int numero = 1;
    int onnistuneetSiirtymiset = 0;
    int edellinenSuunta = 0;
    static ArrayList liikkeet = new ArrayList();
    static ArrayList taulukko = new ArrayList();
    int liikeX;
    int liikeY;
    String teksti ="Backup AMazeIng --- Proof Of Concept --- Petri Riekko 2015";
    
    double viivanKoko = 2.0;
    int pixeliaPerLiike = 10;
    
    int levelNumero = 1;
    Label maaliTeksti = new Label(teksti);
    Label levelTeksti = new Label("Taso:");
    
    Button btnSeuraavaTaso = new Button();
    
    
    
    public static void tauko(int sekuntia) {
        try {
            Thread.sleep(sekuntia);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    public static boolean voikoLiikkua(int paikka){
        boolean onkoOK = false;
        if(liikkeet.contains(paikka)){
            onkoOK = true;
        }
        return onkoOK;
    }
    public static void main(String[] args) {
        launch(args);
    }
    public void play(){
        
    }
 
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Randomly Generated Maze Test");
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(600);
        
        Group root = new Group();
        Canvas canvas = new Canvas(xMaara, yMaara);
        Canvas canvas2 = new Canvas(xMaara, yMaara + 50);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas2);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        piirraMaze(gc);
        
        Circle pallo = new Circle();
        Circle lahto = new Circle();
        Circle maali = new Circle();
        pallo.setRadius(5);
        pallo.setCenterX(xMaara / 2);
        pallo.setCenterY(yMaara / 2);
        pallo.setFill(Color.BLACK);
        lahto.setRadius(5);
        lahto.setCenterX(xMaara / 2);
        lahto.setCenterY(yMaara / 2);
        lahto.setFill(Color.GREEN);
        maali.setRadius(5);
        maali.setCenterX(maaliX);
        maali.setCenterY(maaliY);
        maali.setFill(Color.RED);
        
        
        root.getChildren().add(pallo);
        root.getChildren().add(lahto);
        root.getChildren().add(maali);
        
        Button btn = new Button();
        btn.setText("Aloita uusi");
        btn.setLayoutX(5);
        btn.setLayoutY(yMaara+10);
        btn.setOnAction((ActionEvent event) -> {
            paikkaTaulukossa = 0;
            numero = 1;
            onnistuneetSiirtymiset = 0;
            piirraMaze(gc);
            pallo.setTranslateX(0);
            pallo.setTranslateY(0);
            maali.setCenterX(maaliX);
            maali.setCenterY(maaliY);
            maalissa = false;
            maaliTeksti.setFont(new Font("Arial", 10));
        });
        root.getChildren().add(btn);
        
        
        btnSeuraavaTaso.setText("Seuraava taso");
        btnSeuraavaTaso.setLayoutX(xMaara / 2);
        btnSeuraavaTaso.setLayoutY(yMaara / 2);
        btnSeuraavaTaso.setVisible(false);
        btnSeuraavaTaso.setOnAction((ActionEvent event) -> {
            paikkaTaulukossa = 0;
            numero = 1;
            onnistuneetSiirtymiset = 0;
            piirraMaze(gc);
            pallo.setTranslateX(0);
            pallo.setTranslateY(0);
            maali.setCenterX(maaliX);
            maali.setCenterY(maaliY);
            maalissa = false;
            maaliTeksti.setFont(new Font("Arial", 10));
            btnSeuraavaTaso.setVisible(false);
            siirtymat = siirtymat * 2;
            levelTeksti.setText("Taso:" + levelNumero);
            
            
            
        });
        root.getChildren().add(btnSeuraavaTaso);
        
        Button btnAlkuun = new Button();
        btnAlkuun.setText("Aloita alusta");
        btnAlkuun.setLayoutX(98);
        btnAlkuun.setLayoutY(yMaara+10);
        btnAlkuun.setOnAction((ActionEvent event) -> {
          
            pallo.setTranslateX(0);
            pallo.setTranslateY(0);
            aikaAlku = System.nanoTime();
            maalissa = false;
            maaliTeksti.setText(teksti);
            maaliTeksti.setFont(new Font("Arial", 10));
            //btnAlkuun.setVisible(false);
        });
        root.getChildren().add(btnAlkuun);
        
        
        root.getChildren().add(maaliTeksti);
        maaliTeksti.setLayoutX(210);
        maaliTeksti.setLayoutY(yMaara+10);
        maaliTeksti.setWrapText(true);
        maaliTeksti.setFont(new Font("Arial", 10));
        
        root.getChildren().add(levelTeksti);
        levelTeksti.setLayoutX(460);
        levelTeksti.setLayoutY(yMaara+10);
        levelTeksti.setWrapText(true);
        levelTeksti.setFont(new Font("Arial", 30));
        levelTeksti.setText("Taso:" + levelNumero);
        
        
        root.setOnKeyPressed((KeyEvent keyEvent) -> {
            if("DOWN".equals(keyEvent.getCode().toString()) && maalissa == false && voikoLiikkua(paikkaTaulukkoon((int) pallo.getCenterX() +  (int) pallo.getTranslateX(),(int) pallo.getCenterY() + (int) pallo.getTranslateY() + 5))){
                System.out.println("Alas"); System.out.print("X=");
                System.out.println((int) pallo.getCenterY() + (int) pallo.getTranslateY() + 5);
                System.out.print("Y=");
                System.out.println((int) pallo.getCenterX() +  (int) pallo.getTranslateX());
                System.out.print("Paikka=");
                System.out.println(paikkaTaulukkoon((int) pallo.getCenterX() + (int) pallo.getTranslateX(),(int) pallo.getCenterY() + (int) pallo.getTranslateY() + 5));
                
                TranslateTransition translateTransitionDown = new TranslateTransition();
                translateTransitionDown.setDuration(Duration.millis(10));
                translateTransitionDown.setNode(pallo);
                translateTransitionDown.setFromY(pallo.getTranslateY());
                translateTransitionDown.setToY(pallo.getTranslateY()+5);
                if(paikkaTaulukkoon((int) pallo.getCenterX() +  (int) pallo.getTranslateX(),(int) pallo.getCenterY() + (int) pallo.getTranslateY()+5) == paikkaMaali){
                    maali();}
                translateTransitionDown.play();
                 
                
            }
            if("UP".equals(keyEvent.getCode().toString()) && maalissa == false && voikoLiikkua(paikkaTaulukkoon((int) pallo.getCenterX() +  (int) pallo.getTranslateX(),(int) pallo.getCenterY() + (int) pallo.getTranslateY() - 5))){
                System.out.println("Ylös!");
                System.out.print("X= ");
                System.out.println((int) pallo.getCenterY() + (int) pallo.getTranslateY() -5);
                System.out.print("Y= ");
                System.out.println((int) pallo.getCenterX() +  (int) pallo.getTranslateX());
                System.out.print("Paikka=");
                System.out.println(paikkaTaulukkoon((int) pallo.getCenterX() + (int) pallo.getTranslateX(),(int) pallo.getCenterY() + (int) pallo.getTranslateY() - 5));
                
                TranslateTransition translateTransitionUp = new TranslateTransition();
                translateTransitionUp.setDuration(Duration.millis(10));
                translateTransitionUp.setNode(pallo);
                translateTransitionUp.setFromY(pallo.getTranslateY());
                translateTransitionUp.setToY(pallo.getTranslateY()-5);
                if(paikkaTaulukkoon((int) pallo.getCenterX() +  (int) pallo.getTranslateX(),(int) pallo.getCenterY() + (int) pallo.getTranslateY()-5) == paikkaMaali){
                    maali();}
                translateTransitionUp.play();
                 
                
            }
            if("RIGHT".equals(keyEvent.getCode().toString())&& maalissa == false && voikoLiikkua(paikkaTaulukkoon((int) pallo.getCenterX() +  (int) pallo.getTranslateX() + 5,(int) pallo.getCenterY() + (int) pallo.getTranslateY()))){
                System.out.println("Oikea");
                 System.out.print("X=");
                System.out.println((int) pallo.getCenterY() + (int) pallo.getTranslateY());
                System.out.print("Y=");
                System.out.println((int) pallo.getCenterX() +  (int) pallo.getTranslateX() + 5);
                System.out.print("Paikka=");
                System.out.println(paikkaTaulukkoon((int) pallo.getCenterX() + (int) pallo.getTranslateX() + 5,(int) pallo.getCenterY() + (int) pallo.getTranslateY()));
                
                TranslateTransition translateTransitionRight = new TranslateTransition();
                translateTransitionRight.setDuration(Duration.millis(10));
                translateTransitionRight.setNode(pallo);
                translateTransitionRight.setFromX(pallo.getTranslateX());
                translateTransitionRight.setToX(pallo.getTranslateX()+5);
                if(paikkaTaulukkoon((int) pallo.getCenterX() +  (int) pallo.getTranslateX()+5,(int) pallo.getCenterY() + (int) pallo.getTranslateY()) == paikkaMaali){
                    maali();}
                translateTransitionRight.play();
                 
            }

            if("LEFT".equals(keyEvent.getCode().toString())&& maalissa == false && voikoLiikkua(paikkaTaulukkoon((int) pallo.getCenterX() - 5 +  (int) pallo.getTranslateX(),(int) pallo.getCenterY() + (int) pallo.getTranslateY()))){
                System.out.println("Vasen");
                 System.out.print("X=");
                System.out.println((int) pallo.getCenterY() + (int) pallo.getTranslateY());
                System.out.print("Y=");
                System.out.println((int) pallo.getCenterX() +  (int) pallo.getTranslateX() - 5);
                System.out.print("Paikka=");
                System.out.println(paikkaTaulukkoon((int) pallo.getCenterX() + (int) pallo.getTranslateX() - 5,(int) pallo.getCenterY() + (int) pallo.getTranslateY()));
                
                TranslateTransition translateTransitionRight = new TranslateTransition();
                translateTransitionRight.setDuration(Duration.millis(10));
                translateTransitionRight.setNode(pallo);
                translateTransitionRight.setFromX(pallo.getTranslateX());
                translateTransitionRight.setToX(pallo.getTranslateX()-5);
                if(paikkaTaulukkoon((int) pallo.getCenterX() +  (int) pallo.getTranslateX()-5,(int) pallo.getCenterY() + (int) pallo.getTranslateY()) == paikkaMaali){
                    maali();}
                translateTransitionRight.play();
               
            }
            
            if("P".equals(keyEvent.getCode().toString())){
                
                System.out.print("Paikka=");
                //int x = paikkaTaulukkoon((int) pallo.getCenterX() + (int) pallo.getTranslateX() - 5,(int) pallo.getCenterY() + (int) pallo.getTranslateY());
                System.out.println(paikkaTaulukkoon((int) pallo.getCenterX() + (int) pallo.getTranslateX() - 5,(int) pallo.getCenterY() + (int) pallo.getTranslateY()));
                //System.out.println("X= " + xTaulukosta(x));
            }
        });
    }
       
    
    public void maali(){
        aikaLoppu = System.nanoTime();
        aikaYhteensa = (aikaLoppu - aikaAlku) / 1000000000;
        System.out.println("Hienoa pääsit maalin. Aikaa meni " + aikaYhteensa + " sekuntia.");
        maalissa = true;
        maaliTeksti.setText("Maalissa! Aika: " + aikaYhteensa + " sekuntia");
        maaliTeksti.setFont(new Font("Arial", 10));
        
        
        btnSeuraavaTaso.setVisible(true);
        levelNumero++;
    }
    /**
     * 
     * @return palauttaa arvon väliltä 1 - 4 
     * 1 = ylös
     * 2 = alas
     * 3 = vasen
     * 4 = oikea
     */
    public int valitseSuunta(){
        int suunta = (int) Math.floor(Math.random() * 4) + 1;
      
        
        return suunta;
    }
    
    public int paikkaTaulukkoon(int x, int y){ 
        
        return y*xMaara+x;
    }
    
    public int xTaulukosta(int paikkaTaulukossa){ 
        int x = paikkaTaulukossa % xMaara;  
        return x;
    }
    public int yTaulukosta(int paikkaTaulukossa){ 
        int y = paikkaTaulukossa / xMaara;  
        return y;
    }
    
        
    private void piirraMaze(GraphicsContext gc) {
        liikkeet.clear();
        taulukko.clear();
        gc.clearRect(0, 0, xMaara, yMaara);
        int edellinenX = xMaara / 2;
        int edellinenY = yMaara / 2;
        
        int uusiX = xMaara / 2;
        int uusiY = yMaara / 2;
        
        
        taulukko.add(paikkaTaulukkoon(xMaara /2,yMaara / 2));
        
        //gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(viivanKoko);
        
        
        for (int i = 0; onnistuneetSiirtymiset <= siirtymat; i++){
            if (numero > 100000)break;
            ulkona = false;
            int test;
            
            int suunta = valitseSuunta();
            if (suunta == 1 && edellinenSuunta == 2){
                suunta = edellinenSuunta;
            }
            else if (suunta == 2 && edellinenSuunta == 1){
                suunta = edellinenSuunta;
            }
            else if (suunta == 3 && edellinenSuunta == 4){
                suunta = edellinenSuunta;
            }
            else if (suunta == 4 && edellinenSuunta == 3){
                suunta = edellinenSuunta;
            }
            
            
            if(suunta == 1){
                test = uusiY + pixeliaPerLiike;
                if (test < 0 || test > yMaara){
                    ulkona = true;
                    gc.setStroke(Color.RED);
                }
                else {
                    uusiY = test;
                    uusiX = edellinenX;
                    liikeY = uusiY - 5;
                    liikeX = edellinenX;
                    gc.setStroke(Color.BLUE);
                }
            }
            
            else if(suunta == 2){
                test = uusiY - pixeliaPerLiike;
                if (test < 0 || test > yMaara){
                    ulkona = true;
                    gc.setStroke(Color.RED);
                }
                else {
                    uusiY = test;
                    uusiX = edellinenX;
                    liikeY = uusiY + 5;
                    liikeX = edellinenX;
                    gc.setStroke(Color.BLUE);
                }
            }
            
            else if(suunta == 3){
                test = uusiX - pixeliaPerLiike;
                if (test < 0 || test > xMaara){
                    ulkona = true;
                    gc.setStroke(Color.RED);
                }
                else {
                    uusiX = test;
                    gc.setStroke(Color.BLUE);
                    uusiY = edellinenY;
                    liikeY = edellinenY;
                    liikeX = uusiX + 5; 
                }
            }
            
            else if(suunta == 4){
                test = uusiX + pixeliaPerLiike;
                if (test < 0 || test > xMaara){
                    ulkona = true;
                    gc.setStroke(Color.RED);
                }
                else {
                    uusiX = test;
                    uusiY = edellinenY;
                    gc.setStroke(Color.BLUE);
                    liikeX = uusiX - 5;
                    liikeY = edellinenY;
                }
            }
            else {
                uusiY = edellinenY;
                uusiX = edellinenX;
                liikeX = edellinenX;
                liikeY = edellinenY;
            }
            edellinenSuunta = suunta;
            paikkaTaulukossa = paikkaTaulukkoon(uusiX,uusiY);
            paikkaLiikkeissa = paikkaTaulukkoon(liikeX,liikeY);
            liikeX = edellinenX;
            liikeY = edellinenY;
            if (taulukko.contains(paikkaTaulukossa) || ulkona == true) {
                System.out.println(numero + ". -----  on jo käyty - Paikka: " + paikkaTaulukossa);
                numero++;
                edellinenX = uusiX;
                edellinenY = uusiY;
                }
            else {
            
            gc.strokeLine(edellinenX, edellinenY, uusiX, uusiY);
            
            //liikkeet.add(new XYkoordinaatit(uusiX,uusiY));
            //System.out.println(liikkeet.get(onnistuneetSiirtymiset.getY()));
            
            taulukko.add(paikkaTaulukossa);
            liikkeet.add(paikkaLiikkeissa);
            onnistuneetSiirtymiset++;
            edellinenX = uusiX;
            edellinenY = uusiY;
            }
        }
        maaliX = uusiX;
        maaliY = uusiY;
        paikkaMaali = paikkaTaulukkoon(maaliX,maaliY);
        
        System.out.println("---------------");
        System.out.println(onnistuneetSiirtymiset);
        
        System.out.println(taulukko.size());
        System.out.println(liikkeet.size());
        
         for(int i = 0; i <= taulukko.size() - 1; i++){
            liikkeet.add(taulukko.get(i));  
        }
        aikaAlku = System.nanoTime();
       
       
         
         
    }
   
}
