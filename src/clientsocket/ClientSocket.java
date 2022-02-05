/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientsocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rewoly
 */
public class ClientSocket extends TimerTask{
    String ipAddress;
    int port;
    BufferedWriter out;
    Socket socket;
    File file;
    FileReader fileReader;
    BufferedReader bufferedReader;
    
    public ClientSocket() throws IOException {
        String line;
        this.file = new File("config.txt");
        this.fileReader = new FileReader(file);
        this.bufferedReader = new BufferedReader(this.fileReader);
        while((line = bufferedReader.readLine()) != null) {
            String[] splitLine = line.split("=");
            switch (splitLine[0]){
                    case ("SERVER IP ADDRESS"): 
                        this.ipAddress = splitLine[1];
                        break;
                    case ("SERVER PORT"):
                        this.port = Integer.parseInt(splitLine[1]);
                        break;
                    case ("OBJECTNAME"):
                        Lists.objectName = splitLine[1];
                        break;
            }   
        }
        this.socket = new Socket(this.ipAddress, this.port);
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }
    
    
    @Override
    public void run() {
        String text = Lists.objectName + "=";
        if (Lists.equipmentIsFault.isEmpty()) text = text + "Все объекты в работе:";
        else {
        for (String equipmentIsFault: Lists.equipmentIsFault) text = text + equipmentIsFault + ":";
        }
        try {
            out.write(text + "\n");
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }        
}
