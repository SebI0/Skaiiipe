/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import modele.Salon;


/**
 *
 * @author Elie
 */
public class InfosServeur implements Serializable{
    List<Salon> listServers;

    public InfosServeur() {
        listServers = new ArrayList<>();
    }
    
    public void add(Salon connexion){
        listServers.add(connexion);
    }
    
    public List<Salon> get(){
        return listServers;
    }
    
    public int size(){
        return listServers.size();
    }

    @Override
    public String toString() {
        String s="";
        for(Salon serv: listServers)
            s+="\n"+serv.toString();
        return s;
    }
    
}
