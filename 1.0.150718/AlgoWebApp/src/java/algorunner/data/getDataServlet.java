/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorunner.data;

import static algorunner.analyze.GraphMaker.graphToLegs;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sahan
 */
public class getDataServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static void main(String[] args) {

        DataFetcher df = new DataFetcher("./data.csv", false);
        ArrayList<Leg> legs = new ArrayList<>();
        legs = graphToLegs(df.map);
        System.out.println("Date,Pipes");
        for (int i = 0; i < legs.size(); i++) {
            Leg t = legs.get(i);
            System.out.print(t.getFromDate() + "," + t.getFromPipe() + "\n");
        }

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("");
        response.setHeader("Access-Control-Allow-Origin", "*");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String data = request.getParameter("data");
            DataFetcher df = new DataFetcher(data, true);
            ArrayList<Leg> legs = new ArrayList<>();
            legs = graphToLegs(df.map);
            out.print("Date,Pipes\n");
            
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-DD");
            
            
            for (int i = 0; i < legs.size(); i++) {
                Leg t = legs.get(i);
                out.print(i+ "," + t.getFromPipe() + "\n");
            }

//            out.print(data);
//            out.print("Date,High,Low\n"
//                    + "2007-01-01,62,39\n"
//                    + "2007-01-02,62,44\n"
//                    + "2007-01-03,62,42\n"
//                    + "2007-01-04,57,45\n"
//                    + "2007-01-05,54,44\n"
//                    + "2007-01-06,55,36\n"
//                    + "2007-01-07,62,45\n"
//                    + "2007-01-08,50,48\n"
//                    + "2007-01-09,63,39\n"
//                    + "2007-01-10,57,37\n"
//                    + "2007-01-11,50,37\n"
//                    + "2007-01-12,48,35\n");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
