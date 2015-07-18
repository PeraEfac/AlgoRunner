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
import java.text.SimpleDateFormat;
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
public class PatternReq extends HttpServlet {

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
            out.print("Date,Pipes\n");

            List<List<Leg>> lst = ui.getLst();

            List<Long> lstc = new ArrayList<>();

            for (int i = 0; i < legs.size(); i++) {
                Leg t = legs.get(i);
                if (analyse.contains(t.getId())) {
                    for (List<Leg> lst1 : lst) {
                        for (int j = 0; j < lst1.size(); j++) {
                            Leg lg = lst1.get(j);
                            lstc.add((long) (i + j));
                            out.print((i + j) + " ," + legs.get(i + j).getFromPipe() + "\n");
                        }
                        
                        lstc.add((long) (i + lst1.size()));
                        out.print((i + lst1.size()) + " ," + (legs.get(i + lst1.size())).getFromPipe() + "\n");
                    }

                } else if (!lstc.contains((long) i)) {
                    out.print(i + "," + "\n");
                }
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
