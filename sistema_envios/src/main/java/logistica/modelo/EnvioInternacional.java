package logistica.modelo;

public class EnvioInternacional extends Envio {
    
    private String paisDestino;
    
    public EnvioInternacional(int id, Cliente cliente, String paisDestino) {
        super(id, cliente);
        this.paisDestino = paisDestino;
    }

    public String getPaisDestino() {
        return paisDestino;
}

    @Override
    public double calcularCostoEnvio() {
        double costoBase = 1000; // Costo base para envíos internacionales
        double costoPorKilo = calcularCostoPorKilo();
        return costoBase + costoPorKilo;  
    }

    private double calcularCostoPorKilo() { //es privado porque es un cálculo interno, no es necesario que otras clases accedan.
        double totalKilos = 0;
        for (Paquete p : getPaquetes()) {
            totalKilos += p.getPeso();
        }
        return totalKilos * 200; // Costo adicional por kilo
    }

}
