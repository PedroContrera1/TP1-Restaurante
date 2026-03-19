package Entities;
public enum Propina {
    DOS_PORCIENTO(0.02),
    TRES_PORCIENTO(0.03),
    CINCO_PORCIENTO(0.05);

    private final double porcentaje;

    Propina(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public double getPorcentaje() {
        return porcentaje;
    }
}