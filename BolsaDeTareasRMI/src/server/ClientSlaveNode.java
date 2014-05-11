/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import interfaces.Queue;
import java.util.ArrayList;

/**
 *
 * @author Equipo
 */
public class ClientSlaveNode implements Queue {

    private Registry registry;

    @Override
    public ArrayList<String> obtenerMensajes() throws RemoteException{
        ArrayList<String> prueba = new ArrayList<String>();
        prueba.add("Me invocaste");
        return prueba;
    }

    @Override
    public void publicar(String publication, ArrayList<String> friends) throws RemoteException{
        System.out.println(publication);
    }

    ClientSlaveNode(Registry registry) throws RemoteException {
        super();
        this.registry = registry;
    }

    public void start() {

        try {
            ClientSlaveNode engine = new ClientSlaveNode(registry);

            Queue stub;
            stub = (Queue) UnicastRemoteObject.exportObject(engine, 0);
            registry.rebind("Queue", stub);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientSlaveNode.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
