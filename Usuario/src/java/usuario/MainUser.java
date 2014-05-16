/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.ObjectMessage;
import java.util.Scanner;

/**
 *
 * @author Equipo
 */
public class MainUser {

    @Resource(mappedName = "jms/GlassFishTestConnectionFactory")
    private static ConnectionFactory connectionFactory;
    private static Queue queue;
    private static String username;
    private static String country;

    public MainUser(String u) {
        this.username = u;
        this.country = "Mexico";
    }

    public MainUser(String u, String c) {
        this.username = u;
        this.country = c;
    }
    //Abre un archivo llamado FriendsOf+username, guarda los amigos en un arraylist y lanza el iterador
    public Iterator obtainFriends() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            File f;
            FileReader lectorArchivo;
            f = new File("FriendsOf" + username);
            lectorArchivo = new FileReader(f);
            BufferedReader br = new BufferedReader(lectorArchivo);
            String aux = "";/*variable auxiliar*/

            while (true) {
                aux = br.readLine();
                if (aux != null) {
                    list.add(aux);
                } else {
                    break;
                }
            }
            br.close();
            lectorArchivo.close();

        } catch (IOException e) {
            System.out.println("Error:" + e.getMessage());
        }
        return list.iterator();
    }
//Cuando se quiera agregar un amigo o mandar una publicacion, abre el archivo que le digas y guarda la publicacion o el amigo
    public void saveInFile(String p, String filename, boolean append) {
        File f;
        //Name of the file could be: FeedsOf+username or FriendsOf+username
        f = new File(filename);
        try {
            FileWriter w = new FileWriter(f,append);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            wr.println(p);
            wr.close();
            bw.close();
        } catch (IOException e) {
        };
    }

    public void produceMessages(String message, String type) {
        MessageProducer messageProducer;
        ObjectMessage objectMessage;
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false /*Transacter*/, Session.AUTO_ACKNOWLEDGE);
            Iterator it;
            it = obtainFriends();
            while (it.hasNext()) {
                queue = session.createQueue((String) it.next());
                messageProducer = session.createProducer(queue);
                objectMessage = session.createObjectMessage();
                Mensaje publicacion = new Mensaje(username,message,type);
                objectMessage.setObject(publicacion);
                saveInFile(message, "FeedsOf" + username,true);
                System.out.println("Sending the following message: " + message);
                messageProducer.send(objectMessage);
                messageProducer.close();
            }
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void getMessages() {
        Connection connection;

        ObjectMessage objectMessage;

        try {
            connection = connectionFactory.createConnection();
            Enumeration messageEnumeration;
            Session session = connection.createSession(false /*Transacter*/, Session.AUTO_ACKNOWLEDGE);
            queue = session.createQueue(username);
            Mensaje aux;
            //messageConsumer = session.createConsumer(queue);
            QueueBrowser browser = session.createBrowser(queue);
            messageEnumeration = browser.getEnumeration();

            if (messageEnumeration != null) {
                if (!messageEnumeration.hasMoreElements()) {
                    System.out.println("There are no messages " + "in the queue.");
                } else {
                    System.out.println("The following messages are in the queue:");
                    while (messageEnumeration.hasMoreElements()) {
                        objectMessage = (ObjectMessage) messageEnumeration.nextElement();
                        aux = (Mensaje) objectMessage.getObject();
                        if(aux.getType().equals("publication")){
                            String publication=aux.getSender()+": "+aux.getMessage();
                            System.out.println(publication);
                            saveInFile(publication,"FeedsOf" + username,false);
                        }else{
                            System.out.println("Haz agregado a "+aux.getSender()+" como amigo");
                            saveInFile(aux.getSender(),"FriendsOf"+username,true);
                        }
                    }
                }
            }
            session.close();
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //MainUser aux = new MainUser();
        MainUser user=null;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Bienvenido a Facebook Unchained");
        System.out.println("¿Ya estas registrado? (S/N)");
        String aux = keyboard.nextLine();
        aux=aux.toUpperCase();
        boolean registro = false;
        while (!registro) {
            if (aux.charAt(0) == 'S' || aux.charAt(0) == 'N') {
                if (aux.charAt(0) == 'S') {
                    System.out.println("Por favor escribe tu nombre de usuario:");
                    aux = keyboard.nextLine();
                    user = new MainUser(aux);
                    registro = true;
                } else {
                    System.out.println("Por favor completa los siguientes campos para mandar tu registro");
                    System.out.println("Por favor escribe tu nombre de usuario:");
                    aux = keyboard.nextLine();
                    String u = aux;
                    System.out.println("Por favor indica tu pais de origen:");
                    aux = keyboard.nextLine();
                    user = new MainUser(u, aux);
                    registro = true;
                }
            } else {
                System.out.println("Por favor escirbe S o N.");
                aux =keyboard.nextLine();
            }
        }
        user.getMessages();
        System.out.println("Quieres agregar un nuevo amigo? (S/N)");
        aux = keyboard.nextLine();
        registro = false;
        aux=aux.toUpperCase();
        while (!registro) {
            if (aux.charAt(0) == 'S' || aux.charAt(0) == 'N') {
                if (aux.charAt(0) == 'S') {
                    System.out.println("Escribe el username de tu amigo:");
                    System.out.println("Escribe 'Fin' cuando ya no agregar mas amigos");
                    aux = keyboard.nextLine();
                    while(!aux.equals("Fin")){
                        user.produceMessages(aux,"friend");
                        System.out.println("Agrega otro amigo:");
                        aux = keyboard.nextLine();
                    }
                }
                    registro = true;
            } else {
                System.out.println("Por favor escirbe S o N.");
                aux =keyboard.nextLine();
            }
        }
        System.out.println("Quieres escribir alguna publicacion? (S/N)");
        aux = keyboard.nextLine();
        registro = false;
        aux=aux.toUpperCase();
        while (!registro) {
            if (aux.charAt(0) == 'S' || aux.charAt(0) == 'N') {
                if (aux.charAt(0) == 'S') {
                    System.out.println("Que les quieres decir a tus amigos:");
                    System.out.println("Escribe 'Fin' cuando ya no quieras compartir");
                    aux = keyboard.nextLine();
                    while(!aux.equals("Fin")){
                        user.produceMessages(aux,"publication");
                        System.out.println("Sigue escribiendo aqui:");
                        aux = keyboard.nextLine();
                    }
                }
                    registro = true;
            } else {
                System.out.println("Por favor escirbe S o N.");
                aux =keyboard.nextLine();
            }
        }
        
        System.out.println("Haz cerrado sesion");
    }
}
