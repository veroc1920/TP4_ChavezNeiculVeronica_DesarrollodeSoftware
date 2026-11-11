package logistica.modelo;

import logistica.enums.EstadoPaquete;

public class Paquete {
    // Atributos de la clase Paquete, son privados para que puedan accederse solo a través 
    // de los métodos de la clase y proteger los datos.
    private String id;
    private String destinatario;
    private String direccionEnvio;
    private double peso;
    private EstadoPaquete estado;

    
    //Constructor con parámetros
    public Paquete(String id, String destinatario, String direccionEnvio, double peso) {
        this.id = id;
        this.destinatario = destinatario;
        this.direccionEnvio = direccionEnvio;
        this.peso = peso;
        this.estado = EstadoPaquete.EN_PREPARACION;
    }

    //Getters, permite ver la info pero no cambiarla directamente
    //No hay setters porque no tiene sentido realizar cambios directos a los atributos, 
    // el estado se cambia a través de los métodos específicos.
    
    public double getPeso() {
        return peso;
    }
/*--------------MODIFICACIONES TP 2.1------------------------
    //Getter para ver el estado del paquete desde la clase Envio, para validar si se puede recibir o despachar el paquete.
    public EstadoPaquete getEstado() {
        return estado;
    }
    
   

    //Métodos que cambian el estado del paquete:
    public void recibir() {//agrego validación para que no se pueda recibir un paquete que no esté en distribución
        if (this.estado != EstadoPaquete.EN_DISTRIBUCION) {
            throw new IllegalStateException("El paquete no está en distribución para ser recibido.");
        }
        this.estado = EstadoPaquete.ENTREGADO;
    }
    public void prepararEnvio() {
        this.estado = EstadoPaquete.EN_PREPARACION;
    }
    public void distribuir() {
        if (this.estado == EstadoPaquete.EN_DISTRIBUCION) {
            return; // Agrego esta condición:Si el paquete ya está en distribución y pasó por otra sucursal, no se hace nada.
        }
        if (this.estado != EstadoPaquete.EN_PREPARACION) {
           throw new IllegalStateException("El paquete no está en preparación para ser distribuido.");
        } //excepción si el paquete no está en preparación, para evitar errores de estado.
        this.estado = EstadoPaquete.EN_DISTRIBUCION;
    }
    public void devolver() {//validación para evitar devolver un paquete que no esté en distribución
        if (this.estado != EstadoPaquete.EN_DISTRIBUCION) {
            throw new IllegalStateException("El paquete no está en distribución para ser devuelto.");
        }
        this.estado = EstadoPaquete.DEVUELTO;
    }
    //Agrego un método para verificar si el recorrido del paquete está finalizado, si ya fue entregado o devuelto.
    public boolean estaFinalizado() {
        return this.estado == EstadoPaquete.ENTREGADO || this.estado == EstadoPaquete.DEVUELTO;
    }*/
// MÉTODOS QUE CAMBIAN EL ESTADO DEL PAQUETE, USANDO EL ENUM CREADO EN ESTADOPAQUETE.java
    public void distribuir() {
        this.estado = this.estado.distribuir();
    }

    public void entregar() {
        this.estado = this.estado.entregar();
    }

    public void devolver() {
        this.estado = this.estado.devolver();
    }

    public boolean estaFinalizado() {
        return this.estado.esFinal();
    }


    @Override
    public String toString() {
        return "Paquete{" +
                "id='" + id + '\'' +
                ", destinatario='" + destinatario + '\'' +
                ", direccionEnvio='" + direccionEnvio + '\'' +
                ", peso=" + peso +
                ", estado=" + estado +
                '}';
    }
}
