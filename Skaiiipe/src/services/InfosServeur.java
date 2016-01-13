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
 *
 * @author Elie
 */
public class InfosServeur implements Serializable {

    List<Salon> listServers;

    public InfosServeur() {
        listServers = new ArrayList<>();
    }

    public void add(Salon connexion) {
        listServers.add(connexion);
    }

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

    public void remove(Salon elt) {

        System.out.println("Liste: " + listServers);
        Iterator<Salon> iterator = listServers.iterator();
        int val=0;
        List<Salon> listTemp = new ArrayList<>();
        for(Salon salon : listServers){
            System.out.println("Comparaison: "+salon.getId()+" : "+elt.getId());
            if (salon.getId()!=elt.getId()) {
                listTemp.add(listServers.get(val));
            }
            val++;
        }
        System.out.println("Nouvelle liste: " + listServers.toString());
        listServers = listTemp;

    }

    public int size() {
        return listServers.size();
    }

    @Override
    public String toString() {
        String s = "";
        for(Salon serv : listServers) {
            s += "\n" + serv.toString();
        }
        return s;
    }

}
