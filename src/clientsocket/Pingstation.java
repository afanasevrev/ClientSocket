/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientsocket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rewoly
 */
public class Pingstation extends TimerTask {
    File fileEquipments;
    FileReader fileReader;
    BufferedReader bufferedReader;
    
    public Pingstation () {
        this.fileEquipments = new File("equipments.txt");
        try {
            this.fileReader = new FileReader(this.fileEquipments);
            this.bufferedReader = new BufferedReader(this.fileReader);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Pingstation.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void getRead() throws IOException {
        String line;
        String[] splitLine;
        while((line = this.bufferedReader.readLine()) != null) {
            String equipmentName = "";
            splitLine = line.split("\\s");
            for(int i = 1; i < splitLine.length; i++) equipmentName = equipmentName + splitLine[i] + " ";
            Lists.equipments.put(equipmentName, splitLine[0]);
        }
    }
    
    public void getPing() throws IOException {    
      for (String equipmentName: Lists.equipments.keySet()) {
        try {
            String ipAddress = Lists.equipments.get(equipmentName);
            InetAddress inet = InetAddress.getByName(ipAddress);   
            if (!inet.isReachable(2000)) {
              if (!Lists.equipmentIsFault.contains(equipmentName)) {
                      Lists.equipmentIsFault.add(equipmentName);
              }
            } else {
              if (Lists.equipmentIsFault.contains(equipmentName))
                      Lists.equipmentIsFault.remove(equipmentName); 
            }
        } catch (UnknownHostException ex) {
            System.out.println("Exception:" + ex.getMessage());
        }
      }
    }
    
    @Override
    public void run() {
        try {
            getRead();
            Thread.sleep(2000);
            getPing();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Pingstation.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }  
}
