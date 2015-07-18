/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorunner.controller;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

/**
 *
 * @author Sandaruwan
 */
public class EMFHandler {

    private static EntityManagerFactory emf = null;

    static {
        try {
           
            emf = Persistence.createEntityManagerFactory("AlgoRunnerPU");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Configuration files not found");
            System.exit(0);
        } 
    }

    public static EntityManagerFactory getEmf() {
        return emf;
    }
}
