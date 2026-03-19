package Entities;

import Exceptions.ValidationException;

public class ItemPedido {
    private final Producto producto;
    private final int cantidad;

    public ItemPedido(Producto producto, int cantidad) {
        validarProducto(producto);
        validarCantidad(cantidad);
        this.producto = producto;
        this.cantidad = cantidad;
    }

    private void validarProducto(Producto producto) {
        if (producto == null) {
            throw new ValidationException("El producto no puede ser nulo");
        }
    }

    private void validarCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new ValidationException("La cantidad debe ser mayor a cero");
        }
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double subtotal() {
        return producto.getPrecio() * cantidad;
    }
}
