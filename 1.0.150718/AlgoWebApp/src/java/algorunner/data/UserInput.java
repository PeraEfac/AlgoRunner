/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorunner.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sandaruwan
 */
public class UserInput {

    private String userText = "";
    List<List<Leg>> lst = new ArrayList<>();

    public UserInput(String userText) {
        this.userText = userText;
        
    //   this.userText = "[10,000200,down,0.7;10,000200,up,0.7]";
        //this.userText = "[10,000200,down,0.8;10,000200,up,0.8;10,000200,down,0.8;10,000200,up,0.8]";
        //this.userText = "[10,000200,down;10,000200,up][10,000300,down]";
        // [10,000200,up;10,000200,down][10,000300,10]
        factorizeInput();

    }

    public void factorizeInput() {
        String[] sp = userText.split("]");
        for (String sp1 : sp) {

            String[] legs = sp1.substring(1).split(";");
            List<Leg> ls =new  ArrayList<>();
            for (int i = 0; i < legs.length; i++) {
                String[] param = legs[i].split(",");
                Leg l = new Leg();
                l.setId((long)i);
                l.setFromDate("00000000 000000");
                l.setTillDate("00000000 "+param[1]);
                l.setFromPipe(0);
                l.setTillPipe(Long.parseLong(param[0]));
                l.setIsUp(!(param[2].equals("up") ));
                l.setUserTolerance(Float.parseFloat(param[3]));
                l.setPipeRatio();
               // System.out.println(""+);
                ls.add(l);
            }
            lst.add(ls);
        }
        showInput();
    }

    public void showInput() {
        
        for (int i = 0; i < lst.size(); i++) {
            List<Leg> list = lst.get(i);
            for (Leg leg : list) {
                System.out.print(" "+leg.getId()+" "+leg.fromDate+" "+leg.getTillPipe()+" "+leg.getPipeRatio());
            }
            System.out.println("");
        }
    }
    
    public static void main(String[] args) {
        new UserInput("");
    }

    public List<List<Leg>> getLst() {
        return lst;
    }
    
    
}
