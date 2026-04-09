package org.example;
import Entities.*;
import Persistence.RegistroPedidoArchivo;

public class Main {
    public static void main(String[] args) {

        RegistroPedidoArchivo registro = new RegistroPedidoArchivo("pedidos.txt");

        Pedido pedido = new Pedido(registro);

        Producto agua = new Producto("Agua", 1000, TipoProducto.BEBIDA);
        Producto hamburguesa = new Producto("Hamburguesa", 5000, TipoProducto.PLATO_PRINCIPAL);

        pedido.agregarItem(agua, 2);
        pedido.agregarItem(hamburguesa, 1);

        pedido.confirmar(new Tarjeta(TipoTarjeta.VISA), Propina.DOS_PORCIENTO);

        System.out.println("Pedido confirmado y guardado en archivo.");
    }
}