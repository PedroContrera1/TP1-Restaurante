import Entities.*;
import Exceptions.MesaException;
import Exceptions.ValidationException;
import Persistence.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestauranteMesaTest {
    //Test creado para utilizar las funcionalidades de Restaurante y Mesa.
    @Test
    public void sePuedeOcuparUnaMesaLibre() {
        Mesa mesa = new Mesa(1, 4);

        mesa.ocupar(2);

        assertTrue(mesa.estaOcupada());
        assertEquals(2, mesa.getCantidadPersonas());
    }

    @Test
    public void noSePuedeOcuparUnaMesaYaOcupada() {
        Mesa mesa = new Mesa(1, 4);
        mesa.ocupar(2);

        MesaException exception =
                assertThrows(MesaException.class, () -> mesa.ocupar(2));

        assertEquals("La mesa ya está ocupada", exception.getMessage());
    }

    @Test
    public void noSePuedeOcuparMesaConCantidadCero() {
        Mesa mesa = new Mesa(1, 4);

        ValidationException exception =
                assertThrows(ValidationException.class, () -> mesa.ocupar(0));

        assertEquals("La cantidad de personas debe ser mayor a cero", exception.getMessage());

    }

    @Test
    public void noSePuedeOcuparMesaConMasPersonasQueSuCapacidad() {
        Mesa mesa = new Mesa(1, 4);

        ValidationException exception =
                assertThrows(ValidationException.class, () -> mesa.ocupar(5));

        assertEquals("La cantidad de personas supera la capacidad de la mesa", exception.getMessage());
    }

    @Test
    public void sePuedeLiberarMesaOcupada() {
        Mesa mesa = new Mesa(1, 4);
        mesa.ocupar(3);

        mesa.liberar();

        assertFalse(mesa.estaOcupada());
        assertEquals(0, mesa.getCantidadPersonas());
        assertNull(mesa.getPedido());
    }

    @Test
    public void noSePuedeLiberarMesaDesocupada() {
        Mesa mesa = new Mesa(1, 4);

        MesaException exception =
                assertThrows(MesaException.class, mesa::liberar);

        assertEquals("La mesa está desocupada", exception.getMessage());
    }

    @Test
    public void sePuedeAsignarPedidoAMesaOcupada() {
        RegistroPedidos registro=new RegistroPedidoArchivo("Registro_Costos.txt");
        Mesa mesa = new Mesa(1, 4);
        Pedido pedido = new Pedido(registro);
        mesa.ocupar(2);

        mesa.asignarPedido(pedido);

        assertSame(pedido, mesa.getPedido());
    }

    @Test
    public void noSePuedeAsignarPedidoANull() {
        Mesa mesa = new Mesa(1, 4);
        mesa.ocupar(2);

        ValidationException exception =
                assertThrows(ValidationException.class, () -> mesa.asignarPedido(null));

        assertEquals("El pedido no puede ser nulo", exception.getMessage());
    }

    @Test
    public void noSePuedeAsignarPedidoAMesaDesocupada() {
    RegistroPedidos registro=new RegistroPedidoArchivo("Registro_Costos.txt");
        Mesa mesa = new Mesa(1, 4);
        Pedido pedido = new Pedido(registro);

        MesaException exception =
                assertThrows(MesaException.class, () -> mesa.asignarPedido(pedido));

        assertEquals("La mesa está desocupada", exception.getMessage());
    }

    @Test
    public void noSePuedeAsignarUnSegundoPedidoALaMesa() {
        RegistroPedidos registro=new RegistroPedidoArchivo("Registro_Costos.txt");
        Mesa mesa = new Mesa(1, 4);
        Pedido pedido1 = new Pedido(registro);
        Pedido pedido2 = new Pedido(registro);
        mesa.ocupar(2);
        mesa.asignarPedido(pedido1);

        MesaException exception =
                assertThrows(MesaException.class, () -> mesa.asignarPedido(pedido2));

        assertEquals("La mesa ya tiene un pedido asignado", exception.getMessage());
    }

    @Test
    public void costoConsumidoSinPedidoEsCero() {
        Mesa mesa = new Mesa(1, 4);

        assertEquals(0, mesa.costoConsumido(), 0.001);
    }

    @Test
    public void costoConsumidoConPedidoRetornaCostoTotalDelPedido() {
        RegistroPedidos registro=new RegistroPedidoArchivo("Registro_Costos.txt");
        Mesa mesa = new Mesa(1, 4);
        Pedido pedido = new Pedido(registro);
        pedido.agregarItem(new Producto("Agua", 1000, TipoProducto.BEBIDA), 2);
        pedido.agregarItem(new Producto("Hamburguesa", 5000, TipoProducto.PLATO_PRINCIPAL), 1);
        pedido.confirmar(new Tarjeta(TipoTarjeta.VISA), Propina.DOS_PORCIENTO);

        mesa.ocupar(2);
        mesa.asignarPedido(pedido);

        assertEquals(7078.8, mesa.costoConsumido(), 0.001);
    }

    @Test
    public void restauranteSeCreaConDiezMesas() {
        Restaurante restaurante = new Restaurante();

        assertEquals(10, restaurante.cantidadMesas());
        assertEquals(10, restaurante.getMesas().size());
    }

    @Test
    public void restauranteBuscaMesaExistente() {
        Restaurante restaurante = new Restaurante();

        Mesa mesa = restaurante.buscarMesa(1);

        assertEquals(1, mesa.getNumero());
    }
    @Test
    public void restauranteRetornaSoloMesasLibres() {
        Restaurante restaurante = new Restaurante();

        restaurante.buscarMesa(1).ocupar(2);
        restaurante.buscarMesa(3).ocupar(4);

        assertEquals(8, restaurante.mesasLibres().size());
        assertTrue(restaurante.mesasLibres().stream().allMatch(Mesa::estaLibre));
    }

    @Test
    public void restauranteRetornaSoloMesasOcupadas() {
        Restaurante restaurante = new Restaurante();

        restaurante.buscarMesa(1).ocupar(2);
        restaurante.buscarMesa(3).ocupar(4);

        assertEquals(2, restaurante.mesasOcupadas().size());
        assertTrue(restaurante.mesasOcupadas().stream().allMatch(Mesa::estaOcupada));
    }

    @Test
    public void restaurantePuedeAsignarPedidoAMesa() {
        RegistroPedidos registro = new RegistroPedidoFake();
        Restaurante restaurante = new Restaurante();
        Pedido pedido = new Pedido(registro);

        restaurante.buscarMesa(1).ocupar(2);
        restaurante.asignarPedidoAMesa(1, pedido);

        assertSame(pedido, restaurante.buscarMesa(1).getPedido());
    }

    @Test
    public void totalFacturadoSumaElCostoConsumidoDeLasMesasOcupadasConPedido() {
        RegistroPedidos registro = new RegistroPedidoFake();
        Restaurante restaurante = new Restaurante();

        Pedido pedido1 = new Pedido(registro);
        pedido1.agregarItem(new Producto("Agua", 1000, TipoProducto.BEBIDA), 2);
        pedido1.agregarItem(new Producto("Hamburguesa", 5000, TipoProducto.PLATO_PRINCIPAL), 1);
        pedido1.confirmar(new Tarjeta(TipoTarjeta.VISA), Propina.DOS_PORCIENTO);

        Pedido pedido2 = new Pedido(registro);
        pedido2.agregarItem(new Producto("Gaseosa", 1500, TipoProducto.BEBIDA), 2);
        pedido2.agregarItem(new Producto("Pizza", 6000, TipoProducto.PLATO_PRINCIPAL), 1);
        pedido2.confirmar(new Tarjeta(TipoTarjeta.MASTERCARD), Propina.TRES_PORCIENTO);

        restaurante.buscarMesa(1).ocupar(2);
        restaurante.buscarMesa(2).ocupar(2);

        restaurante.asignarPedidoAMesa(1, pedido1);
        restaurante.asignarPedidoAMesa(2, pedido2);

        assertEquals(16225.2, restaurante.totalFacturado(), 0.001);
    }

    @Test
    public void buscarMesaDisponibleRetornaUnaMesaLibreQueSoportaLaCantidadDeComensales() {
        Restaurante restaurante = new Restaurante();

        restaurante.buscarMesa(1).ocupar(2);
        restaurante.buscarMesa(2).ocupar(2);

        Mesa mesaDisponible = restaurante.buscarMesaDisponible(4);

        assertTrue(mesaDisponible.estaLibre());
        assertTrue(mesaDisponible.puedeSentarse(4));
        assertEquals(3, mesaDisponible.getNumero());
    }

    @Test
    public void buscarMesaDisponibleLanzaExcepcionSiCantidadComensalesEsInvalida() {
        Restaurante restaurante = new Restaurante();

        ValidationException exception =
                assertThrows(ValidationException.class, () -> restaurante.buscarMesaDisponible(0));

        assertEquals("La cantidad de comensales debe ser mayor a cero", exception.getMessage());
    }

    @Test
    public void buscarMesaDisponibleLanzaExcepcionSiNoHayMesaParaEsaCantidad() {
        Restaurante restaurante = new Restaurante();

        IllegalStateException exception =
                assertThrows(IllegalStateException.class, () -> restaurante.buscarMesaDisponible(11));

        assertEquals("No hay mesa disponible para esa cantidad de comensales", exception.getMessage());
    }
}
