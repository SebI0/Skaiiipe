/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tppaint2014;

import java.awt.Color;
import java.awt.Graphics;


public class Droite extends Forme{

    public Droite(int a, int b, int c, int d, Color col) {
        super(a, b, c, d, col);
    }

    @Override
    public void seDessiner(Graphics g, int remplir) {
        g.setColor(col);
       g.drawLine(a, b, c, d);
    }

    @Override
    public void remplir(int col) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
