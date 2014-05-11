/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sdist
 */
public class Starter {
    
    public static void main(String[] args){
        System.setProperty("java.security.policy","C:\\Users\\Equipo\\Documents\\NetBeansProjects\\BolsaDeTareasRMI\\src\\server\\server.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            LocateRegistry.createRegistry(1099);
            Registry registry = LocateRegistry.getRegistry();
            
            SlaveNode nodo1 = new SlaveNode("Bioinformatics", registry);
            SlaveNode nodo2 = new SlaveNode("DataMining", registry);
            SlaveNode nodo3 = new SlaveNode("ImageProcessing", registry);
            nodo1.start();
            nodo2.start();
            nodo3.start();
            
        } catch (RemoteException ex) {
            Logger.getLogger(Starter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
