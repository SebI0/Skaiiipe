/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tppaint2014;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import modele.Salon;
import services.Message;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elie
 */
public class CommunicationClientHote {
    private ObjectOutputStream outputStream;
    private Socket connexionClient;
   
    public CommunicationClientHote(Socket s) throws IOException {
        connexionClient = s;
        outputStream = new ObjectOutputStream(s.getOutputStream());

    }
    
    
    public boolean sendForm(Forme F){
        Message m = new Message(Message.FORME, F);    
        try {
            outputStream.writeObject(m);
            System.out.println("Message envoyé");
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public boolean shutdown() {
        System.out.println("FERMETURE DU SALON");
        try {
            outputStream.writeObject(new Message(Message.FERMETURE_SALON, ""));
            System.out.println("Message envoyé");
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
    
    
}
