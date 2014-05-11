/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import interfaces.Bioinformatics;
import interfaces.DataMining;
import interfaces.ImageProcessing;
import interfaces.Task;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sdist
 */
public class SlaveNode implements Bioinformatics, DataMining, ImageProcessing{

    String entrada;
    Registry registry;
    
    @Override
    public Task executeBioTask(Task aTask) {
        try {
            Thread.sleep(aTask.getLength());
            //aTask.setOutput("Terminado");
        } catch (InterruptedException ex) {
            Logger.getLogger(SlaveNode.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aTask;
    }

    @Override
    public Task executeDataTask(Task aTask) {
        try {
            Thread.sleep(aTask.getLength());
            //aTask.setOutput("Terminado");
            
        } catch (InterruptedException ex) {
            Logger.getLogger(SlaveNode.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aTask;
    }

    @Override
    public Task executeImageTask(Task aTask) {
        try {
            Thread.sleep(aTask.getLength());
            //aTask.setOutput("Terminado");
        } catch (InterruptedException ex) {
            Logger.getLogger(SlaveNode.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aTask;
    }
    
    public SlaveNode(String ent, Registry reg) throws RemoteException{
            super();
 
            entrada = ent;
            registry = reg;
    }
    
    public void start(){
        try {
            SlaveNode engine = new SlaveNode(entrada, registry);
            if(entrada.equals("Bioinformatics")){
                Bioinformatics stub =  (Bioinformatics) UnicastRemoteObject.exportObject(engine, 0);
                registry.rebind(entrada, stub);
            }else{
                if(entrada.equals("DataMining")){
                    DataMining stub =  (DataMining) UnicastRemoteObject.exportObject(engine, 0);
                    registry.rebind(entrada, stub);
                }else{
                    ImageProcessing stub =  (ImageProcessing) UnicastRemoteObject.exportObject(engine, 0);
                    registry.rebind(entrada, stub);
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(SlaveNode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
