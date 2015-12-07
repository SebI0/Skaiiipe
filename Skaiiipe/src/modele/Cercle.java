/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Sébastien
 */
public class Cercle extends Forme{
     public Cercle(int a, int b, int c, int d, Color col) {
        super(a,b,c,d,col);
        
    }
    
     @Override
     public void seDessiner(Graphics g, int remplir)
    {
        g.setColor(col);
        if (this.plein==0)
            g.drawOval(a, b, c, d);
         else if (this.plein == 1)
            g.fillOval(a, b, c, d);
        else
        {
            g.setColor(Color.yellow);
            g.fillOval(a, b, c, d);
        }
    }

    @Override
    public void remplir(int color) {
        if (color==2)
            this.plein =2;
        else
            this.plein = 1;
    }
}
