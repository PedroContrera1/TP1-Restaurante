package Entities;

import Exceptions.MesaException;
import Exceptions.ValidationException;

public class Mesa {
    private final int numero;
    private final int capacidad;
    private boolean ocupada;
    private int cantidadPersonas;
    private Pedido pedido;

    public Mesa(int numero, int capacidad) {
        validarNumero(numero);
        validarCapacidad(capacidad);
        this.numero = numero;
        this.capacidad = capacidad;
        this.ocupada = false;
        this.cantidadPersonas = 0;
        this.pedido = null;
    }

    private void validarNumero(int numero) {
        if (numero <= 0) {
            throw new ValidationException("El número de mesa debe ser mayor a cero");
        }
    }

    private void validarCapacidad(int capacidad) {
        if (capacidad <= 0) {
            throw new ValidationException("La capacidad de la mesa debe ser mayor a cero");
        }
    }

    private void validarMesaLibre() {
        if (!estaLibre()) {
            throw new MesaException("La mesa ya está ocupada");
        }
    }

    private void validarMesaOcupada() {
        if (estaLibre()) {
            throw new MesaException("La mesa está desocupada");
        }
    }

    private void validarCantidadPersonas(int cantidadPersonas) {
        if (cantidadPersonas <= 0) {
            throw new ValidationException("La cantidad de personas debe ser mayor a cero");
        }
        if (cantidadPersonas > capacidad) {
            throw new ValidationException("La cantidad de personas supera la capacidad de la mesa");
        }
    }

    public int getNumero() {
        return numero;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public boolean estaOcupada() {
        return ocupada;
    }

    public boolean estaLibre() {
        return !ocupada;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public boolean puedeSentarse(int cantidadComensales) {
        return cantidadComensales > 0 && cantidadComensales <= capacidad;
    }

    public void ocupar(int cantidadPersonas) {
        validarCantidadPersonas(cantidadPersonas);
        validarMesaLibre();
        this.ocupada = true;
        this.cantidadPersonas = cantidadPersonas;
    }

    public void asignarPedido(Pedido pedido) {
        validarMesaOcupada();
        validarPedido(pedido);
        validarMesaSinPedido();
        this.pedido = pedido;
    }

    private void validarPedido(Pedido pedido) {
        if (pedido == null) {
            throw new ValidationException("El pedido no puede ser nulo");
        }
    }

    private void validarMesaSinPedido() {
        if (this.pedido != null) {
            throw new MesaException("La mesa ya tiene un pedido asignado");
        }
    }

    public Pedido getPedido() {
        return pedido;
    }

    public double costoConsumido() {
        if (pedido == null) {
            return 0;
        }
        return pedido.costoTotal();
    }

    public void liberar() {
        validarMesaOcupada();
        this.ocupada = false;
        this.cantidadPersonas = 0;
        this.pedido = null;
    }
}
