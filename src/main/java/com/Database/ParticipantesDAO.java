 package com.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParticipantesDAO {

    // Método para insertar un participante
    public boolean insertarParticipante(Connection conexion, String nombre, int edad, String historial, int proyectoId, int generoId) {
        String sql = "INSERT INTO participantes (nombre, edad, historial, proyecto_id, genero_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setInt(2, edad);
            stmt.setString(3, historial);
            stmt.setInt(4, proyectoId);
            stmt.setInt(5, generoId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar participante: " + e.getMessage());
            return false;
        }
    }

    // Método para actualizar un participante
    public boolean actualizarParticipante(Connection conexion, int idParticipante, String nombre, int edad, String historial, int proyectoId, int generoId) {
        String sql = "UPDATE participantes SET nombre = ?, edad = ?, historial = ?, proyecto_id = ?, genero_id = ? WHERE id_participante = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setInt(2, edad);
            stmt.setString(3, historial);
            stmt.setInt(4, proyectoId);
            stmt.setInt(5, generoId);
            stmt.setInt(6, idParticipante);

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar participante: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar un participante
    public boolean eliminarParticipante(Connection conexion, int idParticipante) {
        String sql = "DELETE FROM participantes WHERE id_participante = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idParticipante);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar participante: " + e.getMessage());
            return false;
        }
    }

    // Método para obtener todos los participantes
    public List<String[]> obtenerParticipantes(Connection conexion) {
        List<String[]> participantes = new ArrayList<>();
        String sql = "SELECT id_participante, nombre, edad, historial, fecha_registro, proyecto_id, genero_id, investigador_id FROM participantes";

        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String[] participante = {
                    rs.getString("id_participante"),
                    rs.getString("nombre"),
                    rs.getString("edad"),
                    rs.getString("historial"),
                    rs.getString("fecha_registro"),
                    rs.getString("proyecto_id"),
                    rs.getString("genero_id"),
                    rs.getString("investigador_id")
                };
                participantes.add(participante);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener participantes: " + e.getMessage());
        }
        return participantes;
    }

    // Método para buscar participantes por nombre
    public List<String[]> buscarPorNombre(Connection conexion, String nombre) {
        List<String[]> participantes = new ArrayList<>();
        String sql = "SELECT id_participante, nombre, edad, historial, fecha_registro, proyecto_id, genero_id, investigador_id FROM participantes WHERE nombre LIKE ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombre + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String[] participante = {
                        rs.getString("id_participante"),
                        rs.getString("nombre"),
                        rs.getString("edad"),
                        rs.getString("historial"),
                        rs.getString("fecha_registro"),
                        rs.getString("proyecto_id"),
                        rs.getString("genero_id"),
                        rs.getString("investigador_id")
                    };
                    participantes.add(participante);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar participante: " + e.getMessage());
        }
        return participantes;
    }

    // Método para consultar todos los participantes e imprimirlos en consola
    public void consultarParticipantes(Connection conexion) {
        String sql = "SELECT id_participante, nombre, edad, historial, fecha_registro, proyecto_id, genero_id, investigador_id FROM participantes";

        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("Lista de participantes:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getString("id_participante") +
                                   ", Nombre: " + rs.getString("nombre") +
                                   ", Edad: " + rs.getString("edad") +
                                   ", Historial: " + rs.getString("historial") +
                                   ", Fecha Registro: " + rs.getString("fecha_registro") +
                                   ", Proyecto ID: " + rs.getString("proyecto_id") +
                                   ", Género ID: " + rs.getString("genero_id") +
                                   ", Investigador ID: " + rs.getString("investigador_id"));
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar participantes: " + e.getMessage());
        }
    }
}
