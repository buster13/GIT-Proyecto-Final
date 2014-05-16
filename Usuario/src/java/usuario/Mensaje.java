/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package usuario;

import java.io.Serializable;

/**
 *
 * @author Equipo
 */
public class Mensaje implements Serializable{
    private String sender;
    private String message;
    private String type;
    
    public Mensaje(String username, String publication, String t){
        this.sender = username;
        this.message = publication;
        this.type=t;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }
    
}
