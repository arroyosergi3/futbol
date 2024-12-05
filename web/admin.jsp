<%-- 
    Document   : admin
    Created on : 5 dic 2024, 9:42:21
    Author     : diurno
--%>

<%@ page import="java.sql.*" %>
<%@ page import="clases.ConnMysql" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css">
        <title>Partidos</title>
    </head>
    <body>
        <div class="container mt-5">
            <h1 class="text-center mb-4">Lista de Partidos</h1>
            <table class="table table-bordered text-center">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Equipo Local</th>
                        <th>Goles Local</th>
                        <th>Goles Visitantes</th>
                        <th>Equipo Visitante</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        // Conexión a la base de datos
                        Connection conn = null;
                        Statement stmt = null;
                        ResultSet rs = null;

                        try {
                            conn = new ConnMysql().getConnection();
                            stmt = conn.createStatement();

                            // Consulta para obtener los partidos con nombres de equipos
                            String query = "SELECT p.id, e1.nombre AS local, p.g1, p.g2, e2.nombre AS visitante "
                                    + "FROM partido p "
                                    + "JOIN equipo e1 ON p.e1 = e1.id "
                                    + "JOIN equipo e2 ON p.e2 = e2.id";
                            rs = stmt.executeQuery(query);
                            while (rs.next()) {
                                int id = rs.getInt("id");
                                String equipoLocal = rs.getString("local");
                                int golesLocal = rs.getInt("g1");
                                int golesVisitante = rs.getInt("g2");
                                String equipoVisitante = rs.getString("visitante");
                    %>
                <script>
                    function cambiar(button) {
                        // Cambiar texto del botón
                        if (button.textContent === "Editar") {
                            button.textContent = "Guardar";
                        } else {
                            button.textContent = "Editar";
                        }

                        const form = document.getElementById("form-",button.id); 
                        
                        if (form) {
                            console.log("Formulario ubicade");
                            const inputs = form.querySelectorAll('input'); 
                            inputs.forEach(input => {
                                if (input.classList.contains('d-none')) {
                                    input.classList.remove('d-none');
                                    input.classList.add('d-block');
                                } else {
                                    input.classList.remove('d-block');
                                    input.classList.add('d-none');
                                }
                            });
                        }else{
                            console.log("No ta el formualrio");
                        }
                    }


                </script>
                <tr>
                <form id="form-<%= id%>" method="post" action="s2editar">
                    <td><%= id%></td>
                    <td><%= equipoLocal%></td>
                    <td>
                        <span id="<%=id %>"><%=golesLocal %></span>
                        <input id="golesLocal-input-<%= id%>" class="d-none" name="golesLocal" type="number" value="<%= golesLocal%>">
                    </td>
                    <td>
                        
                        <span id="<%=id %>"><%=golesVisitante %></span>
                        <input id="golesVisitante-input-<%= id%>" class="d-none" name="golesVisitante" type="number" value="<%= golesVisitante%>">
                    </td>
                    <td><%= equipoVisitante%></td>
                    <td>
                        <!-- Botón para alternar entre "Editar" y "Guardar" -->
                        <button type="button" id="edit-btn-<%= id%>" class="btn btn-warning btn-sm" onclick="cambiar(this)">Editar</button>
                        <a href="s3borrar.java?id=<%= id%>" class="btn btn-danger btn-sm">Borrar</a>
                    </td>
                </form>
                </tr>
                <%
                        }
                    } catch (SQLException e) {
                        out.println("<tr><td colspan='6'>Error al cargar los partidos: " + e.getMessage() + "</td></tr>");
                    } finally {
                        if (rs != null) {
                            rs.close();
                        }
                        if (stmt != null) {
                            stmt.close();
                        }
                        if (conn != null) {
                            conn.close();
                        }
                    }
                %>
                </tbody>
            </table>

            <form action="insertarPartido.jsp" method="POST" class="row g-3">
                <div class="col-md-3">
                    <select name="equipo1" class="form-select">
                        <option selected disabled>Equipo1...</option>
                        <%
                            try {
                                conn = new ConnMysql().getConnection();
                                stmt = conn.createStatement();
                                rs = stmt.executeQuery("SELECT id, nombre FROM equipo");

                                while (rs.next()) {
                                    int idEquipo = rs.getInt("id");
                                    String nombreEquipo = rs.getString("nombre");
                        %>
                        <option value="<%= idEquipo%>"><%= nombreEquipo%></option>
                        <%
                                }
                            } catch (SQLException e) {
                                out.println("<option>Error al cargar equipos</option>");
                            } finally {
                                if (rs != null) {
                                    rs.close();
                                }
                                if (stmt != null) {
                                    stmt.close();
                                }
                                if (conn != null) {
                                    conn.close();
                                }
                            }
                        %>
                    </select>
                </div>
                <div class="col-md-2">
                    <input type="number" name="goles1" class="form-control" placeholder="Goles E1..." required>
                </div>
                <div class="col-md-2">
                    <input type="number" name="goles2" class="form-control" placeholder="Goles E2..." required>
                </div>
                <div class="col-md-3">
                    <select name="equipo2" class="form-select">
                        <option selected disabled>Equipo2...</option>
                        <%
                            try {
                                conn = new ConnMysql().getConnection();
                                stmt = conn.createStatement();
                                rs = stmt.executeQuery("SELECT id, nombre FROM equipo");

                                while (rs.next()) {
                                    int idEquipo = rs.getInt("id");
                                    String nombreEquipo = rs.getString("nombre");
                        %>
                        <option value="<%= idEquipo%>"><%= nombreEquipo%></option>
                        <%
                                }
                            } catch (SQLException e) {
                                out.println("<option>Error al cargar equipos</option>");
                            } finally {
                                if (rs != null) {
                                    rs.close();
                                }
                                if (stmt != null) {
                                    stmt.close();
                                }
                                if (conn != null) {
                                    conn.close();
                                }
                            }
                        %>
                    </select>
                </div>
                <div class="col-md-1">
                    <button type="submit" class="btn btn-success">Insertar</button>
                </div>
                <div class="col-md-1">
                    <button type="reset" class="btn btn-dark">Cerrar</button>
                </div>
            </form>
        </div>
    </body>
</html>
