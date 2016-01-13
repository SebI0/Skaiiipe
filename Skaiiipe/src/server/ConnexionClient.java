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
import tppaint2014.Fenetre;
import tppaint2014.Forme;
import vue.Host;

/**
 *
 * @author Elie
 */
public class ConnexionClient extends Thread {

    Socket socketServer;
    int id;
    public static int incre = 0;
    private ObjectInputStream InputClient;
    private Fenetre fen;
    private Host hote;
    
    
    public Socket getSocket() {

        return socketServer;

    }

    public void SetFenetre(Fenetre f) {
        this.fen = f;
        System.out.println("Fenetre initialisée");

    }

    public ConnexionClient(java.net.Socket socketServer, Fenetre f,Host hote) throws IOException {
        this.hote = hote;
        this.fen = f;
        this.socketServer = socketServer;
        id = incre;
        incre++;
        System.out.println("Init Input");
        System.out.println("Input initialisé");
    }

    @Override
    public void run() {
        System.out.println("run");
        try {
            //écouteur du client maitre
            InputClient = new ObjectInputStream(socketServer.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ConnexionClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Inputstream localisé");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.println("Waiting pour un message");
                Message msg = (Message) InputClient.readObject();
                System.out.println("ConnexionServeur " + id + ": bip ");
                System.out.println("Message reçu: " + msg.getData().toString());
                Forme receivedForme = (Forme) msg.getData();
                switch (msg.getType()) {
                    case Message.FORME:
                        
                        System.out.println(fen);
                        fen.lesFormes.add(receivedForme);
                        fen.zg.repaint();
                        break;
                    case Message.FERMETURE_SALON:
                        hote.outputStream.writeObject(new Message(Message.FERMETURE_SALON, hote.getId_salon()));
                        break;
                        
                    case Message.GOMME:
                        fen.lesFormes.add(receivedForme);
                        fen.zg.repaint();
                        System.out.println(receivedForme);
                        break;
                }

                //     fen.zg.paint(fen.zg.getGraphics());
            } catch (IOException ex) {
                Logger.getLogger(ConnexionClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnexionClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
