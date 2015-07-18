/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorunner.data;

import java.util.TreeMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Sandaruwan
 */
public class DataFetcher {

    public TreeMap<String, float[]> map;
    String file;

    public DataFetcher(String fileName, boolean isData) {
        if (isData) {
            map = new TreeMap<>();
            file = fileName;
            new FileHandler(file, map);

        } else {

            map = new TreeMap<>();
            file = fileName;
            new FileHandler(file, map);
        }
    }

    public static void main(String[] args) {

        DataFetcher df = new DataFetcher("data.csv", false);
        System.out.println(df.map);

        TreeMap<String, float[]> mp = df.map;

        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " = " + ((float[]) pair.getValue())[0]);
            it.remove(); // avoids a ConcurrentModificationException
        }

    }

}
