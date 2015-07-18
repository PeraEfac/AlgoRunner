/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorunner;


import algorunner.controller.DataSetJpaController;
import algorunner.controller.EMFHandler;
import algorunner.controller.SkipPatternsJpaController;
import algorunner.entity.DataSet;
import algorunner.entity.SkipPatterns;

/**
 *
 * @author Sandaruwan
 */
public class Test {
    
    
    public static void main(String[] args) {
        
        DataSetJpaController dsjc = new DataSetJpaController(EMFHandler.getEmf());
        DataSet  ds = new DataSet();
        ds.setId(1L);
        SkipPatterns sp = new SkipPatterns();
        SkipPatternsJpaController spjc =new SkipPatternsJpaController(EMFHandler.getEmf());
        spjc.create(sp);
        
        dsjc.create(ds);
    }
    
}
