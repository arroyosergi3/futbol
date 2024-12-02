package newpackage;

import clases.ConnMysql;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet(name = "s2editar", urlPatterns = {"/s2editar"})
public class s2editar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtener los parámetros del formulario
        String id = request.getParameter("id");
        String goles1 = request.getParameter("goles1");
        String goles2 = request.getParameter("goles2");

        // Validar que los parámetros no estén vacíos
        if (id == null || id.isEmpty() || goles1 == null || goles1.isEmpty() || goles2 == null || goles2.isEmpty()) {
            response.getWriter().println("Todos los campos son obligatorios.");
            return;
        }

        // Establecer la conexión y ejecutar la actualización en la base de datos
        ConnMysql conexion = new ConnMysql();
        Connection conn = conexion.getConnection();
        if (conn != null) {
            try {
                // Consulta SQL para actualizar los goles de un partido
                String sql = "UPDATE partido SET g1 = ?, g2 = ? WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(goles1));  // Goles del primer equipo
                ps.setInt(2, Integer.parseInt(goles2));  // Goles del segundo equipo
                ps.setInt(3, Integer.parseInt(id));  // ID del partido a actualizar

                int filasActualizadas = ps.executeUpdate();
                if (filasActualizadas > 0) {
                    // Redirigir al listado de partidos después de actualizar
                    response.sendRedirect("index.jsp");
                } else {
                    response.getWriter().println("No se encontró el partido a actualizar.");
                }

                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().println("Error al actualizar el partido.");
            } finally {
                ConnMysql.cerrarConexion();
            }
        } else {
            response.getWriter().println("Error al conectar con la base de datos.");
        }
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
        return "Servlet para actualizar los partidos de fútbol";
    }
}
