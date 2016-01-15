/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.Serializable;

/**
 * Objet message utilisé pour transmetre des informations 
 * @author Elie
 */
public class Message implements Serializable {

    /**
     * Définition de variables statiques
     */
    public static final int FORME = 10;
    public static final int LIST_SALONS = 11;
    public static final int CREATION_SALON = 12;
    public static final int MAJ_SALON = 13;
    public static final int FERMETURE_SALON = 14;
    public static final int DECONNEXION = 15;
    public static final int GOMME = 16;
    public static final int EFFACER = 17;
    public static final int EFFACER_TOUT = 18;
    public static final int EFFACER_FAM = 19;
    public static final int EFFACER_COULEUR = 20;
    public static final int PSEUDO = 21;
    public static final int ERROR = -999;
    public static final int INIT = 0;


    private Object data;

    private int type;

    /**
     * Constructeur d'un message à transmettre
     * @param typ Entier désignant le type de message que l'on souhaite envoyer
     * @param Data Données que l'on souhaite envoyer
     */
    public Message(int typ, Object Data) {
        this.type = typ;
        this.data = Data;
    }

    /**
     * Méthode qui retourne les données d'un messages
     * @return data Objet transmis
     */
    public Object getData() {
        return data;
    }

    /**
     * Méthode qui retourne le type d'un message
     * @return type entier qui désigne le type de message
     */
    public int getType() {
        return type;
    }
    
    /**
     * Retourne le message en chaine de charactères
     * @return chaine de caractères
     */

    @Override
    public String toString() {
        return "Message{" + "data=" + data + ", type=" + type + '}';
    }

}
