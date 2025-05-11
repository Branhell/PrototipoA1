 package controller;

import com.Database.ConexionBD;
import com.Database.ParticipantesDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EliminarParticipanteServlet")
public class EliminarParticipanteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {
            int idParticipante = Integer.parseInt(request.getParameter("id_participante"));

            try (Connection conexion = new ConexionBD().conectar()) {
                if (conexion != null) {
                    ParticipantesDAO participantesDAO = new ParticipantesDAO();
                    boolean eliminado = participantesDAO.eliminarParticipante(conexion, idParticipante);

                    if (eliminado) {
                        response.getWriter().write("<p style='color:green;'>¡Participante eliminado exitosamente!</p>");
                    } else {
                        response.getWriter().write("<p style='color:red;'>Error al eliminar participante.</p>");
                    }
                } else {
                    response.getWriter().write("<p style='color:red;'>Error de conexión a la base de datos.</p>");
                }
            } catch (SQLException e) {
                response.getWriter().write("<p style='color:red;'>Error en la base de datos: " + e.getMessage() + "</p>");
            }
        } catch (NumberFormatException e) {
            response.getWriter().write("<p style='color:red;'>Error: ID inválido, ingresa un número válido.</p>");
        }
    }
}
