package Persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class RegistroPedidoBD implements RegistroPedidos {

    private final Connection conexion;

    public RegistroPedidoBD(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void guardarCosto(double costo) {
        String sql = "INSERT INTO registro_pedidos (fecha, monto_total) VALUES (?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setObject(1, LocalDateTime.now());
            ps.setDouble(2, costo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo guardar el costo en la base de datos", e);
        }
    }
}
