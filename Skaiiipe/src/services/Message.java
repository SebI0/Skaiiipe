/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.Serializable;

/**
 *
 * @author Elie
 */
public class Message implements Serializable{
    public static int FORME = 10;
    public static int LIST_SALONS = 11;
    public static int CREATION_SALON = 12;
    public static int DECONNEXION = -1;
    
    private Object data;
    
    private int type;
    
    public Message(int typ, Object Data){
        this.type = typ;
        this.data = Data;
    }

    public Object getData() {
        return data;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Message{" + "data=" + data + ", type=" + type + '}';
    }
    
    
    
}
