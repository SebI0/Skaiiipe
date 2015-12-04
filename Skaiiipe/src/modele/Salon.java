/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

/**
 *
 * @author Seb
 */
public class Salon {
    
    private String ip;
    private int port;
    private String nom;
    private String catégorie;
    private int nbUsers;

    public Salon(String ip, int port, String nom, String catégorie) {
        this.ip = ip;
        this.port = port;
        this.nom = nom;
        this.catégorie = catégorie;
        this.nbUsers = 1;
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

    @Override
    public String toString() {
        return nom;
    }
    
    
    
    
    
}
