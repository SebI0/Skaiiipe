
package tppaint2014;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;




public class EcouteurSouris implements MouseListener, MouseMotionListener{

    public Fenetre saF;
    public int xi, yi, xf, yf;
    public int xa, ya;
    
    public int[] polyX = new int[3];
    public int[] polyY = new int[3];
    static int nbDroite = 0;

    Forme F = null;

    public boolean firstClic = false;
    
    
    public EcouteurSouris(Fenetre laF) {
        saF = laF;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (saF.COLORIAGE_M23 == 1)
        {
            System.out.println("Chosen");
            xa = e.getX();
            ya = e.getY();

            int i;
            
           
            for (i=0; i<saF.lesFormes.size(); i++)
            {
                Forme f = saF.lesFormes.get(i);
                if (f instanceof Rectangle)
                {         
                    if (xa < f.c +f.a && xa > f.a && ya > f.b && ya < f.b + f.d)
                    {
                        f.remplir(1);
                    }
                }
                else if (f instanceof Cercle)
                {
                        double u = f.a + f.c/2;
                        double v = f.b + f.d/2;
                        double x = xa;
                        double y = ya;
                        double a = f.c/2;
                        double b = f.d/2;
                    
                    if (Math.pow((x-u)/a,2) + Math.pow((y-v)/b, 2) <= 1)
                    {
                        System.out.println("cercle colorié");
                        f.remplir(1);
                    }
                    
                }
              
            }
            

            saF.zg.repaint();
            saF.COLORIAGE_M23 = 0; //on arrete le coloriage magique
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        xi = e.getX();
        yi = e.getY();
        saF.bb.changerMessage("olol");    
        this.firstClic = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (saF.COLORIAGE_M23 == 0)
        {
            xf = e.getX();
            yf = e.getY();
            this.firstClic = false;

            if (saF.ModeGomme == false)
            {
             Graphics g = this.saF.zg.getGraphics(); //prendre la feuille blanche

             switch(this.saF.bh.couleurs.getSelectedIndex())
             {
                 case 0:  g.setColor(Color.blue); break;

                 case 1:  g.setColor(Color.red);  break;

                 case 2:  g.setColor(Color.green); break;

                 case 3:  g.setColor(Color.orange); break;                 
             }

             Forme F = null;
            //tracons la forme demandée
            switch(this.saF.bh.formes.getSelectedIndex())
            {
                case 0:
                    F = new Droite(xi, yi, xf, yf, g.getColor());
                    g.drawLine(xi, yi, xf, yf);
                    break;

                case 1:
                    F = new Rectangle(Math.min(xi, xf) , Math.min(yi, yf), Math.abs(xf-xi), Math.abs(yf-yi),  g.getColor());
                    g.drawRect(Math.min(xi, xf) , Math.min(yi, yf), Math.abs(xf-xi), Math.abs(yf-yi));
                    break;

                case 2:
                    F = new Cercle(Math.min(xi, xf) , Math.min(yi, yf), Math.abs(xf-xi), Math.abs(yf-yi), g.getColor());
                    g.drawOval(Math.min(xi, xf) , Math.min(yi, yf), Math.abs(xf-xi), Math.abs(yf-yi));
                    break;       

                case 3:
                    F = new Disque(Math.min(xi, xf) , Math.min(yi, yf), Math.abs(xf-xi), Math.abs(yf-yi), g.getColor());
                    g.fillOval(Math.min(xi, xf) , Math.min(yi, yf), Math.abs(xf-xi), Math.abs(yf-yi));
                    break;  

                case 4:
                                      
                    int[] posX = new int[3];
                    int[] posY = new int[3];

                    posX[0] = xi;
                    posX[1] = xf;
                    posX[2] = xi + 50;

                    posY[0] = yi;
                    posY[1] = yf;
                    posY[2] = yi - 50;
                    g.drawPolygon(posX, posY, 3);
                    
                    F = new Triangle(xi, yi, xf, yf, g.getColor());

                    break;
                    
                case 5:

                    int a =  Math.min(xi, xf);
                    int b = Math.min(yi, yf);
                    int c = Math.abs(xf-xi);
                    int d = Math.abs(yf-yi);
                    
                   g.drawRect(a, b, (int) ((double)0.8*c), (int) ((double)0.8*d)); //remorque
                   g.drawRect(a+(int) ((double)0.8*c), b+(int) ((double)0.2*d) , (int) ((double)0.2*c), (int) ((double)0.6*d)); //cabine
                   g.drawRect(a+(int) ((double)0.8*c) + 5 , b+(int) ((double)0.2*d) + 5 , (int) ((double)0.1*c), (int) ((double)0.1*d));
                    //roues
                    g.drawOval((int)(double) (a + (c/2-0.2*c)), (int)(double)( b + d -0.2*d - 20), (int)(double)(0.2*c), (int)(double)(c*0.2));
                    g.drawOval((int)(double) (a + (c/2+0.2*c)), (int)(double) ( b + d-0.2*d - 20), (int)(double)(0.2*c), (int)(double)(c*0.2));
                    F = new Camion(a,b,c,d, g.getColor());
                    break;
            }
            this.saF.communication.sendForm(F);
            this.saF.lesFormes.add(F);
            saF.bb.changerMessage("Ta mère");
            }
            else
            {
                
                this.saF.lesFormes.add(F);
                this.saF.communication.sendForm(F);
                saF.EditEnCours=false;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) { //motion
        if (saF.COLORIAGE_M23 == 0)
        {
            this.saF.bb.x.setText("x = " + e.getX());
            this.saF.bb.y.setText("y = " + e.getY());

            xf=e.getX();
            yf=e.getY();

            if (saF.ModeGomme == true && saF.COLORIAGE_M23 == 0)
            {
                saF.bb.changerMessage("Maintenez pour effacer");

                Graphics g = this.saF.zg.getGraphics(); // prendre la feuille blanche
                g.setColor(Color.WHITE);
                if(saF.EditEnCours==false)
                {
                    Forme F=null;
                    saF.EditEnCours=true;
                }

                F = new Gomme(xf,yf,g.getColor());
                g.drawOval(xf,yf,10,10);
                g.fillOval(xf,yf,10,10);
                saF.lesFormes.add(F);
            }   

            else if (this.firstClic && saF.COLORIAGE_M23 == 0) //dessin temps reel
            {
                saF.zg.repaint();
                Graphics g = this.saF.zg.getGraphics(); //prendre la feuille blanche


                 switch(this.saF.bh.couleurs.getSelectedIndex())
                 {
                     case 0:  g.setColor(Color.blue); break;

                     case 1:  g.setColor(Color.red);  break;

                     case 2:  g.setColor(Color.green); break;

                     case 3:  g.setColor(Color.orange); break;                 
                 }

                 Forme F = null;
                //tracons la forme demandée

                switch(this.saF.bh.formes.getSelectedIndex())
                {
                    case 0:
                        g.drawLine(xi, yi, xf, yf);
                        break;

                    case 1:
                        g.drawRect(Math.min(xi, xf) , Math.min(yi, yf), Math.abs(xf-xi), Math.abs(yf-yi));
                        break;

                    case 2:
                        g.drawOval(Math.min(xi, xf) , Math.min(yi, yf), Math.abs(xf-xi), Math.abs(yf-yi));
                        break;       

                    case 3:
                        g.fillOval(Math.min(xi, xf) , Math.min(yi, yf), Math.abs(xf-xi), Math.abs(yf-yi));
                        break;  

                    case 4:                    
                        g.drawLine(xi, yi, xf, yf);
                        g.drawLine(xi, yi, xi + 50, yi - 50);
                        g.drawLine(xi + 50, yi - 50, xf, yf);

                        break;
                }
            }
        }
        
    }

    @Override
    public void mouseMoved(MouseEvent e) { //motion
        this.saF.bb.x.setText("x = " + e.getX());
        this.saF.bb.y.setText("y = " + e.getY());
    }
    
}
