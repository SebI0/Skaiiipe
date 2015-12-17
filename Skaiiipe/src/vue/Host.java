/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.Salon;
import server.ConnexionClient;
import services.InfosServeur;

/**
 *
 * @author Elie
 */
public class Host extends Thread{
    
    private ArrayList<ConnexionClient>ListClient;
    private ArrayList<Salon> salons;
    int id;
    public static int incre = 1000;
    public Host() {
        id= incre;
        incre++;
    }
    
    @Override
    public void run(){
        try {
            ListClient = new ArrayList<>();
            ServerSocket s2 = new ServerSocket();
            InetSocketAddress sa = new InetSocketAddress("localhost", 60002);
            s2.bind(sa);
            System.out.println("SOCKET READY");
            while(!Thread.currentThread().isInterrupted()){
                Socket s = s2.accept();
                ConnexionClient ConnexionCli = new ConnexionClient(s);
                System.out.println("Client "+id+": Ajout d'un client");
                ListClient.add(ConnexionCli);
//listServers.add(ServerConnexion.getInfos());
                System.out.println("Il y a actuellement: "+ListClient.size()+" users en ligne");
                ConnexionCli.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Host.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
