package Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/restaurante_tp";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static Connection obtenerConexion() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo conectar a la base de datos", e);
        }
    }
}