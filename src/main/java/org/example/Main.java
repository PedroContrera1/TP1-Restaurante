package org.example;

import Entities.*;
import Persistence.ConexionBD;
import Persistence.RegistroPedidoBD;
import Persistence.RegistroPedidos;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conexion = ConexionBD.obtenerConexion();

        RegistroPedidos registro = new RegistroPedidoBD(conexion);
        Pedido pedido = new Pedido(registro);

        Producto agua = new Producto("Agua", 1000, TipoProducto.BEBIDA);
        Producto pizza = new Producto("Pizza", 5000, TipoProducto.PLATO_PRINCIPAL);

        pedido.agregarItem(agua, 2);
        pedido.agregarItem(pizza, 1);
        pedido.confirmar(new Tarjeta(TipoTarjeta.VISA), Propina.DOS_PORCIENTO);

        System.out.println("Total: " + pedido.costoTotal());
    }
}