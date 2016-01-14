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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import services.Message;
import tppaint2014.Fenetre;
import tppaint2014.Forme;
import vue.Host;

/**
 * Processus coté client en écoute de l'hote pour receptionner ses messages
 *
 * @author Elie
 */
public class ConnexionClient extends Thread {

    /**
     * @param socketServer Socket de dialogue avec l'hôte
     * @param id Identifiant numérique du salon
     * @param InputClient Flux de lecture des données du client vers l'hôte
     * @param fen Lien d'accès à la fenetre du client.
     * @param username Nom d'utilisateur du client.
     */
    Socket socketServer;
    int id;
    public static int incre = 0;
    private ObjectInputStream InputClient;
    private Fenetre fen;
    private String username;

    /**
     * Constructeur du thread
     *
     * @param socketServer Socket entre hote et client
     * @param f Fenetre de l'utilisateur
     * @throws IOException
     */
    public ConnexionClient(java.net.Socket socketServer, Fenetre f) throws IOException {
        this.fen = f;
        this.socketServer = socketServer;
        this.id = incre;
        this.incre++;
        this.username = username;

    }

    /**
     * Retourne le flux de lecture de données
     *
     * @return InputClient Flux de lecture des données du client vers l'hôte
     */
    public ObjectInputStream getInputClient() {
        return InputClient;
    }

    /**
     * Retourne la socket entre hôte/client
     *
     * @return socketServer Socket entre client/serveur
     */
    public Socket getSocket() {

        return socketServer;

    }

    /**
     * Permet d'assigner a ce thread la fenetre de l'utilisateur
     *
     * @param f Fenetre de l'utilisateur
     */
    public void SetFenetre(Fenetre f) {
        this.fen = f;
        System.out.println("Fenetre initialisée");

    }

    /**
     * Définition du thread qui est executé coté client lors de la connexion à
     * un hote. Ce thread est à l'écoute de l'hôte lorsqu'il souhaite lui
     * envoyer un message et l'interprète
     */
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
                //En attente d'un message
                Message msg = (Message) InputClient.readObject();
                Forme receivedForme;
                //Interprétation du message
                switch (msg.getType()) {
                    //Si le message est une forme il faut l'afficher
                    case Message.FORME:
                        receivedForme = (Forme) msg.getData();
                        System.out.println(fen);
                        fen.lesFormes.add(receivedForme);
                        fen.zg.repaint();

                        System.out.println("Une forme est reçue");
                        break;

                    case Message.GOMME:
                        receivedForme = (Forme) msg.getData();
                        fen.lesFormes.add(receivedForme);
                        fen.zg.repaint();
                        System.out.println(receivedForme);
                        break;

                    //Si le message est une annonce de fermeture du salon, on prévient
                    //l'utilisateur de sa déconnexion
                    case Message.FERMETURE_SALON:
                        JOptionPane.showMessageDialog(null, "L'hôte s'est déconnecté");
                        fen.dispose();
                        break;

                    //Diverses action lié à l'affichage du salon
                    case Message.EFFACER:
                        fen.bh.EffacerLast(false);
                        break;
                    case Message.EFFACER_TOUT:
                        fen.bh.EffacerTout(false);
                        break;
                    case Message.EFFACER_FAM:
                        fen.bh.EffacerFamille(false);
                        break;
                    case Message.EFFACER_COULEUR:
                        fen.bh.EffacerCouleur(false);
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
