package Entities;

import Exceptions.NotFoundException;


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
        return mesas.stream()
                .filter(mesa -> mesa.getNumero() == numero)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No existe una mesa con ese número"));
    }

    public List<Mesa> mesasLibres() {
        return mesas.stream()
                .filter(Mesa::estaLibre)
                .toList();
    }

    public List<Mesa> mesasOcupadas() {
        return mesas.stream()
                .filter(Mesa::estaOcupada)
                .toList();
    }

    public void asignarPedidoAMesa(int numeroMesa, Pedido pedido) {
        buscarMesa(numeroMesa).asignarPedido(pedido);
    }

    public double totalFacturado() {
        return mesas.stream()
                .mapToDouble(Mesa::costoConsumido)
                .sum();
    }

    public Mesa buscarMesaDisponible(int cantidadComensales) {
        return mesas.stream()
                .filter(Mesa::estaLibre)
                .filter(mesa -> mesa.puedeSentarse(cantidadComensales))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No hay mesa disponible para esa cantidad de comensales"));
    }


}
