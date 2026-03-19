import Entities.Mesa;
import Entities.Pedido;
import Entities.Producto;
import Entities.Propina;
import Entities.Restaurante;
import Entities.Tarjeta;
import Entities.TipoProducto;
import Entities.TipoTarjeta;
import Exceptions.MesaException;
import Exceptions.ValidationException;
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
        Mesa mesa = new Mesa(1, 4);
        Pedido pedido = new Pedido();
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
        Mesa mesa = new Mesa(1, 4);
        Pedido pedido = new Pedido();

        MesaException exception =
                assertThrows(MesaException.class, () -> mesa.asignarPedido(pedido));

        assertEquals("La mesa está desocupada", exception.getMessage());
    }

    @Test
    public void noSePuedeAsignarUnSegundoPedidoALaMesa() {
        Mesa mesa = new Mesa(1, 4);
        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();
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
        Mesa mesa = new Mesa(1, 4);
        Pedido pedido = new Pedido();
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
}
