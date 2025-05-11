 package controller;

import com.Database.ConexionBD;
import com.Database.ParticipantesDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ActualizarParticipanteServlet")
public class ActualizarParticipanteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {
            // Obtener parámetros del formulario
            int idParticipante = Integer.parseInt(request.getParameter("id_participante"));
            String nombre = request.getParameter("nombre");
            int edad = Integer.parseInt(request.getParameter("edad"));
            String historial = request.getParameter("historial") != null ? request.getParameter("historial") : "No disponible";
            String genero = request.getParameter("genero") != null ? request.getParameter("genero") : "N/A";
            int proyectoId = Integer.parseInt(request.getParameter("proyecto_id"));
            int generoId = Integer.parseInt(request.getParameter("genero_id"));

            // Conectar con la base de datos
            try (Connection conexion = new ConexionBD().conectar()) {
                if (conexion == null) {
                    response.getWriter().write("<h2 style='color:red;'>Error: No se pudo establecer conexión con la base de datos.</h2>");
                    return;
                }

                // Verificar si el participante existe antes de actualizar
                String sqlVerificar = "SELECT COUNT(*) FROM participantes WHERE id_participante = ?";
                try (PreparedStatement stmtVerificar = conexion.prepareStatement(sqlVerificar)) {
                    stmtVerificar.setInt(1, idParticipante);
                    var rs = stmtVerificar.executeQuery();
                    if (rs.next() && rs.getInt(1) == 0) {
                        response.getWriter().write("<h2 style='color:red;'>Error: El participante con ID " + idParticipante + " no existe.</h2>");
                        return;
                    }
                }

                // Actualizar participante
                ParticipantesDAO participantesDAO = new ParticipantesDAO();
                boolean actualizado = participantesDAO.actualizarParticipante(conexion, idParticipante, nombre, edad, historial, proyectoId, generoId);

                if (actualizado) {
                    response.getWriter().write("<h2 style='color:green;'>¡Participante actualizado exitosamente!</h2>");
                } else {
                    response.getWriter().write("<h2 style='color:red;'>Error al actualizar participante.</h2>");
                }

            } catch (SQLException e) {
                response.getWriter().write("<h2 style='color:red;'>Error en la base de datos: " + e.getMessage() + "</h2>");
            }

        } catch (NumberFormatException e) {
            response.getWriter().write("<h2 style='color:red;'>Error: ID inválido, ingresa un número válido.</h2>");
        } catch (IOException e) {
            response.getWriter().write("<h2 style='color:red;'>Error al procesar la solicitud: " + e.getMessage() + "</h2>");
        }
    }
}
