/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.sun.webkit.ThemeClient;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.Message;

/**
 *
 * @author Elie
 */
public class ConnexionServeur extends Thread {
    
    Socket socketServer;
    int id;
    public static int incre = 0;
    private Server serv;
    public ConnexionServeur(java.net.Socket socketServer, Server s) {
        this.socketServer = socketServer;
        id = incre;
        incre++;
        this.serv = s ;
    }
    
    
    
    @Override 
    public void run(){
        InputStream is = null;
        try {
            is = socketServer.getInputStream();
            ObjectInputStream InputClient = new ObjectInputStream(is);
            OutputStream os = socketServer.getOutputStream();
            ObjectOutputStream outputClient = new ObjectOutputStream(os);
            
            while(!Thread.currentThread().isInterrupted()){
                
                while(true){
                    Object msg = InputClient.readObject();
                    System.out.println("ConnexionServeur "+id+": bip ");
                    System.out.println("Message reçu: "+msg);
                    Message m = (Message) msg;
                    switch(m.getType()){
                        case 11 :
                            System.out.println("Hello");
                            outputClient.writeObject(new Message(Message.LIST_SALONS, serv.getListServers()));
                        break;
                        default:
                            System.out.println("error");
                  }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ConnexionServeur.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnexionServeur.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(ConnexionServeur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
