/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;
import java.net.BindException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import modele.Salon;
import services.InfosServeur;

/**
 * Classe permettant la création d'un serveur principal
 *
 * @author Elie
 */
public class Server extends Thread implements Serializable {

    /**
     * @param listServer Objet du type InfosServeur permetant d'avoir des
     * informations d'un salon
     * @see InfosServeur
     * @param salons Liste de salon permetant de lister les salons
     * @see Salon
     * @param id Identifiant unique du serveur principal
     * @param ip Adresse Inet utilisé par le serveur
     * @param port Entier contentant le port d'écoute du salon
     * @param treeSalons Arbre contenant les salons pour l'affichage
     * @param listUser Liste d'utilisateur servant à l'affichage des
     * utilisateurs d'un salon
     * @param incre Valeur numérique servant à l'identification unique d'un
     * serveur
     */
    InfosServeur listServers;
    private ArrayList<Salon> salons;
    int id;
    private InetAddress ip; //ip utilisée par le serveur de salons
    private int port; //port utilisé par le serveur de salons
    private JTree treeSalons; //affichage de la liste des salons
    private JList listUsers;

    public static int incre = 1000;

        /**
     * Constructeur par défaut d'un serveur
     */
    public Server() {
        id = incre;
        incre++;
    }

       /**
     * Consctructeur d'un serveur
     *
     * @param ip AdresseInet du serveur
     * @param port Port d'écoute du salon
     * @param treeSalons Arbre contenant les salons pour l'affichage des
     * utilisateurs du salon
     * @param listUsers Liste d'utilisateur servant à l'affichage des
     * utilisateurs d'un salon
     */
    public Server(InetAddress ip, int port, JTree treeSalons, JList listUsers) {
        this.ip = ip;
        this.port = port;
        this.treeSalons = treeSalons;
        this.listUsers = listUsers;
    }

        /**
     * Réécriture du processus serveur
     */
    @Override
    public void run() {
                /**
         * Initialisation des variables de listing
         */
        listServers = new InfosServeur();
        this.salons = new ArrayList<Salon>();

        try {
                        //Initialisation socket d'écoute principale

            ServerSocket s2 = new ServerSocket();

            InetSocketAddress sa = new InetSocketAddress(this.ip, this.port);
            s2.bind(sa);
            System.out.println("__serveur__ Serveur de salons crée !");
            System.out.println("__serveur__ IP : " + InetAddress.getLocalHost() + " port : " + this.port);

            updateListeSalons();
            while (!Thread.currentThread().isInterrupted()) {
                 //Acceptation des connexions

                Socket s = s2.accept();
                ConnexionServeur ServerConnexion = new ConnexionServeur(s, this);
                System.out.println("__serveur__ Server " + id + ": Ajout d'une connexion Serveur");
                System.out.println("__serveur__ Il y a actuellement: " + listServers.size() + " serveurs en ligne");
                ServerConnexion.start();
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Ajout d'un utlisateur à un salon
     * @param id_salon Identifiant numérique d'un salon
     * @param pseudo Chaine de caractère
     */
    public void addUser(Integer id_salon, String pseudo) {

        for (Salon s : this.listServers.get()) {
            System.out.println(s.toFull());
            if (s.getId() == id_salon) {
                System.out.println("AJOUT DE USER : " + pseudo);
                s.addUser(pseudo);
            }
        }

 

    }

    /**
     * Retourne une lsite des salons actifs
     * @return SalonActifs Liste de salon
     */
    public List<Salon> getListServers() {
        List<Salon> SalonActifs = new ArrayList<>();
        for (Salon elt : listServers.get()) {
            System.out.println(elt.getNom() + ":" + elt.active);
            if (elt.active) {

                SalonActifs.add(elt);
            }
        }
        return SalonActifs;

    }
    /**
     * Retourne une lsite des salons actifs
     * @return SalonActifs Liste de salon
     */
    public List<Salon> getSalons() {
        List<Salon> SalonActifs = new ArrayList<>();
        for (Salon elt : salons) {
            System.out.println(elt.getNom() + ":" + elt.active);
            if (elt.active) {

                SalonActifs.add(elt);
            }
        }
        return SalonActifs;
    }

       /**
        * Initialisation de la liste de salons
        * @param salons Liste des Salons
        */
    public void setSalons(ArrayList<Salon> salons) {
        this.salons = salons;
        updateListeSalons();
    }

    /**
     * Ajouter un salon à la liste des salons
     * @param salon 
     */
    public void addSalon(Salon salon) {
        this.salons.add(salon);
        updateListeSalons();
    }

    /**
     * Mise à jour de la liste de salons
     */
    public void updateListeSalons() {
        System.out.println("__server__ UPDATE ");
        ArrayList<String> listeCategories = new ArrayList<>();
        DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Salons");

        if (this.listServers.get().isEmpty()) {
            DefaultTreeModel dtm = new DefaultTreeModel(racine);
            DefaultMutableTreeNode salon = new DefaultMutableTreeNode("Aucun salon");
            racine.add(salon);
            this.treeSalons.setModel(dtm);
            System.out.println("__serveur__ Aucun salon");
        } else {
            for (Salon s : this.listServers.get()) {
                DefaultComboBoxModel model = new DefaultComboBoxModel();
                System.out.println(s.getUsers());
                if (!s.getUsers().isEmpty()) {
                    for (String user : s.getUsers()) {
                        model.addElement(user);
                    }
                } else {
                    model.addElement("Aucun utilisateur");
                }
                this.listUsers.setModel(model);

                int pos = listeCategories.indexOf(s.getCatégorie());
                if (pos == -1) {
                    DefaultMutableTreeNode categorie = new DefaultMutableTreeNode(s.getCatégorie());
                    DefaultMutableTreeNode salon = new DefaultMutableTreeNode(s);
                    racine.add(categorie);
                    categorie.add(salon);
                    listeCategories.add(s.getCatégorie());
                } else {
                    DefaultMutableTreeNode salon = new DefaultMutableTreeNode(s);
                    DefaultMutableTreeNode categorie = (DefaultMutableTreeNode) racine.getChildAt(pos);
                    categorie.add(salon);
                }
            }
            DefaultTreeModel dtm = new DefaultTreeModel(racine);
            this.treeSalons.setModel(dtm);
        }
    }

}
