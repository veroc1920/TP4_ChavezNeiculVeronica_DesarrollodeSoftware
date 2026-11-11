package logistica.modelo;

public class EnvioEstandar extends Envio {
    
    public EnvioEstandar(int id, Cliente cliente) {
        super(id, cliente);
    }

    @Override
    public double calcularCostoEnvio() {
        return 1000; // Costo fijo para envíos estándar
    }
    
}
