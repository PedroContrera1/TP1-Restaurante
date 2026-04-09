import Persistence.RegistroPedidos;

public class RegistroPedidoFake implements RegistroPedidos {
    @Override
    public void guardarCosto(double costo) {

    }
    public boolean fueInvocado() {
        boolean invocado = false;
        return invocado =true;
    }
}