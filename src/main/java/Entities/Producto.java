package Entities;

import Exceptions.ValidationException;

public class Producto {
    private final String nombre;
    private final double precio;
    private final TipoProducto tipo;

    public Producto(String nombre, double precio, TipoProducto tipo) {
        validarNombre(nombre);
        validarPrecio(precio);
        validarTipoProducto(tipo);
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new ValidationException("El nombre no puede ser nulo ni vacío");
        }
    }

    private void validarPrecio(double precio) {
        if (precio <= 0) {
            throw new ValidationException("El precio debe ser mayor a 0");
        }
    }

    private void validarTipoProducto(TipoProducto tipo) {
        if (tipo == null) {
            throw new ValidationException("El tipo de producto no puede ser nulo");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public TipoProducto getTipo() {
        return tipo;
    }
}
