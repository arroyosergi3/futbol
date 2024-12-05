/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package newpackage;

import clases.ConnMysql;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.*;

/**
 *
 * @author diurno
 */
@WebServlet(name = "s1comprobarAdmin", urlPatterns = {"/s1comprobarAdmin"})
public class s1comprobarAdmin extends HttpServlet {

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

            String pass = request.getParameter("admin");

            Connection miconexion = new ConnMysql().getConnection();

            //out.println("<h1> POR LO MENOS LLEGA AL S1</h1>");
            // Insertar un partido en la tabla 'partido'
            String query = "Select * from clave";
            try {

                Statement stmt = miconexion.createStatement();

                // Ejecutar la consulta
                ResultSet rs = stmt.executeQuery(query);

                if (rs != null) {

                    if (rs.next()) {

                        String passBD = rs.getString("pass");

                        if (passBD.equals(pass)) {
                            response.sendRedirect("/futbol/admin.jsp");
                        } else {
                            out.println("<!DOCTYPE html>");
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>Servlet s1comprobarAdmin</title>");
                            out.println("</head>");
                            out.println("<body>");
                            out.println("<h1> ERROR, la contraseña no es correcta");
                            out.println("</body>");
                            out.println("</html>");
                        }
                    } else {

                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Servlet s1comprobarAdmin</title>");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h1> ERROR, no se encuentran registros</h1>");
                        out.println("</body>");
                        out.println("</html>");
                    }
                }
            } catch (Exception e) {
                out.println("<h1> ERROR, SALTA EL CATCH</h1>");
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
