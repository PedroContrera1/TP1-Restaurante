package Entities;

import Exceptions.PedidoException;
import Exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private final List<ItemPedido> items;
    private boolean confirmado;
    private Tarjeta tarjeta;
    private Propina propina;

    public Pedido() {
        this.items = new ArrayList<>();
        this.confirmado = false;
    }

    public void agregarItem(Producto producto, int cantidad) {
        validarNoConfirmado();
        items.add(new ItemPedido(producto, cantidad));
    }

    public void confirmar(Tarjeta tarjeta, Propina propina) {
        validarNoConfirmado();
        validarPedidoConItems();
        validarTarjeta(tarjeta);
        validarPropina(propina);

        this.tarjeta = tarjeta;
        this.propina = propina;
        this.confirmado = true;
    }

    private void validarNoConfirmado() {
        if (confirmado) {
            throw new PedidoException("El pedido ya fue confirmado");
        }
    }

    private void validarPedidoConItems() {
        if (items.isEmpty()) {
            throw new PedidoException("No se puede confirmar un pedido vacío");
        }
    }

    private void validarTarjeta(Tarjeta tarjeta) {
        if (tarjeta == null) {
            throw new ValidationException("La tarjeta no puede ser nula");
        }
    }

    private void validarPropina(Propina propina) {
        if (propina == null) {
            throw new ValidationException("La propina no puede ser nula");
        }
    }

    private void validarPedidoConfirmado() {
        if (!confirmado) {
            throw new PedidoException("El pedido aún no fue confirmado");
        }
    }

    public double totalBebidas() {
        return items.stream()
                .filter(item -> item.getProducto().getTipo() == TipoProducto.BEBIDA)
                .mapToDouble(ItemPedido::subtotal)
                .sum();
    }

    public double totalPlatos() {
        return items.stream()
                .filter(item -> item.getProducto().getTipo() == TipoProducto.PLATO_PRINCIPAL)
                .mapToDouble(ItemPedido::subtotal)
                .sum();
    }

    public double subtotalComida() {
        return totalBebidas() + totalPlatos();
    }

    public double descuento() {
        validarPedidoConfirmado();
        return tarjeta.calcularDescuento(totalBebidas(), totalPlatos());
    }

    public double totalConDescuento() {
        validarPedidoConfirmado();
        return subtotalComida() - descuento();
    }

    public double montoPropina() {
        validarPedidoConfirmado();
        return totalConDescuento() * propina.getPorcentaje();
    }

    public double costoTotal() {
        validarPedidoConfirmado();
        return totalConDescuento() + montoPropina();
    }

    public boolean estaConfirmado() {
        return confirmado;
    }

    public int cantidadItems() {
        return items.size();
    }
}
