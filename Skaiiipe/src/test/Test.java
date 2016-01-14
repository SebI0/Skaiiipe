/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Elie
 */
public class Test {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket s2 = new ServerSocket();
        InetSocketAddress sa = new InetSocketAddress("localhost", 60000);
        s2.bind(sa);
        System.out.println("SOCKET READY");
        Socket s = s2.accept();

        System.out.println("UNE CONNEXION EST ARRIVÉE");
        InputStream is = s.getInputStream();
        ObjectInputStream InputClient = new ObjectInputStream(is);
        while (true) {
            Object msg = InputClient.readObject();
            System.out.println("Message reçu: " + msg);
        }
    }
}
