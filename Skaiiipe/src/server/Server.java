/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import modele.Salon;
import services.InfosServeur;

/**
 *
 * @author Elie
 */
public class Server extends Thread implements Serializable {

    InfosServeur listServers;
    private ArrayList<Salon> salons;
    int id;

    private InetAddress ip; //ip utilisée par le serveur de salons
    private int port; //port utilisé par le serveur de salons
    private JTree treeSalons; //affichage de la liste des salons

    public static int incre = 1000;

    public Server() {
        id = incre;
        incre++;
    }

    public Server(InetAddress ip, int port, JTree treeSalons) {
        this.ip = ip;
        this.port = port;
        this.treeSalons = treeSalons;
    }

    @Override
    public void run() {
        listServers = new InfosServeur();
        this.salons = new ArrayList<Salon>();

        try {
            ServerSocket s2 = new ServerSocket();

            InetSocketAddress sa = new InetSocketAddress(this.ip, this.port);
            s2.bind(sa);
            System.out.println("Serveur de salons crée !");
            System.out.println("IP : " + InetAddress.getLocalHost() + " port : " + this.port);
            updateListeSalons();
            while (!Thread.currentThread().isInterrupted()) {
                Socket s = s2.accept();
                ConnexionServeur ServerConnexion = new ConnexionServeur(s, this);
                System.out.println("Server " + id + ": Ajout d'une connexion Serveur");
                //listServers.add(ServerConnexion.getInfos());
                System.out.println("Il y a actuellement: " + listServers.size() + " serveurs en ligne");
                ServerConnexion.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Salon> getListServers() {
        return listServers.get();
    }

    public List<Salon> getSalons() {
        return salons;
    }

    public void setSalons(ArrayList<Salon> salons) {
        this.salons = salons;
        updateListeSalons();
    }

    public void addSalon(Salon salon) {
        this.salons.add(salon);
        updateListeSalons();
    }

    public void updateListeSalons() {
        System.out.println("__server__ UPDATE ");
        ArrayList<String> listeCategories = new ArrayList<>();
        DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Salons");

        if (this.listServers.get().size() == 0) {
            DefaultTreeModel dtm = new DefaultTreeModel(racine);
            DefaultMutableTreeNode salon = new DefaultMutableTreeNode("Aucun salon");
            racine.add(salon);
            this.treeSalons.setModel(dtm);
            System.out.println("Aucun salon");
        } else {
            for (Salon s : this.listServers.get()) {
                System.out.println(s);
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
