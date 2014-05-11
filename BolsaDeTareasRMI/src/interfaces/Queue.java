/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
/**
 *
 * @author Equipo
 */
public interface Queue extends Remote{
    public String obtenerMensajes()throws RemoteException;
    public void publicar(String publicacion, ArrayList<String> amigo)throws RemoteException;
}
