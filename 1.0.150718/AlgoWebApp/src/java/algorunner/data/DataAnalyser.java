/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorunner.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Sandaruwan
 */
public class DataAnalyser {
    
    

    public List<Long> analyse(List<Leg> dataSet, List<List<Leg>> pattern) {
        List<Long> lst =new ArrayList<>();
        
        boolean isAv = true;
        for (int pin = 0; pin < dataSet.size(); pin++) {
            isAv = true;
            for (List<Leg> list : pattern) {
                for (int i = 0; i < list.size(); i++) {
                    Leg dl;
                    if (pin + i < dataSet.size()) {
                        dl = dataSet.get(pin + i);
                    } else {
                        isAv = false;
                        break;
                    }

                    Leg l = list.get(i);
                    
                    float rat = (l.getPipeRatio() / dl.getPipeRatio());
                    int ratin = (int) (l.getPipeRatio() / dl.getPipeRatio());
               //     System.out.println(" ratio " + dl.getPipeRatio() + " " + l.getPipeRatio() + " " + (l.getPipeRatio() / dl.getPipeRatio())+ " "+(rat - ratin));

                    if (!((rat - ratin) < l.getUserTolerance()) || dl.isUp != l.isUp) {
                        isAv = false;
                        break;
                    }
                }
                if (isAv) {
                    lst.add(new Long(pin));
                    System.out.println("PATTERN: " + list.size() + " " + pin);
                }
            }
            if(isAv)System.out.println("");
        }
        return lst;

    }

}
