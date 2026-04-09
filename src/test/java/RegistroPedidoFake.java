import Persistence.RegistroPedidos;

public class RegistroPedidoFake implements RegistroPedidos {
    private boolean invocado=false;
    @Override
    public void guardarCosto(double costo) {

    }
    public boolean fueInvocado() {
        return this.invocado=true;
    }
}