package logistica.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//import logistica.enums.EstadoPaquete; - TP 2.1 sacamos esta importación porque no la vamos a usar
import logistica.enums.TipoMovimiento;

//import java.time.LocalDateTime; - TP 2.1 sacamos esta importación porque la fecha la toma el propio constructor.


//Creamos una clase abstracta que será padre de los diferentes envíos, usamos protected
// para que solo pueda ser accedida por las clases hijas.
public abstract class Envio {
    private static final int MAX_PAQUETES = 3;//TP 2.1 se agrega este atributo para que 3 sea una constante
    private int id; //TP4 cambiamos el tipo de id a int para que sea más fácil de manejar y se pueda hacer seguimiento numérico.
    private List<Paquete> paquetes;
    private List<SeguimientoMovimientos> historial; //se agregó el atributo historial para que se registren los movimientos
    private Cliente cliente; //se agregó el atributo cliente para que se pueda asociar un cliente a cada envío (corrección TP 2.1)
    //de cada envío, como despachar y recibir, y se pueda mostrar un resumen.
  
       
    //Constructor con parámetros, permite crear un envío con toda la información necesaria desde el inicio.
   /*  public Envio(String id) {
        this.id = id;
        this.paquetes = new ArrayList<>();
        this.historial = new ArrayList<>();//inicializa el historial como una lista para registrar los movimientos del envío.
    } */

    //Nuevo constructor que asocia el envío a un cliente, para que el cliente tenga un registro de sus envíos.
    public Envio(int id, Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El envío " + id + " debe tener un cliente asociado.");
        }
        this.id = id;
        this.cliente = cliente;
        this.paquetes = new ArrayList<>();
        this.historial = new ArrayList<>();
        cliente.agregarEnvio(this); //asocia el envío al cliente, para que el cliente tenga un registro de sus envíos.
    }

    //Modificación TP 2.1: reemplazamos este método
    /*public List<Paquete> getPaquetes() {
        return paquetes;
    }*/

//TP 2.1 cambio a protected para que solo puedan usarlo las clases hijas de Envio
    protected List<Paquete> getPaquetes() {
        return Collections.unmodifiableList(paquetes);//Cambia la lista a solo lectura para que no se pueda agregar paquetes después de despachar
    }
   
//TP 2.1 se modifica este método y se agregan comprobaciones antes de agregar paquetes
    public void agregarPaquete(Paquete paquete) {
        //if (paquetes.size() < 3) {
        //   paquetes.add(paquete);
        //} else {
        //    System.out.println("No se pueden agregar más paquetes a este envío.");
        if (!hayLugar()) {
            throw new IllegalStateException("El envío " + id + " ya tiene " + MAX_PAQUETES + " paquetes.");
        }
        if (!todaviaNoSalio()) {
            throw new IllegalStateException("El envío " + id + " ya fue despachado, no se pueden agregar paquetes");
        }
        paquetes.add(paquete);
    }
    public abstract double calcularCostoEnvio();

    /*public void iniciarEnvio() {
        System.out.println("Envío iniciado con ID: " + id);
        for (Paquete p : paquetes) {
            //p.prepararEnvio(); lo quito para evitar problemas con la validación del estado del paquete, que se agregó con la excepción en distribuir()
            p.distribuir();
        }
    }*/ //Quito este método ya que no es necesario, lo reemplaza el método despachar() de la clase Sucursal.

    //TP 2.1 Se elimina este método, se reemplaza con entregar(Sucursal sucursal), que mantiene parte de la lógica
   /* public void finalizarEnvio() {
        System.out.println("Envío finalizado con ID: " + id);
        for (Paquete p : paquetes) {
            p.recibir();
        }
    }*/


//Modificación de los métodos recibir y despachar para que la sucursal sea la que inicia el movimiento
//MODIFICACIÓN TP 2.1 se agregan validaciones
    public void recibir(Sucursal sucursal) {
        //for (Paquete p : paquetes) {
        if (estaFinalizado()) { //agrego validación para que no se pueda recibir un paquete que ya fue entregado o devuelto.
            throw new IllegalStateException("No se puede recibir el envio " + id + " porque finalizó su recorrido");
        }
        if (!puedeSerRecibido()) {
            throw new IllegalStateException("El envío " + id + " no puede recibirse: primero debe ser despachado por la sucursal donde se encuentra.");
        }
        registrarMovimiento(TipoMovimiento.RECEPCION, sucursal);
        //System.out.println("Envío " + id + " recibido por la sucursal " + sucursal.getIdSucursal() + " - " + sucursal.getUbicacion() + ".");
        //historial.add(new SeguimientoMovimientos(LocalDateTime.now(), "Envío recibido", sucursal)); //agrega un nuevo movimiento al historial del envío.
    }

    //MODIFICACIÓN TP 2.1: se agregan validaciones para que si no tiene paquetes, está finalizado o no se recibió en la sucursal no se pueda despachar
    /* public void despachar(Sucursal sucursal) {
        if (paquetes.isEmpty()) { //validación para evitar que un envío no tenga paquetes.
            throw new IllegalStateException("No se puede despachar el envío " + id + " porque no tiene paquetes.");
        }
        for (Paquete p : paquetes) { // for para recorrer la lista de paquetes, llama al método distribuir(), para que actualice el estado de los paquetes.
            p.distribuir();
        }
        System.out.println("Envío " + id + " despachado por la sucursal " + sucursal.getIdSucursal() + " - " + sucursal.getUbicacion() + ".");
        historial.add(new SeguimientoMovimientos(LocalDateTime.now(), "Envío despachado", sucursal)); //agrega un nuevo movimiento al historial del envío.
    } */
   
    //MODIFICACIÓN TP 2.1: se agregan validaciones para que si no tiene paquetes, está finalizado o no se recibió en la sucursal no se pueda despachar
    public void despachar(Sucursal sucursal) {
        if (!tienePaquetes()) {
            throw new IllegalStateException("No se puede despachar el envío " + id + " porque no tiene paquetes.");
        }
        if (estaFinalizado()) {
            throw new IllegalStateException("El envío " + id + " ya fue entregado.");
        }
        if (!estaEnSucursal(sucursal)) {
            throw new IllegalStateException("El envío " + id + " no se encuentra en la sucursal " + sucursal.getIdSucursal() + "." + ubicacionActual());
        }
        for (Paquete p : paquetes) {
            p.distribuir();
        }
        registrarMovimiento(TipoMovimiento.DESPACHO, sucursal);
    }
    
    //MODIFICACIÓN TP 2.1: se agrega método entregar con validaciones para que si no está en sucursal no se pueda registrar como entregado
    public void entregar(Sucursal sucursal) {
        if (todaviaNoSalio()) {
            throw new IllegalStateException("El envío " + id + " todavía no fue despachado, no puede entregarse.");
        }
        if (!estaEnSucursal(sucursal)) {
            throw new IllegalStateException("El envío " + id + " no se encuentra en la sucursal " + sucursal.getIdSucursal() + "."); 
        }
        for (Paquete p : paquetes) {
            p.entregar();
        }
        registrarMovimiento(TipoMovimiento.ENTREGA, sucursal);
    }

    //método para acceder al id del envío desde otras clases como Sucursal...
    public int getId() {
        return id;
    }

    //Nuevo método para acceder al cliente asociado al envío, para que se pueda mostrar el historial de envíos de un cliente.(Corrección del TP 2.1)
    public Cliente getCliente() {
        return cliente;
    }

    //MODIFICACIÓN TP 2.1 se crea nuevo método boolean para que devuelva si un envío contiene al menos un paquete
    public boolean tienePaquetes() {
        return !paquetes.isEmpty();
    }

    //MODIFICACIÓN TP 2.1: estos métodos devuelven datos, no los imprimen en consola, y se pueden reutilizar
    public List<SeguimientoMovimientos> obtenerHistorial() {
        return Collections.unmodifiableList(historial);
    }

    public List<Sucursal> obtenerSucursalesRecorridas() {
        List<Sucursal> recorridas = new ArrayList<>();
        for (SeguimientoMovimientos m : historial) {
            if (!recorridas.contains(m.getSucursal())) {
                recorridas.add(m.getSucursal());
            }
        }
        return recorridas;
    }

    public SeguimientoMovimientos obtenerUltimoMovimiento() {
        if (historial.isEmpty()) {
            throw new IllegalStateException("El envío " + id + " no tiene movimientos registrados.");
        }
        return historial.get(historial.size() - 1);
    }

    //MODIFICACIÓN TP 2.1: reglas de negocio que se agregan, métodos privados para realizar las comprobaciones
    private boolean hayLugar() {
        return paquetes.size() < MAX_PAQUETES;
    }

    private boolean todaviaNoSalio() {
        for (SeguimientoMovimientos m : historial) {
            if (m.esDespacho()) {
                return false;
            }
        }
        return true;
    }

    private boolean estaFinalizado() {
        for (Paquete p : paquetes) {
            if (p.estaFinalizado()) {
                return true;
            }
        }
        return false;
    }

    private boolean puedeSerRecibido() {
        return historial.isEmpty() || obtenerUltimoMovimiento().esDespacho();
    }

    private boolean estaEnSucursal(Sucursal sucursal) {
        return !historial.isEmpty() && obtenerUltimoMovimiento().esRecepcionEn(sucursal);
    }

    private String ubicacionActual() {
        if (historial.isEmpty()) {
            return "Todavía no fue recibido en ninguna sucursal.";
        }
        return "Último movimiento: " + obtenerUltimoMovimiento();
    }

    private void registrarMovimiento(TipoMovimiento tipo, Sucursal sucursal) {
        historial.add(new SeguimientoMovimientos(tipo, sucursal));
    }

    @Override
    public String toString() {
        return "Envio{" +
                "id=" + id +
                ", Costo=" + calcularCostoEnvio() +
                ", paquetes=" + paquetes.size() +
                '}';
    }
}


    /* -----MODIFICACIÓN TP 2.1: Estos métodos ya no lo vamos a usar porque se crean otros métodos que devuelven estos datos
     //Nuevo método para mostrar el historial del envío
    //  public void mostrarHistorial() {
    //     System.out.println("Historial del envío " + id + ":");
    //     for (SeguimientoMovimientos m : historial) {
    //         System.out.println(m);
    //     }
    //  } 

     //Nuevo método que informa las sucursales por las que pasó el envío:
     public void mostrarSucursalesRecorridas() {
        List<Sucursal> recorridas = new ArrayList<>();
        for (SeguimientoMovimientos m : historial) {
            Sucursal s = m.getSucursal();
            if (!recorridas.contains(s)) {
                recorridas.add(s);
            }
        }
        System.out.println("Sucursales por las que pasó el envío " + id + ":");
        for (Sucursal s : recorridas) {
            System.out.println("- " + s.getIdSucursal() + " - " + s.getUbicacion());
        }
     }

     //Nuevo método para mostrar el último movimiento que tuvo el envío.
     public void mostrarUltimoMovimiento() {
        if (historial.isEmpty()) {
            System.out.println("El envío " + id + " no tiene movimientos registrados.");
        } else {
            SeguimientoMovimientos ultimo = historial.get(historial.size() - 1);
            System.out.println("Último movimiento del envío " + id + ":" + ultimo);
        }
     }
 */
    
