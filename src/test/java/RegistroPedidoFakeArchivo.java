
import Persistence.RegistroPedidos;

public class RegistroPedidoFakeArchivo implements RegistroPedidos {
    private boolean invocado;
    private double ultimoMontoGuardado;

    public RegistroPedidoFakeArchivo() {
        this.invocado = false;
        this.ultimoMontoGuardado = 0;
    }

    @Override
    public void guardarCosto(double monto) {
        this.invocado = true;
        this.ultimoMontoGuardado = monto;
    }

    public boolean fueInvocado() {
        return invocado;
    }

    public double getUltimoMontoGuardado() {
        return ultimoMontoGuardado;
    }
}
