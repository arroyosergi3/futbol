package newpackage;

import clases.ConnMysql;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author diurno
 */
@WebServlet(name = "s1insertar", urlPatterns = {"/s1insertar"})
public class s1insertar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtener los parámetros del formulario para un partido
        int equipo1 = Integer.parseInt(request.getParameter("equipo1"));
        int equipo2 = Integer.parseInt(request.getParameter("equipo2"));
        int goles1 = Integer.parseInt(request.getParameter("goles1"));
        int goles2 = Integer.parseInt(request.getParameter("goles2"));

        // Establecer conexión con la base de datos
        Connection miconexion = new ConnMysql().getConnection();
        
        // Insertar un partido en la tabla 'partido'
        String query = "INSERT INTO partido (e1, e2, g1, g2) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = miconexion.prepareStatement(query);
            ps.setInt(1, equipo1); // ID del primer equipo
            ps.setInt(2, equipo2); // ID del segundo equipo
            ps.setInt(3, goles1); // Goles del primer equipo
            ps.setInt(4, goles2); // Goles del segundo equipo
            ps.executeUpdate(); // Ejecutar la inserción
        } catch (Exception e) {
            System.out.println("Ha habido un error al insertar el partido: " + e.getMessage());
        }

        // Redirigir de nuevo al index.jsp
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet que maneja la inserción de partidos de fútbol";
    }
}
