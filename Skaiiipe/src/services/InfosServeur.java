/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import server.ConnexionServeur;
import server.InfoServeur;

/**
 *
 * @author Elie
 */
public class InfosServeur implements Serializable{
    List<InfoServeur> listServers;

    public InfosServeur() {
        listServers = new ArrayList<>();
    }
    
    public void add(InfoServeur connexion){
        listServers.add(connexion);
    }
    
    public List<InfoServeur> get(){
        return listServers;
    }
    
    public int size(){
        return listServers.size();
    }
}
