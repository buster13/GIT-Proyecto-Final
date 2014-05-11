/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import interfaces.Bioinformatics;
import interfaces.DataMining;
import interfaces.ImageProcessing;
import interfaces.Task;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sdist
 */
public class MasterNode {
    
    public static void main(String[] args) throws NotBoundException{
        System.setProperty("java.security.policy","C:\\Users\\BusterD\\Dropbox\\ITAM\\X Semestre\\Sistemas Distribuidos\\GIT Proyecto Final\\BolsaDeTareasRMI\\src\\client\\client.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        Registry registry; 
        try {
            registry = LocateRegistry.getRegistry("192.168.0.25"); // server's ip address
            
            Task[] BoTIP = {

                new Task("T1", "ImageProcessing", (long)5000),
                new Task("T2", "ImageProcessing", (long)10000), 
                new Task("T3", "ImageProcessing", (long)15000),
                new Task("T4", "ImageProcessing", (long)20000),
                new Task("T5", "ImageProcessing", (long)30000),
                new Task("T6", "ImageProcessing", (long)5000),
                new Task("T7", "ImageProcessing", (long)10000),
                new Task("T8", "ImageProcessing", (long)15000),
                new Task("T9", "ImageProcessing", (long)20000),
                new Task("T10", "ImageProcessing", (long)30000)
            };
            Task[] BoTDM = {
                new Task("T11", "DataMining", (long)5000),
                new Task("T12", "DataMining", (long)10000),
                new Task("T13", "DataMining", (long)15000),
                new Task("T14", "DataMining", (long)20000), 
                new Task("T15", "DataMining", (long)30000),
                new Task("T16", "DataMining", (long)5000),
                new Task("T17", "DataMining", (long)10000),
                new Task("T18", "DataMining", (long)15000),
                new Task("T19", "DataMining", (long)20000),
                new Task("T20", "DataMining", (long)30000),
                new Task("T21", "DataMining", (long)5000),
                new Task("T22", "DataMining", (long)10000),
                new Task("T23", "DataMining", (long)15000),
                new Task("T24", "DataMining", (long)20000),
                new Task("T25", "DataMining", (long)30000),
                new Task("T26", "DataMining", (long)5000),
                new Task("T27", "DataMining", (long)10000),
                new Task("T28", "DataMining", (long)15000),
                new Task("T29", "DataMining", (long)20000),
                new Task("T30", "DataMining", (long)30000)
            };
            Task[] BoTBI = {
                new Task("T31", "Bioinformatics", (long)5000),
                new Task("T32", "Bioinformatics", (long)10000),
                new Task("T33", "Bioinformatics", (long)15000),
                new Task("T34", "Bioinformatics", (long)20000),
                new Task("T35", "Bioinformatics", (long)30000),
                new Task("T36", "Bioinformatics", (long)5000),
                new Task("T37", "Bioinformatics", (long)10000),
                new Task("T38", "Bioinformatics", (long)15000),
                new Task("T39", "Bioinformatics", (long)20000),
                new Task("T40", "Bioinformatics", (long)30000),
                new Task("T41", "Bioinformatics", (long)5000),
                new Task("T42", "Bioinformatics", (long)10000),
                new Task("T43", "Bioinformatics", (long)15000),
                new Task("T44", "Bioinformatics", (long)20000),
                new Task("T45", "Bioinformatics", (long)30000)
            };
            
            MyThread hiloIP = new MyThread(BoTIP, registry);
            MyThread hiloDM = new MyThread(BoTDM, registry);
            MyThread hiloBI = new MyThread(BoTBI, registry);
            hiloIP.start();
            hiloDM.start();
            hiloBI.start();
            
        } catch (RemoteException ex) {
            Logger.getLogger(MasterNode.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}

class MyThread extends Thread{
    protected Task[] BoT;
    protected Registry reg;
    
    public MyThread(Task[] BoT, Registry reg) {
        this.BoT = BoT;
        this.reg = reg;
        
    }
    public void run() {
        for(Task a : BoT){
            try {
                if(a.getRequirementId().equals("DataMining")){
                    DataMining dat = (DataMining) reg.lookup("DataMining");
                    Task ret = dat.executeDataTask(a);
                    System.out.println(ret.getOutput());
                }else{
                    if(a.getRequirementId().equals("Bioinformatics")){
                        Bioinformatics bio = (Bioinformatics) reg.lookup("Bioinformatics");
                        Task ret = bio.executeBioTask(a);
                        System.out.println(ret.getOutput());
                    }else{
                        ImageProcessing ima = (ImageProcessing) reg.lookup("ImageProcessing");
                        Task ret = ima.executeImageTask(a);
                        System.out.println(ret.getOutput());
                    }
                }
            } catch (RemoteException ex) {
                    Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
} 