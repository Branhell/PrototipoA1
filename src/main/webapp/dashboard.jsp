 <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Quantum Psycho</title>
    <link rel="stylesheet" href="styles.css">
</head>

<body>
    <h1>Dashboard - Lista de Participantes</h1>
<table>
    <tr><th>ID</th><th>Nombre</th><th>Edad</th><th>Historial</th><th>Género</th><th>Proyecto ID</th><th>Género ID</th></tr>
    <%
        com.Database.ConexionBD conexionBD = new com.Database.ConexionBD();
        java.sql.Connection conexion = conexionBD.conectar();
        com.Database.ParticipantesDAO participantesDAO = new com.Database.ParticipantesDAO();
        java.util.List<String[]> participantes = participantesDAO.obtenerParticipantes(conexion);
        for (String[] participante : participantes) {
    %>
    <tr>
        <% for (String dato : participante) { %>
            <td><%= dato %></td>
        <% } %>
    </tr>
    <% } %>
</table>

<!-- Botones de acción fuera de la tabla -->
<div class="contenedor-botones">
    <a href="consultar.html">Consultar Participante</a>
    <a href="actualizar.html">Actualizar Participante</a>
    <a href="eliminar.html">Eliminar Participante</a>
    <a href="login.html">Login</a>
    <a href="index.html">Volver al inicio</a>
</div>

    <br>
    <a href="index.html">Volver al inicio</a>
</body>
</html>
