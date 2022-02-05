/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientsocket;

import java.io.IOException;
import java.util.Timer;

/**
 *
 * @author rewoly
 */
public class ClientLauncher {
     
    public static void main(String[] args) throws IOException {
        Timer timer = new Timer();
        Pingstation pingstation = new Pingstation();
        ClientSocket clientSocket = new ClientSocket();
        
        timer.scheduleAtFixedRate(pingstation, 0, 60*1000);
        timer.scheduleAtFixedRate(clientSocket, 6*1000, 60*1000);
    }
}
