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
import services.Message;

/**
 *
 * @author Elie
 */
public class ConnexionServeur extends Thread implements Serializable {

    Socket socketServer;
    int id;
    public static int incre = 0;
    private Server serv;
    private Salon info;
    private boolean estHote;

    public ConnexionServeur(java.net.Socket socketServer, Server s) {
        this.socketServer = socketServer;
        id = incre;
        incre++;
        this.serv = s;
      //  info = new Salon(socketServer.getInetAddress().toString(), socketServer.getPort(), "Salon "+id, "cate");
        estHote = false;
    }

    public Salon getInfos() {
        return info;
    }

    @Override
    public void run() {

        InputStream is = null;
        try {
            is = socketServer.getInputStream();
            ObjectInputStream InputClient = new ObjectInputStream(is);
            OutputStream os = socketServer.getOutputStream();
            ObjectOutputStream outputClient = new ObjectOutputStream(os);

            while (!Thread.currentThread().isInterrupted()) {
                if (!estHote) {
                    //for(InfoServeur connex : serv.getListServers())
                    //outputClient.writeObject(new Message(Message.LIST_SALONS, serv.getListServers()));  
                    while (!Thread.currentThread().isInterrupted() && !estHote) {
                        Object msg = InputClient.readObject();
                        System.out.println("ConnexionServeur " + id + ": bip ");
                        System.out.println("Message reçu: " + msg);
                        Message m = (Message) msg;
                        switch (m.getType()) {
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

                            case Message.LIST_SALONS:
                                 System.out.println("__server__ envoi liste salons");
                                // for(Salon connex : serv.getListServers())
                                System.out.println("__server__ " + this.serv.getSalons());
                                outputClient.writeObject(new Message(Message.LIST_SALONS, this.serv.listServers.get()));
                                break;
                            case Message.CREATION_SALON:
                                
                                this.estHote = true;
                                serv.listServers.add((Salon) m.getData());
                                info=(Salon) m.getData();
                                break;
                            default:
                                System.out.println("error");
                                outputClient.writeObject(new Message(Message.ERROR, null));
                                break;
                        }
                    }
                } else {
                    outputClient.writeObject(new Message(Message.CREATION_SALON, id));
                    //Tant que le client héberge un salon
                    while (!Thread.currentThread().isInterrupted() && estHote) {
                        Message msg = (Message) InputClient.readObject();
                        switch (msg.getType()) {
                            case Message.MAJ_SALON:
                              //  info.setNbUsers((int) msg.getData());
                                
                                System.out.println(msg.getData());
                                info.addUser((String) msg.getData());
                                System.out.println("UPDATE");
                                this.serv.updateListeSalons();
                                break;
                            case Message.FERMETURE_SALON:
                                estHote = false;
                                System.out.println(info);
                                System.out.println("Salon fermé!");
 //                               info.active=false;
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
