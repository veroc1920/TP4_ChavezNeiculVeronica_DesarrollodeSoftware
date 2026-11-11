package logistica.modelo;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Sucursal {
    private String idSucursal;
    private String ubicacion;
    private Queue<Envio> pendientesDespacho; //TP4: cola de envíos pendientes de despacho

    public Sucursal(String idSucursal, String ubicacion) {
        this.idSucursal = idSucursal;
        this.ubicacion = ubicacion;
        this.pendientesDespacho = new LinkedList<>();//TP4: inicialización de la cola de envíos pendientes de despacho
    }
//MODIFICACIÓN TP 2.1: Se modifican los métodos para que no impriman datos en consola, solo mostrará los datos App.java
    //En el método para despachar y recibir envíos, con this se referencia la propia sucursal 
   /*  public void despacharEnvio(Envio envio) {
        //System.out.println("Sucursal " + idSucursal + " - " + ubicacion + " ha despachado el envío con ID: " + envio.getId());
        envio.despachar(this);
    } */ //TP4: se elimina el método despacharEnvio, ya que ahora se reemplazó por despachar(int cantidad), que respeta el orden de la cola.

    // TP4: despacha como máximo "cantidad" envíos, respetando el orden de llegada
    public List<Envio> despachar(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a despachar debe ser mayor a 0.");
        }
        if (pendientesDespacho.isEmpty()) {
            throw new IllegalStateException("La sucursal " + idSucursal + " no tiene envíos pendientes de despacho.");
        }
        List<Envio> despachados = new ArrayList<>();
        while (despachados.size() < cantidad && !pendientesDespacho.isEmpty()) {
            Envio envio = pendientesDespacho.poll(); // saca el primero de la cola
            envio.despachar(this); // la acción la sigue haciendo Envio
            despachados.add(envio);
        }
        return despachados;
    }

    public void recibirEnvio(Envio envio) {
        //System.out.println("Sucursal " + idSucursal + " - " + ubicacion + " ha recibido el envío con ID: " + envio.getId());
        envio.recibir(this); //primero valida y registra el movimiento.
        pendientesDespacho.add(envio); //TP4: se agrega el envío a la cola de pendientes de despacho
    }

    //MODIFICACIÓN TP 2.1: Se agrega método entregarEnvio
    public void entregarEnvio(Envio envio) {
        envio.entregar(this);
        pendientesDespacho.remove(envio); //TP4: llegó a destino, se elimina el envío de la cola de pendientes de despacho
    }
   
    //getters para acceder a la información de la sucursal desde otras clases como Envio...
    public String getIdSucursal() {
        return idSucursal;
    }
    public String getUbicacion() {
        return ubicacion;
    }

    @Override
    public String toString() {
        return idSucursal + " - " + ubicacion;
    }
}