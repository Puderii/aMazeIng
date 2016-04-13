/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amazeing;

/**
 *
 * @author Petri
 */
public abstract class Vihollinen extends javafx.scene.shape.Circle{
    int paikkaX;
    int paikkaY;
    int paikka;

    public Vihollinen(int paikkaX, int paikkaY, int paikka) {
        this.paikkaX = paikkaX;
        this.paikkaY = paikkaY;
        this.paikka = paikka;
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
    
    
}
