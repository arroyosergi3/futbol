package newpackage;

import clases.ConnMysql;
import java.sql.Connection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;

@WebServlet(name = "s3borrar", urlPatterns = {"/s3borrar"})
public class s3borrar extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtener el ID del partido a borrar
        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            response.getWriter().println("ID no válido.");
            return;
        }

        // Establecer la conexión y ejecutar la eliminación en la base de datos
        ConnMysql conexion = new ConnMysql();
        Connection conn = conexion.getConnection();
        if (conn != null) {
            try {
                // Consulta SQL para eliminar un partido
                String sql = "DELETE FROM partido WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(id));  // Convertir el id a entero

                int filasEliminadas = ps.executeUpdate();
                if (filasEliminadas > 0) {
                    // Redirigir al listado de partidos después de eliminar
                    response.sendRedirect("index.jsp");
                } else {
                    response.getWriter().println("No se encontró el partido a eliminar.");
                }

                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().println("Error al eliminar el partido.");
            } finally {
                ConnMysql.cerrarConexion();
            }
        } else {
            response.getWriter().println("Error al conectar con la base de datos.");
        }
    }
}
