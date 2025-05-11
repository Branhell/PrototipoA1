package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;


@WebServlet("/RegistroServlet")
public class RegistroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String contraseña = request.getParameter("historial");

        String jdbcURL = "jdbc:mysql://localhost:3306/quantum_db";
        String jdbcUsername = "root"; 
        String jdbcPassword = ""; 

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "INSERT INTO participantes (nombre, correo, historial) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nombre);
            statement.setString(2, correo);
            statement.setString(3, contraseña);

            int filasInsertadas = statement.executeUpdate();

            if (filasInsertadas > 0) {
                response.sendRedirect("registro_exitoso.html");
            } else {
                response.getWriter().println("<h2>Error al registrar usuario.</h2>");
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h2>Error al conectar con la base de datos.</h2>");
        }
    }
}
