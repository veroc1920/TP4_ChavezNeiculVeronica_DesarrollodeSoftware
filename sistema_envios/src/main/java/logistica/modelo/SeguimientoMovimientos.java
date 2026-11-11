package logistica.modelo;
import java.time.LocalDateTime; //importa la clase LocalDateTime para registrar la fecha y hora de los movimientos
import java.time.format.DateTimeFormatter;
import logistica.enums.TipoMovimiento; //importa el enum TipoMovimiento para definir los tipos de movimientos que puede tener un envío

//Modificaciones TP 2.1: agrego atributo final para que un movimiento que ya pasó no pueda cambiarse.
public class SeguimientoMovimientos {
    private final LocalDateTime fechaHora;
    //private final String descripcion; sacamos el string, usamos el enum
    private final TipoMovimiento tipo;
    private final Sucursal sucursal;

    /*  Este método se reemplaza (TP 2.1)
    public SeguimientoMovimientos(LocalDateTime fechaHora, String descripcion, Sucursal sucursal) {
        this.fechaHora = fechaHora;
        this.descripcion = descripcion;
        this.sucursal = sucursal;
    }*/

    public SeguimientoMovimientos(TipoMovimiento tipo, Sucursal sucursal) {
        this.fechaHora = LocalDateTime.now();
        this.tipo = tipo;
        this.sucursal = sucursal;
    }

    // Getters para acceder a los atributos de esta clase desde otras.
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /*TP 2.1 se reeemplaza este getter
    public String getDescripcion() {
        return descripcion;
    }*/

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

//TP 2.1 creamos dos métodos nuevos para que responda el movimiento en lugar de que Envio.java pida el tipo y compare
    public boolean esDespacho() {
        return tipo == TipoMovimiento.DESPACHO;
    }

    public boolean esRecepcionEn(Sucursal s) {
        return tipo == TipoMovimiento.RECEPCION && sucursal == s;
    }

//Cambios TP 2.1
    //@Override
  /*   public String toString() {
     return "SeguimientoMovimientos{" +
                "fechaHora=" + fechaHora +
                ", descripcion='" + descripcion + '\'' +
                ", sucursal=" + sucursal.getIdSucursal() + " - " + sucursal.getUbicacion() +
                '}';
    } */

    @Override 
    public String toString() {
        return fechaHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + " - " + tipo + " - " + sucursal.getIdSucursal() + " " + sucursal.getUbicacion();
    }
}