/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.Salon;
import server.Broadcaster;
import server.ConnexionClient;
import services.Message;
import tppaint2014.Fenetre;
import tppaint2014.Forme;

/**
 * Thread permettant aux salons d'écouter les connexions clients.
 *
 * @author Elie
 */
public class Host extends Thread {

    /**
     * @param ListClient Liste de ConnexionClient, pour gerer les différents
     * utilisateurs connectés
     * @see ConnexionClient
     * @param id_salon Identifiant numérique du salon
     * @param incre Valeur numérique servant à l'attribution des identifiants
     * @param serveurSocket Socket d'écoute du serveur principal
     * @param inputStream Flux de lecture du serveur principal vers l'hote
     * @param outputStream Flux d'écriture de l'hote vers serveur princpal
     * @param f Fenetre de peinture associé au client qui héberge le salon.
     * @see Fenetre
     */
    private ArrayList<Broadcaster> ListClient;
    int id;
    private int id_salon;
    public static int incre = 1000;
    private ServerSocket serveurSocket;
    private ObjectInputStream inputStream;
    public ObjectOutputStream outputStream;
    private Fenetre f;

    public ArrayList<Broadcaster> getListClient() {
        return ListClient;
    }

    /**
     * Constructeur d'un Hôte
     *
     * @param serverSocket Socket d'écoute du serveur principal
     * @param inputStr Flux de lecture du serveur principal vers l'hote
     * @param outputStr Flux d'écriture de l'hote vers serveur princpal
     * @param f Passage de la fenêtre associé au client hôte
     */
    public Host(ServerSocket serverSocket, ObjectInputStream inputStr, ObjectOutputStream outputStr, Fenetre f) {
        id = incre;
        incre++;
        this.inputStream = inputStr;
        this.outputStream = outputStr;
        this.serveurSocket = serverSocket;
        this.f = f;
    }

    public Fenetre getFen() {
        return f;
    }

    
    /**
     * Getter de l'id du salon associé à l'hôte
     *
     * @return id_salon identifiant numérique du salon
     */
    public int getId_salon() {
        return id_salon;
    }

    /**
     * Setter de l'id du salon associé à l'hôte
     *
     * @param id_salon identifiant numérique du salon que l'on souhaite
     * attribuer
     */
    public void setId_salon(int id_salon) {
        this.id_salon = id_salon;
    }

    /**
     * Définition du fonctionnement du thread. On commence par créer une liste
     * dans laquelle on y mettra les différents clients. Ensuite, on se met à
     * l'écoute du réseau pour détecter lorsqu'un client souhaite se connecter
     * au salon, dès lors qu'il y a une connexion on l'enregistre, on lui
     * attribue un thread et on l'ajoute dans la liste des utilisateurs.
     */
    @Override
    public void run() {
        //écouteur du client maitre
        try {
            //Création d'une liste
            ListClient = new ArrayList<>();

            while (!Thread.currentThread().isInterrupted()) {
                Socket s = this.serveurSocket.accept();
                Broadcaster ConnexionCli = new Broadcaster(s, this);
                ListClient.add(ConnexionCli);
                outputStream.writeObject(new Message(Message.MAJ_SALON, "Seb"));
                ConnexionCli.start();

            }
        } catch (IOException ex) {
            Logger.getLogger(Host.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /* public void broadcastAllUser(ConnexionClient c,Message m) throws IOException{
     System.out.println("HelloBroadcast");
     int incre=0;
     for(ConnexionClient client : ListClient){
     incre++;
     if(!client.equals(c)){
     ObjectOutputStream outputClient = new ObjectOutputStream(client.getSocket().getOutputStream());
     outputClient.writeObject(m);
                
     }
     }
     System.out.println("Broadcasté :"+incre);
        
     }*/
}
