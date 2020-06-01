/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.ajjacome2.objectandclasses.utils;

/**
 *
 * @author jon_m
 */
public class FileManager {
    public boolean create(String fileName) {
        try {
            System.out.println("Creating file -> " + fileName);
            return true;
        } catch (Exception exception) {
            System.out.println("Creating file " + fileName + " FAILED :( ");
            return false;
        }
    }
    
    public boolean write(String data, String fileName) {
        try {
            System.out.println("writing " + data + " in file: " + fileName);
            return true;
        } catch (Exception exception) {
            System.out.println("witing file" + fileName + " FAILED :( ");
            return false;
        }
    }
    
    public String read(String fileName) {
        String content = new String();
        
        try {
            System.out.println("Reading data from " + fileName);
        } catch (Exception exception) {
            System.out.println("Failing to read form " + fileName);
        }
        
        return content;
    }
}
