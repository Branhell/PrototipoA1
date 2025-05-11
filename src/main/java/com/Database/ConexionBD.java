package com.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/quantumpsycho?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASSWORD = "Deathnote666";
    private static final Logger LOGGER = Logger.getLogger(ConexionBD.class.getName());

    public Connection conectar() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            LOGGER.info("¡Conexión exitosa a la base de datos!");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error: El controlador MySQL no fue encontrado.", e);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error de conexión a la base de datos.", e);
        }
        return conexion;
    }
}
