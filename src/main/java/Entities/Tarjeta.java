package Entities;

import Exceptions.ValidationException;

public class Tarjeta {
    private final TipoTarjeta tipo;

    public Tarjeta(TipoTarjeta tipo) {
        validarTipo(tipo);
        this.tipo = tipo;
    }

    private void validarTipo(TipoTarjeta tipo) {
        if (tipo == null) {
            throw new ValidationException("El tipo de tarjeta no puede ser nulo");
        }
    }

    public TipoTarjeta getTipo() {
        return tipo;
    }

    public double calcularDescuento(double totalBebidas, double totalPlatos) {
        validarTotales(totalBebidas, totalPlatos);

        return switch (tipo) {
            case VISA -> totalBebidas * 0.03;
            case MASTERCARD -> totalPlatos * 0.02;
            case COMARCA_PLUS -> (totalBebidas + totalPlatos) * 0.02;
            case VIEDMA -> 0;
        };
    }

    private void validarTotales(double totalBebidas, double totalPlatos) {
        if (totalBebidas < 0 || totalPlatos < 0) {
            throw new ValidationException("Los totales no pueden ser negativos");
        }
    }
}
