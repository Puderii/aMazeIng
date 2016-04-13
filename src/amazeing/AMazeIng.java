/*
 * Petri Riekko 2015 
 * Java
 * 
 */
package amazeing;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;

import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;


import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 *
 * @author Petri
 */
public class AMazeIng extends Application {
  static int xMaara = 600; //kentän koko x
   static  int yMaara = 600; //kentän koko y
    int siirtymat = 50;
    int maaliX = 0;
    int maaliY = 0;
    int paikkaMaali;
    long aikaYhteensa;
    long aikaAlku;
    long aikaLoppu;
    long nanoTimeViimeksi = System.nanoTime();
    boolean maalissa = false;
    static int autoLiikeIndex = 0;
    static int paikka;
    static int vEdellinenSuunta = 1;
    static int vSuunta;
    int paikkaTaulukossa;
    int paikkaLiikkeissa;
    boolean ulkona;
    int numero = 1;
    int onnistuneetSiirtymiset = 0;
    int edellinenSuunta = 0;
    static ArrayList liikkeet = new ArrayList();
    static ArrayList liikkeetAnimation = new ArrayList();
    static ArrayList taulukko = new ArrayList();
    int liikeX;
    int liikeY;
    String teksti ="AMazeIng --- Proof Of Concept --- Petri Riekko 2015";
    boolean vihuMaalissa = false;
    static boolean nimiOK = false;
    double viivanKoko = 2.0;
    static int pixeliaPerLiike = 10;
    static String pelaajanNimi;
    int levelNumero = 1;
    Text alkuTeksti = new Text("Anna nimesi:");
    Label maaliTeksti = new Label(teksti);
    Label levelTeksti = new Label("Taso:");
    Text lapiTeksti = new Text("Peli läpi, Onneksi olkoon!");
    Rectangle loppuNelio = new Rectangle();
    Button btnSeuraavaTaso = new Button();
    Rectangle kulkija = new Rectangle();
    PerusVihollinen perusvihu1 = new PerusVihollinen(0, 0, 0);
    Rectangle alkuNelio = new Rectangle();
    
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
        primaryStage.setTitle("Randomly Generated Maze Test --- Proof Of Concept");
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
        
        TextField text1 = new TextField();
        text1.setLayoutX(210);
        text1.setLayoutY(yMaara/2/2);
        Circle pallo = new Circle();
        Circle lahto = new Circle();
        Circle maali = new Circle();
        
        
        alkuTeksti.setLayoutX(210);
        alkuTeksti.setLayoutY((yMaara/2/2)-20);
        alkuTeksti.setFont(new Font("Arial", 20));
        alkuTeksti.setFill(Color.WHITE);
        
        Button nimiNappi = new Button("OK");
         nimiNappi.setLayoutX(210);
        nimiNappi.setLayoutY((yMaara/2/2)+50);
        nimiNappi.setOnAction(e -> {
                pelaajanNimi = text1.getText();           
                alkuNelio.setVisible(false);
                text1.setVisible(false);
                nimiNappi.setVisible(false);
                alkuTeksti.setVisible(false);
                piirraMaze(gc);
                pallo.setTranslateX(0);
            pallo.setTranslateY(0);
            maali.setCenterX(maaliX);
            maali.setCenterY(maaliY);
            maalissa = false;
            
            vihuMaalissa = false;
            perusvihu1.setCenterX(maaliX);
            perusvihu1.setCenterY(maaliY);
            perusvihu1.setPaikkaX(maaliX);
            perusvihu1.setPaikkaY(maaliY);
        });
               
        
        
        
        perusvihu1.setRadius(5);
        perusvihu1.setCenterX(maaliX);
        perusvihu1.setCenterY(maaliY);
        perusvihu1.setFill(Color.PURPLE);
        perusvihu1.setPaikkaX(maaliX);
        perusvihu1.setPaikkaY(maaliY);
        
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
        loppuNelio.setFill(Color.BLACK);
        loppuNelio.setHeight(200);
        loppuNelio.setWidth(400);
        loppuNelio.setX(100);
        loppuNelio.setY(100);
        loppuNelio.setVisible(false);
        
        alkuNelio.setFill(Color.BLACK);
        alkuNelio.setHeight(200);
        alkuNelio.setWidth(400);
        alkuNelio.setX(100);
        alkuNelio.setY(100);
        alkuNelio.setVisible(true);
        
        kulkija.setHeight(5);
        kulkija.setWidth(5);
        kulkija.setX(xMaara-50);
        kulkija.setY(yMaara-50);
        kulkija.setFill(Color.ORANGE);
        
        root.getChildren().add(alkuNelio);
        root.getChildren().add(alkuTeksti);
        root.getChildren().add(text1);
        root.getChildren().add(nimiNappi);
        
        root.getChildren().add(kulkija);
        root.getChildren().add(pallo);
        root.getChildren().add(lahto);
        root.getChildren().add(maali);
        root.getChildren().add(perusvihu1);
        
        Button btn = new Button();
        btn.setText("Uusi peli");
        btn.setLayoutX(5);
        btn.setLayoutY(yMaara+10);
        btn.setOnAction((ActionEvent event) -> {
            paikkaTaulukossa = 0;
            numero = 1;
            onnistuneetSiirtymiset = 0;
            levelNumero = 1;
            piirraMaze(gc);
            pallo.setTranslateX(0);
            pallo.setTranslateY(0);
            maali.setCenterX(maaliX);
            maali.setCenterY(maaliY);
            maalissa = false;
            maaliTeksti.setFont(new Font("Arial", 10));
            vihuMaalissa = false;
            perusvihu1.setCenterX(maaliX);
            perusvihu1.setCenterY(maaliY);
            perusvihu1.setPaikkaX(maaliX);
            perusvihu1.setPaikkaY(maaliY);
        });
        root.getChildren().add(btn);
        
        
        btnSeuraavaTaso.setText("Seuraava taso");
        btnSeuraavaTaso.setLayoutX(xMaara - 130);
        btnSeuraavaTaso.setLayoutY(yMaara - 30);
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
            
            vihuMaalissa = false;
            perusvihu1.setCenterX(maaliX);
            perusvihu1.setCenterY(maaliY);
            perusvihu1.setPaikkaX(maaliX);
            perusvihu1.setPaikkaY(maaliY);
            
            maaliTeksti.setFont(new Font("Arial", 10));
            btnSeuraavaTaso.setVisible(false);
            siirtymat = siirtymat * 2;
            levelTeksti.setText("Taso:" + levelNumero + "/9");
            
            
            
        });
        root.getChildren().add(btnSeuraavaTaso);
        
        Button btnAlkuun = new Button();
        btnAlkuun.setText("Taso alusta");
        btnAlkuun.setLayoutX(90);
        btnAlkuun.setLayoutY(yMaara+10);
        btnAlkuun.setOnAction((ActionEvent event) -> {
          
            pallo.setTranslateX(0);
            pallo.setTranslateY(0);
            aikaAlku = System.nanoTime();
            maalissa = false;
            maaliTeksti.setText(teksti);
            maaliTeksti.setFont(new Font("Arial", 10));
            vihuMaalissa = false;
            perusvihu1.setCenterX(maaliX);
            perusvihu1.setCenterY(maaliY);
            perusvihu1.setPaikkaX(maaliX);
            perusvihu1.setPaikkaY(maaliY);
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
        levelTeksti.setText("Taso:" + levelNumero + "/9");
        
        
        lapiTeksti.setLayoutX(140);
        lapiTeksti.setLayoutY(200);
        
        lapiTeksti.setFont(new Font("Arial", 30));
        lapiTeksti.setFill(Color.WHITE);
        lapiTeksti.setVisible(false);
        
        
        
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
            
            if("F".equals(keyEvent.getCode().toString())){
                
                System.out.print("Paikka  =  ");
                
                System.out.println(paikkaTaulukkoon((int)pallo.getCenterX() + (int)pallo.getTranslateX(),(int)pallo.getCenterY() + (int)pallo.getTranslateY()));
                
                     
                    
                    System.out.println("X= " + maaliX + " : " + pallo.getTranslateX());
                    System.out.println("Y= " + maaliY + " : " + pallo.getTranslateY());
                    System.out.println("-------");
                    
                        TranslateTransition translateTransitionMaaliin = new TranslateTransition();
                        translateTransitionMaaliin.setDuration(Duration.millis(1000));
                        translateTransitionMaaliin.setNode(pallo);

                        translateTransitionMaaliin.setFromX(pallo.getTranslateX());
                        translateTransitionMaaliin.setToX(maaliX - ((int) pallo.getCenterX() + (int) pallo.getTranslateX()));

                        translateTransitionMaaliin.setFromY(pallo.getTranslateY());
                        translateTransitionMaaliin.setToY(maaliY - ((int) pallo.getCenterY() + (int) pallo.getTranslateY()));
                        
                        translateTransitionMaaliin.play();     
            }
            
            if("A".equals(keyEvent.getCode().toString())){
                
                Path path = new Path();
                path.getElements().add(new MoveTo(xMaara / 2,yMaara / 2));
               
                
                for(int i = 0; i < liikkeetAnimation.size();i++){
                 path.getElements().add(new LineTo(xTaulukosta((int)liikkeetAnimation.get(i)), yTaulukosta((int)liikkeetAnimation.get(i))));   
                }
                
                PathTransition pathToMaali = new PathTransition();
                pathToMaali.setDuration(Duration.millis(4000*levelNumero));
                pathToMaali.setPath(path);
                pathToMaali.setNode(kulkija);
                
                pathToMaali.setCycleCount(1);
                pathToMaali.setAutoReverse(true);
                pathToMaali.play();
                
                        
            }
            if("Q".equals(keyEvent.getCode().toString())){
                
                solveMaze();
                
                        
            }
            
        });
        
        Timeline vihunLiikeTimeLine = new Timeline(new KeyFrame(Duration.seconds(0.01), (ActionEvent event) -> {
            if (vihuMaalissa == false){
            vihunliike();}
            
        }));
        vihunLiikeTimeLine.setCycleCount(Timeline.INDEFINITE);
        vihunLiikeTimeLine.play();

        if (1000 < (System.nanoTime() - nanoTimeViimeksi)){
            vihunliike();
            nanoTimeViimeksi = System.nanoTime();
        }
        
        root.getChildren().add(loppuNelio);
        root.getChildren().add(lapiTeksti);
    }
    
    public int eiTakaisin(int pEdellinenSuunta){
        int pSuunta = valitseSuunta();
        if (pSuunta == 1 && pEdellinenSuunta == 2){
                pSuunta = pEdellinenSuunta;
            }
            else if (pSuunta == 2 && pEdellinenSuunta == 1){
                pSuunta = pEdellinenSuunta;
            }
            else if (pSuunta == 3 && pEdellinenSuunta == 4){
                pSuunta = pEdellinenSuunta;
            }
            else if (pSuunta == 4 && pEdellinenSuunta == 3){
                pSuunta = pEdellinenSuunta;
            }
        return pSuunta;
    }  
    
    public int mahdPaikat(){
        int paikatMaara = 0;
        int mahdX;
        int mahdY;
        int mahdPaikka;
        
        mahdX = perusvihu1.getPaikkaX();
        mahdY = perusvihu1.getPaikkaY() + pixeliaPerLiike/2;
        mahdPaikka = paikkaTaulukkoon(mahdX,mahdY);
        
       if (voikoLiikkua(mahdPaikka)){
                   paikatMaara++;
                }
        mahdX = perusvihu1.getPaikkaX();
        mahdY = perusvihu1.getPaikkaY() - pixeliaPerLiike/2;
        mahdPaikka = paikkaTaulukkoon(mahdX,mahdY);
        
       if (voikoLiikkua(mahdPaikka)){
                   paikatMaara++;
                }
        mahdX = perusvihu1.getPaikkaX() - pixeliaPerLiike/2;
        mahdY = perusvihu1.getPaikkaY();
        mahdPaikka = paikkaTaulukkoon(mahdX,mahdY);
        
       if (voikoLiikkua(mahdPaikka)){
                   paikatMaara++;
                }
        mahdX = perusvihu1.getPaikkaX() + pixeliaPerLiike/2;
        mahdY = perusvihu1.getPaikkaY();
        mahdPaikka = paikkaTaulukkoon(mahdX,mahdY);
        
       if (voikoLiikkua(mahdPaikka)){
                   paikatMaara++;
                }
        //System.out.println(paikatMaara);
       return paikatMaara;
    }
    public void vihunliike(){
        
        int testX;
        int testY;
        int testPaikka;
        int vPaikka = perusvihu1.getPaikka();
        long vihunAikaLoppu;
        long vihunAikaYhteensa;
        vihunAikaLoppu = System.nanoTime();
        vihunAikaYhteensa = (vihunAikaLoppu - aikaAlku) / 1000000000;
        if (paikkaTaulukkoon(xMaara/2,yMaara/2) == perusvihu1.getPaikka()){
        vihuMaalissa = true;
            System.out.println("Vihu maalissa ajassa " + vihunAikaYhteensa + " sekuntia!");
    }
        
        if (mahdPaikat() >= 3){
            vEdellinenSuunta = 0;
        }
        if (vEdellinenSuunta == 0){
            vSuunta = valitseSuunta();
        }
        else {
        vSuunta = vEdellinenSuunta;   
        }
        
        
        
        if(vSuunta == 1){
                testX = perusvihu1.getPaikkaX();
                testY = perusvihu1.getPaikkaY() + pixeliaPerLiike/2;
                testPaikka = paikkaTaulukkoon(testX,testY);
                
                if (voikoLiikkua(testPaikka)){
                    perusvihu1.setCenterX(testX);
                    perusvihu1.setCenterY(testY);
                    perusvihu1.setPaikkaX(testX);
                    perusvihu1.setPaikkaY(testY);
                    vEdellinenSuunta = 1;
                }
                else{
                    vEdellinenSuunta = 0;
                }
            }
            
            else if(vSuunta == 2){
                testX = perusvihu1.getPaikkaX();
                testY = perusvihu1.getPaikkaY() - pixeliaPerLiike/2;
                testPaikka = paikkaTaulukkoon(testX,testY);
                if (voikoLiikkua(testPaikka)){
                    perusvihu1.setCenterX(testX);
                    perusvihu1.setCenterY(testY);
                    perusvihu1.setPaikkaX(testX);
                    perusvihu1.setPaikkaY(testY);
                    vEdellinenSuunta = 2;
                    //System.out.println(perusvihu1.getPaikka());
                }
               else{
                    vEdellinenSuunta = 0;
                }
            }
            
            else if(vSuunta == 3){
                 testX = perusvihu1.getPaikkaX()- pixeliaPerLiike/2;
                testY = perusvihu1.getPaikkaY();
                testPaikka = paikkaTaulukkoon(testX,testY);
                if (voikoLiikkua(testPaikka)){
                    perusvihu1.setCenterX(testX);
                    perusvihu1.setCenterY(testY);
                    perusvihu1.setPaikkaX(testX);
                    perusvihu1.setPaikkaY(testY);
                    vEdellinenSuunta = 3;
                   // System.out.println(perusvihu1.getPaikka());
                }
             else{
                    vEdellinenSuunta = 0;
                }
            }
            
            else if(vSuunta == 4){
                testX = perusvihu1.getPaikkaX()+ pixeliaPerLiike/2;
                testY = perusvihu1.getPaikkaY();
                testPaikka = paikkaTaulukkoon(testX,testY);
                if (voikoLiikkua(testPaikka)){
                    perusvihu1.setCenterX(testX);
                    perusvihu1.setCenterY(testY);
                    perusvihu1.setPaikkaX(testX);
                    perusvihu1.setPaikkaY(testY);
                    vEdellinenSuunta = 4;
                    //System.out.println(perusvihu1.getPaikka());
                }
              else{
                    vEdellinenSuunta = 0;
                }
            }
        
        perusvihu1.setPaikka(paikkaTaulukkoon(perusvihu1.getPaikkaX(),perusvihu1.getPaikkaY()));
            
    }
     public static void uusiTulos(Connection con, int level, long aika,String pelaaja) throws Exception, SQLException {
        
     
        
       Statement lause2 = con.createStatement();
            String sqlLisays = "insert into Scores (Nimi,Aika,Level) values ('"+ pelaaja +"','"+ aika +"','"+ level +"')";
            
            int tulos = lause2.executeUpdate(sqlLisays);
        
}

    public void tulosKantaan(String pelaaja,int level, long aika){
          
       
    Connection con = null;
    try {
      //ladataan ajuri käyttäen Class.forName() -metodia
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      //muodostetaan yhteys
     con = DriverManager.getConnection("jdbc:mysql:///amazeing",
        "Tester", "tester"); //mysql tunnarit
    //jos yhteys on nyt avautunut (ei suljettu) suoritetaan metodit
      if(!con.isClosed())
          
     uusiTulos(con,level,aika,pelaaja);
      
    //jos yhteydenotto epäonnistuu tulostetaan poikkeuksen antama virheilmoitus
    } catch(Exception e) {
      System.err.println("Exception: " + e.getMessage());
    } finally {
      try {
        //lopuksi katkaistaan yhteys kantaan
          if(con != null)
          con.close();
      } catch(SQLException e) {}
    }
    }
    public void maali(){
        aikaLoppu = System.nanoTime();
        aikaYhteensa = (aikaLoppu - aikaAlku) / 1000000000;
        System.out.println("Hienoa pääsit maalin. Aikaa meni " + aikaYhteensa + " sekuntia.");
        maalissa = true;
        maaliTeksti.setText("Maalissa! Aika: " + aikaYhteensa + " sekuntia");
        maaliTeksti.setFont(new Font("Arial", 10));
        tulosKantaan(pelaajanNimi, levelNumero, aikaYhteensa);
        
        
        btnSeuraavaTaso.setVisible(true);
        levelNumero++;
        if(levelNumero == 10){
            btnSeuraavaTaso.setVisible(false);
            lapiTeksti.setVisible(true); 
            loppuNelio.setVisible(true);
        }
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
    
    static public int paikkaTaulukkoon(int x, int y){ 
        
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
        liikkeetAnimation.clear();
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
            if (numero > 50000)break;
            ulkona = false;
            int test;
            
            int suunta = eiTakaisin(edellinenSuunta);
            
            
            if(suunta == 1){
                test = uusiY + pixeliaPerLiike;
                if (test < 5 || test > yMaara-5){
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
                if (test < 5 || test > yMaara-5){
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
                if (test < 5 || test > xMaara-5){
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
                if (test < 5 || test > xMaara-5){
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
            liikkeetAnimation.add(paikkaTaulukossa);
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
    public static void ammuSolverit(int paikkaX, int paikkaY, int paikka, ArrayList paikat, int generaatio){
      Solver solver1 = new Solver(paikkaX,paikkaY,paikka,paikat,generaatio,1);
      //Solver solver2 = new Solver(paikkaX,paikkaY,paikka,paikat,generaatio,2);
      Solver solver3 = new Solver(paikkaX,paikkaY,paikka,paikat,generaatio,3);
      Solver solver4 = new Solver(paikkaX,paikkaY,paikka,paikat,generaatio,4);
      solver1.liiku();
      //solver2.liiku();
      solver3.liiku();
      solver4.liiku();
    }
   
   public void solveMaze(){
    // ei toimi
   ArrayList solverReitti = new ArrayList();
   //Solver solver = new Solver(xMaara/2,yMaara/2,paikkaTaulukkoon(xMaara/2,yMaara/2),solverReitti,0,0);
   ammuSolverit(xMaara/2,yMaara/2,paikkaTaulukkoon(xMaara/2,yMaara/2),solverReitti,0);
  
   }
}
