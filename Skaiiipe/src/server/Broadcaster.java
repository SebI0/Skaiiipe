/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.Message;
import tppaint2014.Forme;
import vue.Host;

/**
 *
 * @author Elie
 */
public class Broadcaster extends Thread {

    private Socket socketServer;
    private Host hote;
    private List<Broadcaster> ListClient;
    private ObjectInputStream InputClient;
    private ObjectOutputStream OutputClient;

    public Broadcaster(Socket socketServer, Host hote) {
        this.socketServer = socketServer;
        this.hote = hote;
    }

    public Socket getSocket() {
        return socketServer;
    }

    public void setSocket(Socket socketServer) {
        this.socketServer = socketServer;
    }

    public Host getHote() {
        return hote;
    }

    public void setHote(Host hote) {
        this.hote = hote;
    }

    public List<Broadcaster> getListClient() {
        return ListClient;
    }

    public void setListClient(List<Broadcaster> ListClient) {
        this.ListClient = ListClient;
    }

    public ObjectInputStream getInputClient() {
        return InputClient;
    }

    public ObjectOutputStream getOutputClient() {
        return OutputClient;
    }

    public void broadcastAllUser(Broadcaster c, Message m) throws IOException {
        System.out.println("HelloBroadcast");
        int incre = 0;
        for (Broadcaster client : hote.getListClient()) {
            incre++;
            if (!client.equals(c)) {
                client.getOutputClient().writeObject(m);
            }
        }
        System.out.println("Broadcasté :" + incre);

    }

    @Override
    public void run() {
        try {
            //écouteur du client maitre
            InputClient = new ObjectInputStream(socketServer.getInputStream());
            OutputClient = new ObjectOutputStream(socketServer.getOutputStream());
            for (Forme fe : hote.getFen().lesFormes) {
                OutputClient.writeObject(new Message(Message.FORME, fe));
            }
        } catch (IOException ex) {
            Logger.getLogger(ConnexionClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message msg = (Message) InputClient.readObject();
                
                    broadcastAllUser(this, msg);
                if(msg.getType()==Message.FERMETURE_SALON)
                      hote.setActive(false);

            } catch (IOException ex) {
                Logger.getLogger(ConnexionClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnexionClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
