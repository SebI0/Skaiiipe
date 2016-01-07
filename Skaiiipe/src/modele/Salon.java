/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Seb
 */
public class Salon implements Serializable{
    
    private String ip;
    private int port;
    private String nom;
    private String catégorie;
    private int nbUsers;
    private ArrayList<String> users;

    public Salon(String ip, int port, String nom, String catégorie) {
        this.ip = ip;
        this.port = port;
        this.nom = nom;
        this.catégorie = catégorie;
        this.nbUsers = 1;
        this.users = new ArrayList<String>();
    }
    
    

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCatégorie() {
        return catégorie;
    }

    public void setCatégorie(String catégorie) {
        this.catégorie = catégorie;
    }

    public int getNbUsers() {
        return nbUsers;
    }

    public void setNbUsers(int nbUsers) {
        this.nbUsers = nbUsers;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public void addUser(String username)
    {
        this.users.add(username);
        
    }
    @Override
    public String toString() {
        return nom;
    }
    
    
    
    
    
}
