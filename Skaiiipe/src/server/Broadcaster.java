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
 * Classe du thread permetant d'écouter les messages du client vers l'hôte et de
 * les diffuser par la suite
 *
 * @author Elie
 */
public class Broadcaster extends Thread {

    /**
     * @param socketServer Socket liant le client et le serveur
     * @param hote Lien vers le processus père de l'hôte
     * @see Host
     * @param InputClient Flux de lecture des données du client vers l'hôte
     * @param InputClient Flux d'écriture des données du client vers l'hôte
     */
    private Socket socketServer;
    private Host hote;
    private ObjectInputStream InputClient;
    private ObjectOutputStream OutputClient;

    /**
     * Constructeur d'un processus Broadcaster
     *
     * @param socketServer liant le client et le serveur
     * @param hote Lien vers le processus père de l'hôte
     */
    public Broadcaster(Socket socketServer, Host hote) {
        this.socketServer = socketServer;
        this.hote = hote;
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
     * Assigner une valeur de socket entre hôte/client
     *
     * @param socketServer Socket entre client/serveur
     */
    public void setSocket(Socket socketServer) {
        this.socketServer = socketServer;
    }

    /**
     * Retourne le processus Hote
     *
     * @return hote
     * @see Host
     */
    public Host getHote() {
        return hote;
    }

    /**
     * Assigner a un processus un processus hôte
     *
     * @param hote
     * @see Host
     */
    public void setHote(Host hote) {
        this.hote = hote;
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
     * Retourne le flux d'écriture de données
     *
     * @param InputClient Flux d'écriture des données du client vers l'hôte
     */
    public ObjectOutputStream getOutputClient() {
        return OutputClient;
    }

    /**
     * Permet de diffuser un message à tout les utilisateurs connectés à un
     * salon.
     *
     * @param c Broadcast permetant d'identifer l'émetteur du message afin de ne
     * pas le lui renvoyer l'information
     * @param m Message que l'on souhaite diffuser
     * @see Message
     * @throws IOException
     */
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

    /**
     * Définition du thread qui est executé coté serveur pour chaque client qui
     * se connecte. Chaque client possède son thread qui permet au serveur
     * d'écouter le client. Lors de son execution, on initialiser les différents
     * flux d'entrée/sortie et nous renvoyons à l'utilisateur les formes déjà
     * tracées
     */
    @Override
    public void run() {
        try {
            //Initialisation écouteur du client maitre
            InputClient = new ObjectInputStream(socketServer.getInputStream());
            OutputClient = new ObjectOutputStream(socketServer.getOutputStream());
            //Renvoie des formes que l'on va chercher dans la fenêtre de l'hôte
            for (Forme fe : hote.getFen().lesFormes) {
                OutputClient.writeObject(new Message(Message.FORME, fe));
            }
        } catch (IOException ex) {
            Logger.getLogger(ConnexionClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Écoute du réseau
        while (!Thread.currentThread().isInterrupted()) {
            try {
                //En attente d'un message
                Message msg = (Message) InputClient.readObject();
                //Diffusion du message
                broadcastAllUser(this, msg);
                //Si le message est un message de fermeture de salon, on désactive l'hote.
                if (msg.getType() == Message.FERMETURE_SALON) {
                    hote.setActive(false);
                }
                //Gestion des erreurs
            } catch (IOException ex) {
                Logger.getLogger(ConnexionClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnexionClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
