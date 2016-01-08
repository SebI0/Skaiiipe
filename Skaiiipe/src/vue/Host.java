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
import server.ConnexionClient;
import services.Message;

/**
 *
 * @author Elie
 */
public class Host extends Thread{
    
    private ArrayList<ConnexionClient>ListClient;
    private ArrayList<Salon> salons;
    int id;
    private int id_salon;
    public static int incre = 1000;
    
    private ServerSocket serveurSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    
    
    public Host(ServerSocket serverSocket, ObjectInputStream inputStr,ObjectOutputStream outputStr) {
        id= incre;
        incre++;
        this.inputStream = inputStr;
        this.outputStream = outputStr;
        this.serveurSocket = serverSocket;
    }

    public int getId_salon() {
        return id_salon;
    }

    public void setId_salon(int id_salon) {
        this.id_salon = id_salon;
    }
    
    
    
    @Override
    public void run(){
        //écouteur du client maitre
        try {
            ListClient = new ArrayList<>();
         //   ServerSocket s2 = new ServerSocket();
         //   InetSocketAddress sa = new InetSocketAddress("localhost", 60002);
         //   s2.bind(sa);
            System.out.println("SOCKET READY");
            while(!Thread.currentThread().isInterrupted()){
                Socket s = this.serveurSocket.accept();
                ConnexionClient ConnexionCli = new ConnexionClient(s);
                System.out.println("Client "+id+": Ajout d'un client");
                ListClient.add(ConnexionCli);
                outputStream.writeObject(new Message(Message.MAJ_SALON, "Seb"));
                System.out.println("Il y a actuellement: "+ListClient.size()+" users en ligne");
                ConnexionCli.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Host.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
