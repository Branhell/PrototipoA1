 package controller;

import com.Database.ConexionBD;
import com.Database.ParticipantesDAO;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MostrarParticipantesServlet")
public class MostrarParticipantesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (Connection conexion = new ConexionBD().conectar()) {
            if (conexion == null) {
                response.getWriter().write("Error de conexión a la base de datos.");
                return;
            }

            ParticipantesDAO participantesDAO = new ParticipantesDAO();
            List<String[]> participantes = participantesDAO.obtenerParticipantes(conexion);

            StringBuilder html = new StringBuilder("<html><head><title>Participantes</title>");
            html.append("<link rel='stylesheet' href='styles.css'></head><body>");
            html.append("<h1>Lista de Participantes</h1>");

            if (participantes.isEmpty()) {
                html.append("<p>No hay participantes registrados.</p>");
            } else {
                html.append("<table border='1'><tr><th>ID</th><th>Nombre</th><th>Edad</th><th>Historial</th><th>Fecha Registro</th><th>Proyecto ID</th><th>Género ID</th><th>Investigador ID</th></tr>");
                for (String[] participante : participantes) {
                    html.append("<tr>");
                    for (String campo : participante) {
                        html.append("<td>").append(campo != null ? campo : "N/A").append("</td>");
                    }
                    html.append("</tr>");
                }
                html.append("</table>");
            }

            html.append("<br><a href='dashboard.jsp'>Volver al Dashboard</a>");
            html.append("</body></html>");
            response.getWriter().write(html.toString());

        } catch (Exception e) {
            response.getWriter().write("Error al procesar la solicitud: " + e.getMessage());
        }
    }
}
