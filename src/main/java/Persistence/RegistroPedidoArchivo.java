package Persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroPedidoArchivo implements RegistroPedidos{
    private static final DateTimeFormatter FORMATO=DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final File archivo;

    public RegistroPedidoArchivo (String nombre_archivo){
        this.archivo=new File(nombre_archivo);
    }

    public void guardarCosto(double monto) {
        String linea = String.format(
                "%s || %s%n",
                LocalDateTime.now().format(FORMATO),
                monto
        );

        try {
            File directorioPadre = archivo.getParentFile();
            if (directorioPadre != null && !directorioPadre.exists()) {
                directorioPadre.mkdirs();
            }

            try (FileWriter writer = new FileWriter(archivo, true)) {
                writer.write(linea);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el costo en archivo", e);
        }
    }
}
