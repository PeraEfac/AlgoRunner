/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorunner;

import algorunner.controller.DataSetJpaController;
import algorunner.controller.EMFHandler;
import algorunner.controller.SkipPatternsJpaController;
import algorunner.data.Leg;
import algorunner.entity.DataSet;
import algorunner.entity.SkipPatterns;
import java.util.ArrayList;

/**
 *
 * @author Sandaruwan
 */
public class AlgoRunner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DataSetJpaController dsjc = new DataSetJpaController(EMFHandler.getEmf());
        DataSet  ds = new DataSet();
        ds.setId(1L);
        ds.setUserData("held");
        SkipPatterns sp = new SkipPatterns();
        SkipPatternsJpaController spjc =new SkipPatternsJpaController(EMFHandler.getEmf());
        spjc.create(sp);
        
        dsjc.create(ds);
    }
    
}
