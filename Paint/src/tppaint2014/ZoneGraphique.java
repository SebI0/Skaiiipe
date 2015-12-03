package tppaint2014;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class ZoneGraphique extends Canvas{

    public Fenetre saF;
    
    public ZoneGraphique(Fenetre laF) {
        this.setBackground(Color.white);
        saF = laF;
    }
    
    
    @Override
    public void paint(Graphics g)
    {
      
        for(Forme fo : this.saF.lesFormes)
        {
            fo.seDessiner(g, 0);
        }
        
    }
    
    
}
