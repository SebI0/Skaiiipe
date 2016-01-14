/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.Serializable;

/**
 *
 * @author Seb
 */
public class User implements Serializable {

    private Integer id_salon;
    private String pseudo;

    public User(Integer id_salon, String pseudo) {
        this.id_salon = id_salon;
        this.pseudo = pseudo;
    }

    public Integer getId_salon() {
        return id_salon;
    }

    public void setId_salon(Integer id_salon) {
        this.id_salon = id_salon;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

}
