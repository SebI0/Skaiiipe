/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skaiiipe;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import server.ConnexionClient;
import test.testSocket;


/**
 *
 * @author Seb
 */
public class Skaiiipe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        server.Server serveur = new server.Server();
        serveur.start();
                    Socket s1 = new Socket();
            InetSocketAddress sa = new InetSocketAddress("localhost", 60000);
            System.out.println("Try to connect");
            s1.connect(sa);
            System.out.println("Connexion Accepted");
        ConnexionClient ConnexionSock = new ConnexionClient(s1);
        ConnexionSock.start();

    }
    
}
