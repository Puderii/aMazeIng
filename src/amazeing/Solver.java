/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amazeing;

import static amazeing.AMazeIng.liikkeet;
import java.util.ArrayList;

/**
 *
 * @author Petri
 */
public class Solver {
    
    int paikkaX;
    int paikkaY;
    int paikka;
    ArrayList paikat;
    int generaatio;
    int suunta;

    public Solver(int paikkaX, int paikkaY, int paikka, ArrayList paikat, int generaatio, int suunta) {
        this.paikkaX = paikkaX;
        this.paikkaY = paikkaY;
        this.paikka = paikka;
        this.generaatio = generaatio+1;
        this.suunta = suunta;


    
    }

    public int getPaikkaX() {
        return paikkaX;
    }

    public void setPaikkaX(int paikkaX) {
        this.paikkaX = paikkaX;
    }

    public int getPaikkaY() {
        return paikkaY;
    }

    public void setPaikkaY(int paikkaY) {
        this.paikkaY = paikkaY;
    }

    public int getPaikka() {
        return paikka;
    }

    public void setPaikka(int paikka) {
        this.paikka = paikka;
    }

    public ArrayList getPaikat() {
        return paikat;
    }

    public void setPaikat(ArrayList paikat) {
        this.paikat = paikat;
    }

    public int getGeneraatio() {
        return generaatio;
    }

    public void setGeneraatio(int generaatio) {
        this.generaatio = generaatio;
    }

    public int getSuunta() {
        return suunta;
    }

    public void setSuunta(int suunta) {
        this.suunta = suunta;
    }
    public static boolean voikoLiikkua(int paikka){
        boolean onkoOK = false;
        if(liikkeet.contains(paikka)){
            onkoOK = true;
        }
        return onkoOK;
    }
    public void liiku(){
        int liikeMaara = 0;
        int ppl = AMazeIng.pixeliaPerLiike / 2;
        
        
        
        if(suunta == 1){
            
                while (voikoLiikkua(AMazeIng.paikkaTaulukkoon(this.paikkaX,this.paikkaY + ppl)) == true){
                    System.out.println("1 " + AMazeIng.paikkaTaulukkoon(this.paikkaX,this.paikkaY + ppl));
                    this.paikkaY = this.paikkaY+ppl;
                }
                while (generaatio < 2){
                   AMazeIng.ammuSolverit(this.paikkaX, (this.paikkaY+ppl), AMazeIng.paikkaTaulukkoon(this.paikkaX,this.paikkaY + ppl + liikeMaara), this.paikat, this.generaatio);  
                }
               
        }
         if(suunta == 2){
            
                while (voikoLiikkua(AMazeIng.paikkaTaulukkoon(this.paikkaX,(this.paikkaY - ppl - liikeMaara)))){
                    System.out.println("2 " + AMazeIng.paikkaTaulukkoon(paikkaX,paikkaY - ppl - liikeMaara));
                    System.out.println(paikkaX + " ja " + (paikkaY-liikeMaara));
                    liikeMaara = liikeMaara + ppl;
                }
                 while (generaatio < 2){
                 AMazeIng.ammuSolverit(this.paikkaX, (this.paikkaY-ppl-liikeMaara), AMazeIng.paikkaTaulukkoon(paikkaX,paikkaY - ppl - liikeMaara), paikat, generaatio);
                 }
        } 
        if(suunta == 3){
            
                while (voikoLiikkua(AMazeIng.paikkaTaulukkoon(paikkaX - ppl - liikeMaara,paikkaY))){
                    System.out.println("3 " + AMazeIng.paikkaTaulukkoon(paikkaX - ppl - liikeMaara,paikkaY));
                    System.out.println (paikkaX - ppl - liikeMaara +" ja " +paikkaY);
                    liikeMaara = liikeMaara + ppl;
                }
                 while (generaatio < 2){
                AMazeIng.ammuSolverit((this.paikkaX-ppl-liikeMaara), this.paikkaY, AMazeIng.paikkaTaulukkoon(paikkaX - ppl - liikeMaara,paikkaY), paikat, generaatio);
                 }
        }
         if(suunta == 4){
            
                while (voikoLiikkua(AMazeIng.paikkaTaulukkoon(paikkaX + ppl + liikeMaara,paikkaY))){
                    System.out.println("4 " + AMazeIng.paikkaTaulukkoon(paikkaX + ppl + liikeMaara,paikkaY));
                    System.out.println(paikkaX +" ja " +paikkaY);
                    liikeMaara = liikeMaara + ppl;
                }
                 while (generaatio < 2){
                AMazeIng.ammuSolverit((this.paikkaX+ppl+liikeMaara), this.paikkaY, AMazeIng.paikkaTaulukkoon(paikkaX + ppl + liikeMaara,paikkaY), paikat, generaatio);
                 }
        }       
    }
    
}
