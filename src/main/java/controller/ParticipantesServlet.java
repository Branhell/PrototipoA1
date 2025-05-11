package controller;

import com.Database.ConexionBD;
import com.Database.ParticipantesDAO;
import java.io.IOException;
import java.sql.Connection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ParticipantesServlet", urlPatterns = {"/ParticipantesServlet"})
public class ParticipantesServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            // Validar y obtener parámetros enviados por el formulario
            String nombre = request.getParameter("nombre");
            int edad = Integer.parseInt(request.getParameter("edad"));
            String historial = request.getParameter("historial");
            int proyectoId = Integer.parseInt(request.getParameter("proyecto_id"));
            int generoId = Integer.parseInt(request.getParameter("genero_id"));

            // Establecer conexión con la base de datos
            try (Connection conexion = new ConexionBD().conectar()) {
                if (conexion != null) {
                    // Usar ParticipantesDAO para insertar el participante
                    ParticipantesDAO participantesDAO = new ParticipantesDAO();
                    participantesDAO.insertarParticipante(conexion, nombre, edad, historial, proyectoId, generoId);

                    // Respuesta exitosa
                    response.getWriter().write("¡Participante agregado exitosamente!");
                } else {
                    response.getWriter().write("Error de conexión a la base de datos.");
                }
            }
        } catch (Exception e) {
            // Manejar errores y enviar mensaje de error al cliente
            response.getWriter().write("Error al procesar la solicitud: " + e.getMessage());
        }
    }
}
