import Entities.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestauranteTest {
    RegistroPedidoFakeArchivo registro=new RegistroPedidoFakeArchivo();
    @Test
    public void calculoDeCostoConTarjetaVisa() {
        Producto agua = new Producto("Agua", 1000, TipoProducto.BEBIDA);
        Producto hamburguesa = new Producto("Hamburguesa", 5000, TipoProducto.PLATO_PRINCIPAL);

        Pedido pedido = new Pedido(registro);
        pedido.agregarItem(agua, 2);
        pedido.agregarItem(hamburguesa, 1);
        pedido.confirmar(new Tarjeta(TipoTarjeta.VISA), Propina.DOS_PORCIENTO);

        assertTrue(registro.fueInvocado());
        assertTrue(registro.getUltimoMontoGuardado() > 0);
        assertEquals(2000, pedido.totalBebidas(), 0.001);
        assertEquals(5000, pedido.totalPlatos(), 0.001);
        assertEquals(60, pedido.descuento(), 0.001);
        assertEquals(7078.8, pedido.costoTotal(), 0.001);
    }

    @Test
    public void calculoDeCostoConTarjetaMastercard() {
        Producto gaseosa = new Producto("Gaseosa", 1500, TipoProducto.BEBIDA);
        Producto pizza = new Producto("Pizza", 6000, TipoProducto.PLATO_PRINCIPAL);

        Pedido pedido = new Pedido(registro);
        pedido.agregarItem(gaseosa, 2);
        pedido.agregarItem(pizza, 1);
        pedido.confirmar(new Tarjeta(TipoTarjeta.MASTERCARD), Propina.TRES_PORCIENTO);

        assertTrue(registro.fueInvocado());
        assertTrue(registro.getUltimoMontoGuardado() > 0);
        assertEquals(120, pedido.descuento(), 0.001);
        assertEquals(9146.4, pedido.costoTotal(), 0.001);
    }

    @Test
    public void calculoDeCostoConTarjetaComarcaPlus() {
        Producto vino = new Producto("Vino", 4000, TipoProducto.BEBIDA);
        Producto pasta = new Producto("Pasta", 8000, TipoProducto.PLATO_PRINCIPAL);

        Pedido pedido = new Pedido(registro);
        pedido.agregarItem(vino, 1);
        pedido.agregarItem(pasta, 1);
        pedido.confirmar(new Tarjeta(TipoTarjeta.COMARCA_PLUS), Propina.CINCO_PORCIENTO);

        assertTrue(registro.fueInvocado());
        assertTrue(registro.getUltimoMontoGuardado() > 0);
        assertEquals(240, pedido.descuento(), 0.001);
        assertEquals(12348, pedido.costoTotal(), 0.001);
    }

    @Test
    public void calculoDeCostoConTarjetaViedma() {
        Producto cerveza = new Producto("Cerveza", 2000, TipoProducto.BEBIDA);
        Producto milanesa = new Producto("Milanesa", 7000, TipoProducto.PLATO_PRINCIPAL);

        Pedido pedido = new Pedido(registro);
        pedido.agregarItem(cerveza, 1);
        pedido.agregarItem(milanesa, 1);
        pedido.confirmar(new Tarjeta(TipoTarjeta.VIEDMA), Propina.DOS_PORCIENTO);

        assertTrue(registro.fueInvocado());
        assertTrue(registro.getUltimoMontoGuardado() > 0);
        assertEquals(0, pedido.descuento(), 0.001);
        assertEquals(9180, pedido.costoTotal(), 0.001);
    }


}
