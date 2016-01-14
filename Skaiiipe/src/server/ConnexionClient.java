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
import javax.swing.JOptionPane;

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
    private String username;

    public ObjectInputStream getInputClient() {
        return InputClient;
    }

    public Socket getSocket() {

        return socketServer;

    }

    public void SetFenetre(Fenetre f) {
        this.fen = f;
        System.out.println("Fenetre initialisée");

    }

    public ConnexionClient(java.net.Socket socketServer, Fenetre f) throws IOException {
      //  this.hote = hote;
        this.fen = f;
        this.socketServer = socketServer;
        this.id = incre;
        this.incre++;
        this.username = username;

    }

    @Override
    public void run() {

        try {
            //écouteur du client maitre
            InputClient = new ObjectInputStream(socketServer.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ConnexionClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message msg = (Message) InputClient.readObject();
                Forme receivedForme;
                switch (msg.getType()) {
                    case Message.FORME:
                         receivedForme = (Forme) msg.getData();
                        System.out.println(fen);
                        fen.lesFormes.add(receivedForme);
                        fen.zg.repaint();

                        System.out.println("Une forme est reçue");
                        break;
                    case Message.FERMETURE_SALON:
                        JOptionPane.showMessageDialog(null, "L'hôte s'est déconnecté");
                        fen.dispose();
                        break;

                    case Message.GOMME:
                        receivedForme = (Forme) msg.getData();
                        fen.lesFormes.add(receivedForme);
                        fen.zg.repaint();
                        System.out.println(receivedForme);
                        break;
                }

            } catch (IOException ex) {
                Logger.getLogger(ConnexionClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnexionClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
