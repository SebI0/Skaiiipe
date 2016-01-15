/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.Serializable;

/**
 * Objet permettant de communiquer des informations au serveurs/client
 *
 * @author Elie
 */
public class InfoServeur implements Serializable {

    String adresse;
    int port;
    String nom;

    /**
     * Constructeur de l'objet
     *
     * @param adresse String adresse du serveur
     * @param port Port d'écoute du serveur
     * @param nom Nom du serveur
     */
    public InfoServeur(String adresse, int port, String nom) {
        this.adresse = adresse;
        this.port = port;
        this.nom = nom;
    }

    /**
     * Réécriture de la fonction toString pour la mise en forme
     *
     * @return Retourne une chaine de caractères avec les informations de
     * l'objet
     */
    @Override
    public String toString() {
        return "Serveur : " + nom + "adresse= " + adresse + ", port=" + port;
    }

}
