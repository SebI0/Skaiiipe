package tppaint2014;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Sébastien
 */
public class Disque extends Forme{

    public Disque(int a, int b, int c, int d, Color couleur) {
        super(a, b, c, d, couleur);
    }

    @Override
    public void seDessiner(Graphics g,int remplir) {
        g.setColor(col);
        g.fillOval(a, b, c, d);
       //g.drawOval(a, b, c, d);
    }


    @Override
    public void remplir(int col) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
