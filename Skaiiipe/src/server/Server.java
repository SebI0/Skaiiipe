/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.Salon;
import services.InfosServeur;

/**
 *
 * @author Elie
 */
public class Server extends Thread implements Serializable{
    
    InfosServeur listServers;
    private ArrayList<Salon> salons;
    int id;
    public static int incre = 1000;
    public Server() {
        id= incre;
        incre++;
    }
    
    
    
    @Override
    public void run(){
        listServers = new InfosServeur();
        this.salons = new ArrayList<Salon>();
        try {
            ServerSocket s2 = new ServerSocket();
            InetSocketAddress sa = new InetSocketAddress("localhost", 60001);
            s2.bind(sa);
            System.out.println("SOCKET READY");
            while(!Thread.currentThread().isInterrupted()){
                Socket s = s2.accept();
                ConnexionServeur ServerConnexion = new ConnexionServeur(s,this);
                System.out.println("Server "+id+": Ajout d'une connexion Serveur");
                listServers.add(ServerConnexion.getInfos());
                System.out.println("Il y a actuellement: "+listServers.size()+" serveurs en ligne");
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
    }
    
    public void addSalon(Salon salon) {
        this.salons.add(salon);
    }
    
    
    
}
