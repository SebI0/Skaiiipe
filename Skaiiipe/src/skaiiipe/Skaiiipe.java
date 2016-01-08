/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skaiiipe;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import vue.StartView;


/**
 *
 * @author Seb
 */
public class Skaiiipe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        
        // TODO code application logic here
        StartView s = new StartView();
        s.setVisible(true);
                StartView s2 = new StartView();
        s2.setVisible(true);
        
        Server_launch s1 = new Server_launch();
        s1.setVisible(true);
        
       
    }
    
}
