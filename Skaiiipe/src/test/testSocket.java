/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elie
 */
public class testSocket extends Thread {

    @Override
    public void run() {
        OutputStream os = null;
        try {
            System.out.println("Tests sockets");
            //Création d'une socket adresse, port, stream ou non
            Socket s1 = new Socket();
            InetSocketAddress sa = new InetSocketAddress("localhost", 60000);
            //s1.bind(sa);
            //5s3.bind(sa);
            s1.connect(sa);
            System.out.println("Connexion Accepted");
            System.out.println(s1.isConnected());
            os = s1.getOutputStream();
            ObjectOutputStream outputClient = new ObjectOutputStream(os);
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // long start=System.nanoTime();
                    // while((System.nanoTime()-start)<1000000000);
                    // System.out.println("Done");
                    outputClient.writeObject("Ceci est un test");
                } catch (IOException ex) {
                    Logger.getLogger(testSocket.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(testSocket.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(testSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Tests sockets");
        //Création d'une socket adresse, port, stream ou non 
        Socket s1 = new Socket();

        InetSocketAddress sa = new InetSocketAddress("localhost", 60000);
        //s1.bind(sa);

        //5s3.bind(sa);
        s1.connect(sa);

        System.out.println("Connexion Accepted");
        System.out.println(s1.isConnected());
        OutputStream os = s1.getOutputStream();
        ObjectOutputStream outputClient = new ObjectOutputStream(os);

        while (true) {
            // long start=System.nanoTime(); 
            // while((System.nanoTime()-start)<1000000000); 
            // System.out.println("Done");
            outputClient.writeObject("Ceci est un test");
        }
    }
}
