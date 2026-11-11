package logistica.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class RegistroCentral {
    private List<Cliente> clientes;
    private List<Sucursal> sucursales;
    private Map<Integer, Envio> envios; // TP4: clave: código de seguimiento, valor: el envío.

    public RegistroCentral() {
        this.clientes = new ArrayList<>();
        this.sucursales = new ArrayList<>();
        this.envios = new HashMap<>(); // TP4: inicialización del mapa de envíos.
    }

    public void registrarCliente(Cliente c) {
        if (existeCliente(c.getDni())) { //el control de repetidos compara por DNI y no por "mismo objeto".
            throw new IllegalArgumentException("El cliente " + c.getDni() + " ya está registrado.");            
        }
        clientes.add(c);
    }

    private boolean existeCliente(int dni) {
        for (Cliente c : clientes) {
            if (c.getDni() == dni) {
                return true;
            }
        }
        return false;
    }

    public List<Cliente> listarClientes() {
        return Collections.unmodifiableList(clientes);
    }

    public Cliente buscarClienteSecuencial(int dni) {
        for (Cliente c : clientes) { //recorre desde el principio uno por uno, es lo opuesto al HashMap...
            if (c.getDni() == dni) { // ... hasta encontrar el cliente con el DNI buscado.
                return c;
            }
        }
        throw new IllegalArgumentException("No se encontró ningún cliente con el DNI " + dni + ".");
    }

    public void registrarSucursal(Sucursal s) {
        if (sucursales.contains(s)) {
            throw new IllegalArgumentException("La sucursal " + s.getIdSucursal() + " ya está registrada.");            
        }
        sucursales.add(s);
    }

    public void registrarEnvio(Envio e) {
        if (envios.containsKey(e.getId())) { //contains key responde si ya hay algo guardado con esa clave.
            throw new IllegalArgumentException("El envío con ID " + e.getId() + " ya está registrado.");
        }
        envios.put(e.getId(), e);//guarda el par.
    }

    public Envio buscarEnvio(int codigo) {
        Envio encontrado = envios.get(codigo);//get(clave) encuentra el valor asociado, sin recorrer toda la lista, es más eficiente.
        if (encontrado == null) {
            throw new IllegalArgumentException("No se encontró ningún envío con el código " + codigo + ".");
        }
        return encontrado;
    }
}
