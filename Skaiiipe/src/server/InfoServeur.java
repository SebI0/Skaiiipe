/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.Serializable;

/**
 *
 * @author Elie
 */
public class InfoServeur implements Serializable {

    String adresse;
    int port;
    String nom;

    public InfoServeur(String adresse, int port, String nom) {
        this.adresse = adresse;
        this.port = port;
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Serveur : " + nom + "adresse= " + adresse + ", port=" + port;
    }

}
