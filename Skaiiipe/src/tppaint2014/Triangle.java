package tppaint2014;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Sébastien
 */
public class Triangle extends Forme {

    public Triangle(int a, int b, int c, int d, Color couleur) {
        super(a, b, c, d, couleur);
    }

    @Override
    public void seDessiner(Graphics g, int remplir) {
        g.setColor(col);
        int[] posX = new int[3];
        int[] posY = new int[3];

        posX[0] = a;
        posX[1] = c;
        posX[2] = a + 50;

        posY[0] = b;
        posY[1] = d;
        posY[2] = b - 50;

        if (this.plein == 0) {
            g.drawPolygon(posX, posY, 3);
        }
        if (this.plein == 1) {
            g.fillPolygon(posX, posY, 3);
        } else if (this.plein == 2) {
            g.setColor(Color.yellow);
            g.fillPolygon(posX, posY, 3);
        }

    }

    @Override
    public void remplir(int color) {
        if (color == 2) {
            this.plein = 2;
        } else {
            this.plein = 1;
        }
    }
}
