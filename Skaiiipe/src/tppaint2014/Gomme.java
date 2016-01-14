/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tppaint2014;

import java.awt.Color;
import java.awt.Graphics;

public class Gomme extends Forme {

    public Gomme(int a, int b, Color col) {
        super(a, b, 0, 0, col);
    }

    @Override
    public void seDessiner(Graphics g, int remplir) {
        g.setColor(col);
        g.drawOval(a, b, 10, 10);
        g.fillOval(a, b, 10, 10);
    }

    @Override
    public void remplir(int col) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
