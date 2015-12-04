/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.Message;

/**
 *
 * @author Elie
 */
public class ConnexionClient extends Thread{
     Socket socketServer;
    int id;
    public static int incre = 0;

    public ConnexionClient(java.net.Socket socketServer) {
        this.socketServer = socketServer;
        id = incre;
        incre++;
    }
    
    
    
    @Override 
    public void run(){
        System.out.println("HELLO");
        InputStream is = null;
        try {
            is = socketServer.getInputStream();
            ObjectInputStream InputClient = new ObjectInputStream(is);
            OutputStream os = socketServer.getOutputStream();
            ObjectOutputStream outputClient = new ObjectOutputStream(os);
            System.out.println("Votre choix: ");
            while(!Thread.currentThread().isInterrupted()){
                Scanner scan = new Scanner(System.in);

                while(true){
                    outputClient.writeObject(new Message(Message.LIST_SALONS, scan.nextLine()));
                    Object msg = InputClient.readObject();
                    //System.out.println("ConnexionServeur "+id+": bip ");
                    //System.out.println("Message reçu: "+msg);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ConnexionServeur.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
             Logger.getLogger(ConnexionClient.class.getName()).log(Level.SEVERE, null, ex);
         } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(ConnexionServeur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
