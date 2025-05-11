package com.Database;

import java.sql.Connection;

public class PruebaParticipantes {
    public static void main(String[] args) {
        try {
            // Configurar la salida estándar para UTF-8
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));

            // Conectar con la base de datos
            try (Connection conexion = new ConexionBD().conectar()) {
                if (conexion == null) {
                    System.out.println("No se pudo establecer conexión con la base de datos.");
                    return;
                }

                // Crear una instancia de ParticipantesDAO
                ParticipantesDAO participantesDAO = new ParticipantesDAO();

                // Prueba de inserción de participante
                System.out.println("Insertando un participante...");
                participantesDAO.insertarParticipante(conexion, "Laura Gómez", 28, "Sin historial médico", 2, 1);

                // Prueba de consulta de participantes
                System.out.println("Consultando la lista de participantes...");
                participantesDAO.consultarParticipantes(conexion);

            }
        } catch (Exception e) {
            System.err.println("Error durante la prueba: " + e.getMessage());
        }
    }
}
