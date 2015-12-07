package tppaint2014;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Sébastien
 */
public class Camion extends Forme{

    public Camion(int a, int b, int c, int d, Color couleur) {
        super(a, b, c, d, couleur);
    }

    @Override
    public void seDessiner(Graphics g, int remplir) {
        if (this.plein==0)
        {
            g.setColor(col);
          g.drawRect(a, b, (int) ((double)0.8*c), (int) ((double)0.8*d)); //remorque
     
        g.drawRect(a+(int) ((double)0.8*c), b+(int) ((double)0.2*d) , (int) ((double)0.2*c), (int) ((double)0.6*d)); //cabine
        g.drawRect(a+(int) ((double)0.8*c) + 5 , b+(int) ((double)0.2*d) + 5 , (int) ((double)0.1*c), (int) ((double)0.1*d)); //fenetre
         //roues
         g.drawOval((int)(double) (a + (c/2-0.2*c)), (int)(double)( b + d -0.2*d - 20), (int)(double)(0.2*c), (int)(double)(c*0.2));
         g.drawOval((int)(double) (a + (c/2+0.2*c)), (int)(double) ( b + d-0.2*d - 20), (int)(double)(0.2*c), (int)(double)(c*0.2));
        }
        else if (this.plein==1)
        {
            g.setColor(col);
            g.fillRect(a, b, (int) ((double)0.8*c), (int) ((double)0.8*d)); //remorque
     
        g.fillRect(a+(int) ((double)0.8*c), b+(int) ((double)0.2*d) , (int) ((double)0.2*c), (int) ((double)0.6*d)); //cabine
        

         //roues
         g.fillOval((int)(double) (a + (c/2-0.2*c)), (int)(double)( b + d -0.2*d - 20), (int)(double)(0.2*c), (int)(double)(c*0.2));
         g.fillOval((int)(double) (a + (c/2+0.2*c)), (int)(double) ( b + d-0.2*d - 20), (int)(double)(0.2*c), (int)(double)(c*0.2));
         
         g.setColor(Color.white);
                 g.fillRect(a+(int) ((double)0.8*c) + 5 , b+(int) ((double)0.2*d) + 5 , (int) ((double)0.1*c), (int) ((double)0.1*d)); //fenetre
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
