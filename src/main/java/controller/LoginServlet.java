 package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.Database.ConexionBD;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String usuario = request.getParameter("nombre");
        String contraseña = request.getParameter("historial");

        if (usuario == null || contraseña == null || usuario.isEmpty() || contraseña.isEmpty()) {
            response.getWriter().write("<h2 style='color:red;'>Error: Todos los campos son obligatorios.</h2>");
            return;
        }

        try (Connection conexion = new ConexionBD().conectar()) {
            if (conexion == null) {
                response.getWriter().write("<h2 style='color:red;'>Error: No se pudo conectar a la base de datos.</h2>");
                return;
            }

            String sql = "SELECT id_participante, nombre FROM participantes WHERE nombre = ? AND historial = ?";
            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                stmt.setString(1, usuario);
                stmt.setString(2, contraseña);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        response.sendRedirect("bienvenido.html");
                    } else {
                        response.getWriter().write("<h2 style='color:red;'>Error: Usuario o contraseña incorrectos.</h2>");
                    }
                }
            }
        } catch (SQLException e) {
            response.getWriter().write("<h2 style='color:red;'>Error en la base de datos: " + e.getMessage() + "</h2>");
        }
    }
}
