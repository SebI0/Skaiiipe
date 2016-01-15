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
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.Salon;
import modele.User;
import services.Message;

/**
 * Thread permettant la connexion au serveur principal coté serveur
 *
 * @author Elie
 */
public class ConnexionServeur extends Thread implements Serializable {

    /**
     * @param Socket Socket entre client et serveur
     * @param id Identifiant numérique du client
     * @param incre Valeur numérique permettant l'attribution unique d'un
     * identifiant
     * @param Server Lien vers l'objet serveur vers lequel le client est lié.
     * @see Server
     * @param info Objet Salon contenant les des informations d'un salon
     * @see info
     */
    Socket socketServer;
    int id;
    public static int incre = 0;
    private Server serv;
    private Salon info;
    private boolean estHote;

    /**
     * Constructeur de l'objet ConnexionServeur
     *
     * @param socketServer Socket entre serveur et client
     * @param s Objet Serveur identifiant le serveur qui à créé le processus
     */
    public ConnexionServeur(java.net.Socket socketServer, Server s) {
        this.socketServer = socketServer;
        id = incre;
        incre++;
        this.serv = s;
        estHote = false;
    }

    /**
     * Permet de récuperer les informations d'un salon
     *
     * @return info objet Salon
     * @see Salon
     */
    public Salon getInfos() {
        return info;
    }

    /**
     * Définition du thread qui est executé coté serveur pour chaque client qui
     * se connecte. Chaque client possède son thread qui permet au serveur
     * d'écouter le client. Lors de son execution, on initialiser les différents
     * flux d'entrée/sortie, on lui envoie la liste des différents salons
     * disponibles et nous sommes à l'écoute du choix de l'utilisateur
     */
    @Override
    public void run() {

        InputStream is = null;
        try {
            //Initialisation des flux
            is = socketServer.getInputStream();
            ObjectInputStream InputClient = new ObjectInputStream(is);
            OutputStream os = socketServer.getOutputStream();
            ObjectOutputStream outputClient = new ObjectOutputStream(os);

            while (!Thread.currentThread().isInterrupted()) {
                //Si le client n'héberge pas de salon
                if (!estHote) {
                    while (!Thread.currentThread().isInterrupted() && !estHote) {
                        Object msg = InputClient.readObject();
                        //On écoute le client
                        System.out.println("ConnexionServeur " + id + ": bip ");
                        System.out.println("Message reçu: " + msg);
                        Message m = (Message) msg;
                        //Gestion des actions
                        switch (m.getType()) {
                            case Message.PSEUDO:
                                User u = (User) m.getData();
                                this.serv.addUser(u.getId_salon(), u.getPseudo());
                                this.serv.updateListeSalons();
                                break;
                            case 0:
                                System.out.println("Initialisation");
                                switch (Integer.parseInt(m.getData().toString())) {
                                    case 1:
                                        System.out.println("L'utilisateur veut rejoindre un salon");
                                        outputClient.writeObject(new Message(Message.LIST_SALONS, "test"));
                                        this.serv.updateListeSalons();
                                        break;
                                    case 0:
                                        System.out.println("L'utilisateur veut créer un salon");
                                        outputClient.writeObject(new Message(Message.CREATION_SALON, "id"));
                                        this.serv.updateListeSalons();
                                        break;
                                }
                                break;
                            case 1:
                                System.out.println("Hello");
                                for (Salon connex : serv.getListServers()) {
                                    outputClient.writeObject(new Message(Message.LIST_SALONS, connex));
                                }
                                break;
                            case 2:
                                System.out.println("Hello");
                                for (Salon connex : serv.getListServers()) {
                                    outputClient.writeObject(new Message(Message.LIST_SALONS, connex));
                                }
                                break;
                            //L'utilisateur souhaite avoir les salons

                            case Message.LIST_SALONS:
                                System.out.println("__server__ envoi liste salons");
                                // for(Salon connex : serv.getListServers())
                                System.out.println("__server__ " + this.serv.getSalons());
                                outputClient.writeObject(new Message(Message.LIST_SALONS, this.serv.listServers.get()));
                                break;
                            //L'utilisateur souhaite héberger son salon.
                            //On récupère donc les différentes valeurs de création du salon

                            case Message.CREATION_SALON:

                                this.estHote = true;
                                serv.listServers.add((Salon) m.getData());
                                info = (Salon) m.getData();
                                break;
                            //Gestion des erreurs

                            default:
                                System.out.println("error");
                                outputClient.writeObject(new Message(Message.ERROR, null));
                                break;
                        }
                    }
                } //Si le client est un hote
                else {
                    //Renvoie d'une confirmation de création du salon avec son identifiant

                    outputClient.writeObject(new Message(Message.CREATION_SALON, id));
                    //Tant que le client héberge un salon
                    while (!Thread.currentThread().isInterrupted() && estHote) {
                        //On se met à l'écoute du salon

                        Message msg = (Message) InputClient.readObject();
                        switch (msg.getType()) {
                            //Si le salon a une MAJ

                            case Message.MAJ_SALON:
                                System.out.println(msg.getData());
                                info.addUser((String) msg.getData());
                                System.out.println("UPDATE");
                                this.serv.updateListeSalons();
                                break;
                            //Si le salon ne souhaite plus herberger

                            case Message.FERMETURE_SALON:
                                estHote = false;
                                System.out.println(info);
                                System.out.println("Salon fermé!");
                                this.serv.listServers.remove(info);
                                this.serv.updateListeSalons();
                                break;
                        }
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
