/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tppaint2014;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Label;
import java.awt.Panel;

public class BarreBasse extends Panel {

    public Label message;
    public Label x, y;
    // public static Label x, y;

    public BarreBasse() {

        this.setBackground(Color.lightGray);
        message = new Label("Coucou");
        x = new Label("x=       ");
        y = new Label("y=       ");

        this.add(message, BorderLayout.WEST);
        this.add(x);
        this.add(y);
    }

    public void changerMessage(String msg) {
        this.message.setText(msg);
    }

}
