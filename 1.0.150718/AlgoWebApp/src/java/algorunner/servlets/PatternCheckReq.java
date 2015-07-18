/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorunner.servlets;

import static algorunner.analyze.GraphMaker.graphToLegs;
import algorunner.data.DataAnalyser;
import algorunner.data.DataFetcher;
import algorunner.data.Leg;
import algorunner.data.UserInput;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sahan
 */
public class PatternCheckReq extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String data = request.getParameter("data");
            String upat = request.getParameter("pat");

            System.out.println("" + data);
            System.out.println("" + upat);
            DataFetcher df = new DataFetcher(data, true);
            ArrayList<Leg> legs = new ArrayList<>();
            legs = graphToLegs(df.map);
            System.out.println("" + legs.size());
            DataAnalyser da = new DataAnalyser();
            UserInput ui = new UserInput(upat);
            List<Long> analyse = da.analyse(legs, ui.getLst());

            for (int i = 0; i < analyse.size(); i++) {
                Long t = analyse.get(i);
                out.print("<div class=\"input-group\">\n"
                        + "                        <span class=\"input-group-addon\">\n"
                        + "                            <input type=\"checkbox\" aria-label=\"...\">\n"
                        + "                        </span>\n"
                        + "                        <input type=\"text\" class=\"form-control\" aria-label=\"...\"  placeholder=\"Pin @ "
                        + (t + 1) + ": Pattern\">\n"
                        + "                    </div>");

            }

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
