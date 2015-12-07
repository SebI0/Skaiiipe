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
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.Message;

/**
 *
 * @author Elie
 */
public class ConnexionClient extends Thread{
    Socket socketServer;
    int id;
    public static int incre = 0;

    public ConnexionClient(java.net.Socket socketServer) {
        this.socketServer = socketServer;
        id = incre;
        incre++;
    }
   
}
