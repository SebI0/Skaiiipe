/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import modele.Salon;

/**
 * Classe d'objet nécessaire au transfert des informations des salons
 *
 * @author Elie
 */
public class InfosServeur implements Serializable {

    List<Salon> listServers;

    /**
     * Constructeur d'un objet InfosServeur
     */
    public InfosServeur() {
        listServers = new ArrayList<>();
    }

    /**
     * Ajouter un salon à la liste des salons
     *
     * @param connexion Salon que l'on souhaite ajouter
     * @see Salon
     */
    public void add(Salon connexion) {
        listServers.add(connexion);
    }

    /**
     * Retourne la listes des salons
     *
     * @return SalonActifs Liste de Salon
     * @see Salon
     */
    public List<Salon> get() {
        List<Salon> SalonActifs = new ArrayList<>();
        for (Salon elt : listServers) {
            System.out.println(elt.getNom() + ":" + elt.active);
            if (elt.active) {
                SalonActifs.add(elt);
            }
        }
        return SalonActifs;
    }

    /**
     * Supprimer un salon de la liste des salons
     *
     * @param elt Salon
     * @see Salon
     */
    public void remove(Salon elt) {

        System.out.println("Liste: " + listServers);
        Iterator<Salon> iterator = listServers.iterator();
        int val = 0;
        List<Salon> listTemp = new ArrayList<>();
        for (Salon salon : listServers) {
            System.out.println("Comparaison: " + salon.getId() + " : " + elt.getId());
            if (salon.getId() != elt.getId()) {
                listTemp.add(listServers.get(val));
            }
            val++;
        }
        System.out.println("Nouvelle liste: " + listServers.toString());
        listServers = listTemp;

    }

    /**
     * Retourne le nombre de salons
     *
     * @return Valeur numérique
     */
    public int size() {
        return listServers.size();
    }

    /**
     * Retourne leses informations des salons sous formes de caractères
     *
     * @return chaine de caractère
     */
    @Override
    public String toString() {
        String s = "";
        for (Salon serv : listServers) {
            s += "\n" + serv.toString();
        }
        return s;
    }

}
