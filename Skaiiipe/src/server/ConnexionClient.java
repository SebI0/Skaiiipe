/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import services.Message;
import vue.Host;

/**
 *
 * @author Elie
 */
public class ConnexionClient extends Thread{
    Socket socketServer;
    int id;
    public static int incre = 0;
    private ObjectInputStream InputClient;
    
    public Socket getSocket() {

        return socketServer;
        
    }

    public ConnexionClient(java.net.Socket socketServer) throws IOException {
        
        this.socketServer = socketServer;
        id = incre;
        incre++;
        System.out.println("Init Input");
        //InputClient = new ObjectInputStream(socketServer.getInputStream());
        System.out.println("Input initialisé");
    }
   @Override
    public void run(){
        //écouteur du client maitre
         
            while(!Thread.currentThread().isInterrupted()){
                try {
                    Object msg = InputClient.readObject();
                    System.out.println("ConnexionServeur " + id + ": bip ");
                    System.out.println("Message reçu: " + msg);
                } catch (IOException ex) {
                    Logger.getLogger(ConnexionClient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ConnexionClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

    }
}
