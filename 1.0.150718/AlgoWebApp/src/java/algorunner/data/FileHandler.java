/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorunner.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

/**
 *
 * @author Sandaruwan
 */
public class FileHandler {

    private String csvFile;
    private BufferedReader br = null;
    private String line = "";
    private String cvsSplitBy = ";";
    private TreeMap<String, float[]> map;
    private String data;

    public FileHandler(String data, TreeMap<String, float[]> mapGet) {
        this.data = data;
        
        map = mapGet;
        System.out.println("============================");
        String[] split = data.split(",");
        String date = "";
        
        for (int i = 0; i < split.length; i++) {
            String line = split[i];
            String [] lines = line.split(";");
         //   System.out.println(""+date);
            date = lines[0];
            
            float[] fn = {Float.parseFloat(lines[1]),
                Float.parseFloat(lines[2]), Float.parseFloat(lines[3]), Float.parseFloat(lines[4])};

            map.put(date, fn);

        }

    }
    
//    public FileHandler(String csvFile, TreeMap<String, float[]> mapGet) {
//        this.csvFile = csvFile;
//        map = mapGet;
//
//        String date = "";
//        try {
//
//            br = new BufferedReader(new FileReader(csvFile));
//            while ((line = br.readLine()) != null) {
//                date = line.substring(0, 16); // use comma as separator
//                String[] dataset = line.substring(16).split(cvsSplitBy);
//                
//               float[] fn = {Float.parseFloat(dataset[0]), 
//                   Float.parseFloat(dataset[1]),Float.parseFloat(dataset[2]),Float.parseFloat(dataset[3])};
//                
//                map.put(date, fn);
//                
//               // System.out.println("Data [d1= " + dataset[0] + " ,d2=" + dataset[1] + "]");
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        System.out.println("Done");
//
//    }

//    public static void main(String[] args) {
//        new FileHandler("data.csv",);
//    }

}
