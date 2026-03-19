package Entities;

import Exceptions.NotFoundException;
import Exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class Restaurante {
    private static final int CANTIDAD_ESPERADA_DE_MESAS = 10;
    private final List<Mesa> mesas;

    public Restaurante() {
        this.mesas = new ArrayList<>();
        crearMesas();
        validarCantidadDeMesas();
        validarNumerosNoRepetidos();
    }

    private void crearMesas() {
        mesas.add(new Mesa(1, 2));
        mesas.add(new Mesa(2, 2));
        mesas.add(new Mesa(3, 4));
        mesas.add(new Mesa(4, 4));
        mesas.add(new Mesa(5, 4));
        mesas.add(new Mesa(6, 6));
        mesas.add(new Mesa(7, 6));
        mesas.add(new Mesa(8, 8));
        mesas.add(new Mesa(9, 8));
        mesas.add(new Mesa(10, 10));
    }

    private void validarCantidadDeMesas() {
        if (mesas.size() != CANTIDAD_ESPERADA_DE_MESAS) {
            throw new IllegalStateException("El restaurante debe tener exactamente 10 mesas");
        }
    }

    private void validarNumerosNoRepetidos() {
        for (int i = 0; i < mesas.size(); i++) {
            for (int j = i + 1; j < mesas.size(); j++) {
                if (mesas.get(i).getNumero() == mesas.get(j).getNumero()) {
                    throw new IllegalStateException("No puede haber números de mesa repetidos");
                }
            }
        }
    }

    public List<Mesa> getMesas() {
        return new ArrayList<>(mesas);
    }

    public int cantidadMesas() {
        return mesas.size();
    }

    public Mesa buscarMesa(int numero) {
        for (Mesa mesa : mesas) {
            if (mesa.getNumero() == numero) {
                return mesa;
            }
        }
        throw new NotFoundException("No existe una mesa con ese número");
    }

    public List<Mesa> mesasLibres() {
        List<Mesa> libres = new ArrayList<>();
        for (Mesa mesa : mesas) {
            if (mesa.estaLibre()) {
                libres.add(mesa);
            }
        }
        return libres;
    }

    public List<Mesa> mesasOcupadas() {
        List<Mesa> ocupadas = new ArrayList<>();
        for (Mesa mesa : mesas) {
            if (mesa.estaOcupada()) {
                ocupadas.add(mesa);
            }
        }
        return ocupadas;
    }

    public void asignarPedidoAMesa(int numeroMesa, Pedido pedido) {
        buscarMesa(numeroMesa).asignarPedido(pedido);
    }

    public double totalFacturado() {
        double total = 0;
        for (Mesa mesa : mesas) {
            total += mesa.costoConsumido();
        }
        return total;
    }

    public Mesa buscarMesaDisponible(int cantidadComensales) {
        validarCantidadComensales(cantidadComensales);

        for (Mesa mesa : mesas) {
            if (mesa.estaLibre() && mesa.puedeSentarse(cantidadComensales)) {
                return mesa;
            }
        }

        throw new IllegalStateException("No hay mesa disponible para esa cantidad de comensales");
    }

    private void validarCantidadComensales(int cantidadComensales) {
        if (cantidadComensales <= 0) {
            throw new ValidationException("La cantidad de comensales debe ser mayor a cero");
        }
    }
}
