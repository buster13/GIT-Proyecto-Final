/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package WebServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

/**
 *
 * @author BusterD
 */
@WebService(serviceName = "SOAPServ")
@Stateless()
public class SOAPServ {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "Register")
    public boolean Register(@WebParam(name = "username") String username, @WebParam(name = "password") String password, @WebParam(name = "name") String name, @WebParam(name = "lastname") String lastname, @WebParam(name = "country") String country, @WebParam(name = "email") String email) {
        boolean resp= false;
        try {
                Class.forName("org.apache.derby.jdbc.ClientDriver"); 
                Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/FaceBookUnchained", "root", "root");          
                Statement query = con.createStatement();
                String InsertString= "INSERT INTO " + country + " VALUES ('" + username+ "', '" + password + "', '" + name + "', '" + lastname + "', '" + country + "', '" + email +"')";
                System.out.println(InsertString);
                if(query.executeUpdate(InsertString)!=0){
                    resp =  true;
                }    
                con.commit();
                con.close();                             
            } catch (Exception ex) {
                System.out.println(ex);
            }  
        return resp;
    }

    /**
     * Web service operation
     * @return ArrayList<String> Lista de elementos de la tupla respuesta
     */
    @WebMethod(operationName = "SearchByEmail")
    public ArrayList<String> SearchByEmail(@WebParam(name = "email") String email, @WebParam(name = "country") String country) {
        ArrayList<String> datos =  null;
        try {
                Class.forName("org.apache.derby.jdbc.ClientDriver"); 
                Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/FaceBookUnchained", "root", "root");          
                Statement query = con.createStatement();
                String SelectString= "SELECT USERNAME, NAME, LASTNAME FROM " + country +" WHERE " + country +".EMAIL='" + email + "'";
                System.out.println(SelectString);
                ResultSet rs = query.executeQuery(SelectString);
                if(rs.next()){
                    datos = new ArrayList<String>();
                    datos.add(rs.getString("USERNAME"));
                    datos.add(rs.getString("NAME"));
                    datos.add(rs.getString("LASTNAME"));
                }    
                System.out.println(datos.get(0) + " " + datos.get(1) + " " + datos.get(2));
                con.commit();
                con.close();                             
            } catch (Exception ex) {
                System.out.println(ex);
            }  
        return datos;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "LogIn")
    public boolean LogIn(@WebParam(name = "username") String username, @WebParam(name = "password") String password, @WebParam(name = "country") String country) {
        boolean resp =  false;
        
        try {
                Class.forName("org.apache.derby.jdbc.ClientDriver"); 
                Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/FaceBookUnchained", "root", "root");          
                Statement query = con.createStatement();
                String SelectString= "SELECT USERNAME, PASSWORD FROM " + country +" WHERE " + country +".USERNAME='" + username + "' AND " + country +".PASSWORD='" + password +"'";
                System.out.println(SelectString);
                ResultSet rs = query.executeQuery(SelectString);
                if(rs.next()){
                    resp = true;
                }    
                con.commit();
                con.close();                             
            } catch (Exception ex) {
                System.out.println(ex);
            }  
        
        return resp;
    }

}
