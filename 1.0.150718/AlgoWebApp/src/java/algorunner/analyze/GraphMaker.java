/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorunner.analyze;

import algorunner.data.DataFetcher;
import algorunner.data.Leg;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author User
 */
public class GraphMaker {

    public static ArrayList graphToLegs(TreeMap<String, float[]> map) {

        ArrayList<Leg> legs = new ArrayList<>();
        long c = 1;
        Iterator it = map.entrySet().iterator();
        float tolarance = (float) 0.001;
        float V1 = (float) 0.0, V2 = (float) 0.0, V3 = (float) 0.0;
        String Dt = "";

        Leg leg = new Leg();

        Map.Entry<String, float[]> pair = (Map.Entry<String, float[]>) it.next();
        if (it.hasNext()) {
            V1 = (pair.getValue())[0];
            leg.setFromDate(pair.getKey());
            leg.setFromPipe((pair.getValue())[0]);
            leg.setId(c);
            c++;
            it.remove(); // avoids a ConcurrentModificationException
        }

        pair = (Map.Entry<String, float[]>) it.next();
        if (it.hasNext()) {
            V2 = (pair.getValue())[0];
            Dt = pair.getKey();
            it.remove(); // avoids a ConcurrentModificationException
        }

        while (it.hasNext()) {
            pair = (Map.Entry<String, float[]>) it.next();
            V3 = (pair.getValue())[0];

            if (V1 < V2 && V2 > V3) {
                if (!legs.isEmpty()) {
                    if (Math.abs(legs.get(legs.size() - 1).getTillPipe() - V2) > tolarance) {
                        leg.setTillDate(Dt);
                        leg.setTillPipe(V2);
                        leg.setIsUp(true);
                        leg.setPipeRatio();
                        legs.add(leg);
                        leg = new Leg();
                        leg.setFromDate(Dt);
                        leg.setFromPipe(V2);
                        leg.setId(c);
                        c++;
                    }
                } else {
                    leg.setTillDate(Dt);
                    leg.setTillPipe(V2);
                    leg.setIsUp(true);
                    leg.setPipeRatio();
                    legs.add(leg);
                    leg = new Leg();
                    leg.setFromDate(Dt);
                    leg.setFromPipe(V2);
                    leg.setId(c);
                    c++;
                }
            }

            if (V1 > V2 && V2 < V3) {
                if (!legs.isEmpty()) {
                    if (Math.abs(legs.get(legs.size() - 1).getTillPipe() - V2) > tolarance) {
                        leg.setTillDate(Dt);
                        leg.setTillPipe(V2);
                        leg.setIsUp(false);
                        legs.add(leg);
                        leg.setPipeRatio();
                        leg = new Leg();
                        leg.setFromDate(Dt);
                        leg.setFromPipe(V2);
                        leg.setId(c);
                        c++;
                    }
                } else {
                    leg.setTillDate(Dt);
                    leg.setTillPipe(V2);
                    leg.setIsUp(false);
                    leg.setPipeRatio();
                    legs.add(leg);
                    leg = new Leg();
                    leg.setFromDate(Dt);
                    leg.setFromPipe(V2);
                    leg.setId(c);
                    c++;
                }
            }

            V1 = V2;
            V2 = V3;
            Dt = pair.getKey();
            it.remove(); // avoids a ConcurrentModificationException
        }

        return legs;
    }

    public static void main(String[] args) {
        DataFetcher df = new DataFetcher("data.csv",false);
        ArrayList<Leg> legs = new ArrayList<>();
        legs = graphToLegs(df.map);
        for (int i = 0; i < legs.size(); i++) {
            Leg t = legs.get(i);
            System.out.println(t.getFromDate() + " " + t.getFromPipe() + " " + t.getTillDate() + " " + t.getTillPipe() + " " + t.isIsUp() + " " + t.getId());
        }
    }

}
