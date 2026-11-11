package logistica.modelo;

public class EnvioExpress extends Envio {
    
    public EnvioExpress(int id, Cliente cliente) {
        super(id, cliente);
    }

    @Override
    public double calcularCostoEnvio() {
        return 1000 * 1.5; // 50% más caro que el envío estándar
    }
    
}
