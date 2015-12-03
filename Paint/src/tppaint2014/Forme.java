/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tppaint2014;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;


public abstract class Forme implements Serializable {
    
    public int a,b,c,d;
    public Color col;
    int plein;

    public Forme(int a, int b, int c, int d, Color couleur) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.col = couleur;
    }
    
    public abstract void seDessiner(Graphics g, int remplir);
    public abstract void remplir(int col);

}
