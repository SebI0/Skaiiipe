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
public class Rectangle extends Forme{
     public Rectangle(int a, int b, int c, int d, Color col) {
        super(a,b,c,d,col);
        
    }
     
     @Override
    public void seDessiner(Graphics g, int remplir)
    {
        g.setColor(col);
        if (this.plein ==0)
            g.drawRect(a, b, c, d);
        else if (this.plein == 1)
            g.fillRect(a, b, c, d);
        else
        {
            g.setColor(Color.yellow);
            g.fillRect(a, b, c, d);
        }
        
    }

    @Override
    public void remplir(int col) {
        if (col ==2)
            this.plein = 2;
        else
            this.plein = 1;
    }
    
}
