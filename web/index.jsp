<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Clasificación Liga de Fútbol</title>
</head>
<body>
<h1>Clasificación Actual</h1>
<table border="1">
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/futbol", "usuario", "contraseña");
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM equipo ORDER BY puntos DESC");
            int puesto = 1;
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + puesto++ + "</td>");
                out.println("<td><img src='images/" + rs.getString("id") + ".png' alt='" + rs.getString("nombre") + "'/> " + rs.getString("nombre") + "</td>");
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
<a href="admin.jsp">Entrar como Administrador</a>
</body>
</html>