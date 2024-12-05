<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Clasificación Liga de Fútbol</title>
    
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css">
</head>
<body>
<h1>Clasificación Actual</h1>
<table class="table">
    <tr>
        <th>#</th>
        <th>Equipo</th>
        <th>Puntos</th>
        <th>Victorias</th>
        <th>Empates</th>
        <th>Derrotas</th>
        <th>Goles Favor</th>
        <th>Goles en Contra</th>
    </tr>
    <%
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/futbol", "dwes", "abc123.");
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM equipo ORDER BY puntos DESC");
            int puesto = 1;
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + puesto++ + "</td>");   
                out.println("<td><img src='" + rs.getString("id") + ".png'/> " + rs.getString("nombre") + "</td>");
                out.println("<td>" + rs.getInt("puntos") + "</td>");
                out.println("<td>" + rs.getInt("v") + "</td>");
                out.println("<td>" + rs.getInt("e") + "</td>");
                out.println("<td>" + rs.getInt("d") + "</td>");
                out.println("<td>" + rs.getInt("gf") + "</td>");
                out.println("<td>" + rs.getInt("gc") + "</td>");
                out.println("</tr>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (con != null) con.close();
        }
    %>
</table>
<br>
<br>
<br>
<br>
<form action="s1comprobarAdmin" method="post">
    <input type="password" name="admin" placeholder="Introduce la contraseña">
    <input type="submit" class="btn btn-primary" value="Administrador">
</form>
</body>
</html>