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

@WebServlet("/ConsultarServlet")
public class ConsultarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String nombre = request.getParameter("nombre");

        try (Connection conexion = new ConexionBD().conectar()) {
            if (conexion == null) {
                response.getWriter().write("Error de conexión a la base de datos.");
                return;
            }

            ParticipantesDAO participantesDAO = new ParticipantesDAO();
            List<String[]> participantes = participantesDAO.buscarPorNombre(conexion, nombre);

            StringBuilder html = new StringBuilder("<html><head><title>Resultados de la búsqueda</title>");
            html.append("<link rel='stylesheet' href='styles.css'></head><body>");
            html.append("<h1>Resultados de la búsqueda</h1>");

            if (participantes.isEmpty()) {
                html.append("<p>No se encontraron participantes con ese nombre.</p>");
            } else {
                html.append("<table><tr><th>ID</th><th>Nombre</th><th>Edad</th><th>Historial</th><th>Fecha Registro</th><th>Proyecto ID</th><th>Género ID</th><th>Investigador ID</th></tr>");
                for (String[] participante : participantes) {
                    html.append("<tr>");
                    for (String campo : participante) {
                        html.append("<td>").append(campo).append("</td>");
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
