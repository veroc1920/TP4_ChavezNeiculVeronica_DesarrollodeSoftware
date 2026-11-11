package logistica.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cliente {
    private int dni; //TP4: DNI numérico.
    private String nombreApellido;
    private String direccion;
    private String telefono;
    private List<Envio> envios;

    public Cliente(int dni, String nombreApellido, String direccion, String telefono) {
        this.dni = dni;
        this.nombreApellido = nombreApellido;
        this.direccion = direccion;
        this.telefono = telefono;
        this.envios = new ArrayList<>(); //TP 2.1: se agrega este array, permite eliminar los if (envios == null)
    }

    // public void realizarEnvio(Envio envio) {
    //     /*if (envios == null) {
    //         envios = new ArrayList<>();
    //     }*/ //MODIFICACION TP 2.1: se saca este if y se reemplaza por esta validación que lanza una excepción si el envío está vacío
    //     if (!envio.tienePaquetes()) {
    //         throw new IllegalArgumentException("No se puede registrar el envío " + envio.getId() + " porque no tiene paquetes.");
    //     }
    //     envios.add(envio);
    // }
    
    // Se reemplaza el método realizarEnvio por agregarEnvio, que agrega un envío a la lista de envíos del cliente si no está ya presente.
    void agregarEnvio(Envio envio) {
        if (!envios.contains(envio)) {
            envios.add(envio);
        }
        
    }

    //MODIFICACIÓN TP 2.1: se reemplaza este método que imprime datos por otro que devuelve directamente el dato cuando se lo solicita:
    //para mostrar los envíos que realiza el cliente
    /* public void mostrarEnvios() {
        if (envios == null || envios.isEmpty()) {
            System.out.println("No hay envíos realizados por el cliente " + nombreApellido);
            return;
        }
        System.out.println("Envíos realizados por el cliente " + nombreApellido + ":");
        for (Envio envio : envios) {
            System.out.println(envio);
        }
    } */

    public List<Envio> obtenerEnvios() {
        return Collections.unmodifiableList(envios);
    }

    //validación para buscar un envío, y evitar buscar envíos no realizados por el cliente.
    /* public Envio buscarEnvio(String idEnvio) {
        if (envios != null) {
            for (Envio e : envios) {
                if (e.getId().equals(idEnvio)) {
                    return e;
                }
            }
        } */
    
        //return null; // Retorna null si no se encuentra el envío. Luego de la prueba, falló en la validación, cambio por una excepción.
        //throw new IllegalArgumentException("El envío con ID " + idEnvio + " no existe para el cliente " + nombreApellido);
    
        //Modificación TP4: sacamos el trim() y el equalsIgnoreCase porque ya no hacen falta.
    public Envio buscarEnvio(int codigo) {
        for (Envio e : envios) {
            if (e.getId() == codigo) { 
                return e;
            }
        }
        throw new IllegalArgumentException("No se encontró ningún envío con el código " + codigo + " para el cliente " + nombreApellido + ".");
    
    }

    public int getDni() {
        return dni;
    }

    @Override
    public String toString() {//para imprimir los datos del cliente en caso de ser necesario.
        return "Cliente{" +
                "dni=" + dni +
                ", nombreApellido='" + nombreApellido + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", envios=" + (envios != null ? envios.size() : 0) +
                '}';
    }

    
    
}
