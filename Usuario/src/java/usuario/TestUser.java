/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package usuario;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 *
 * @author Equipo
 */
public class TestUser {
     @Resource(mappedName = "jms/GlassFishTestConnectionFactory")
    private static ConnectionFactory connectionFactory;
    private static Queue queue;
    public static String username = "Aaron";
    
     
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

    public void saveInFile(String p,String filename) {
        File f;
        //Name of the file could be: FeedsOf+username or FriendsOf+username
        f = new File(filename);
        try {
            FileWriter w = new FileWriter(f,true);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            wr.println(p);
            wr.close();
            bw.close();
        } catch (IOException e) {
        };
    }

    public void produceMessages(String message) {
        MessageProducer messageProducer;
        TextMessage textMessage;
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false /*Transacter*/, Session.AUTO_ACKNOWLEDGE);
            Iterator it;
            it=obtainFriends();
            while (it.hasNext()){
            queue = session.createQueue((String)it.next());
            messageProducer = session.createProducer(queue);
            textMessage = session.createTextMessage();
            textMessage.setText(message);
            saveInFile(message,"FeedsOf"+username);
            System.out.println("Sending the following message: " + textMessage.getText());
            messageProducer.send(textMessage);
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
        
        TextMessage textMessage;
        
        try {
            connection = connectionFactory.createConnection();
            Enumeration messageEnumeration;
            Session session = connection.createSession(false /*Transacter*/, Session.AUTO_ACKNOWLEDGE);
            queue = session.createQueue(username);
            //messageConsumer = session.createConsumer(queue);
            QueueBrowser browser = session.createBrowser(queue);
            messageEnumeration = browser.getEnumeration();

            if (messageEnumeration != null) {
                if (!messageEnumeration.hasMoreElements()) {
                    System.out.println("There are no messages " + "in the queue.");
                } else {
                    System.out.println("The following messages are in the queue:");
                    while (messageEnumeration.hasMoreElements()) {
                        textMessage = (TextMessage) messageEnumeration.nextElement();
                        System.out.println(textMessage.getText());
                        saveInFile(textMessage.getText(),"FeedsOf"+username);
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
        TestUser aux = new TestUser();
        aux.getMessages();
    }
}
