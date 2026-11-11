package logistica.app;

import logistica.modelo.Envio;
import logistica.modelo.EnvioEstandar;
import logistica.modelo.EnvioExpress; 
import logistica.modelo.EnvioInternacional;
import logistica.modelo.Paquete;
import logistica.modelo.Cliente;
import logistica.modelo.Sucursal;
import logistica.modelo.SeguimientoMovimientos;
import logistica.modelo.RegistroCentral;
import java.util.ArrayList;
import java.util.List;


public class App {
    public static void main(String[] args) {
        List<Envio> envios = new ArrayList<>(); //Crea una lista para almacenar los envíos.
        RegistroCentral registro = new RegistroCentral();

        Paquete p1 = new Paquete("P0001", "Juan Pérez", "Rivadavia 12, Villa Regina", 10);
        Paquete p2 = new Paquete("P0002", "Sara García", "Sarmiento 45, Bariloche", 20);
        Paquete p3 = new Paquete("P0003", "Carlos López", "Mitre 78, Cipolletti", 15);
        Paquete p4 = new Paquete("P0004", "Ana Martínez", "Brasil", 5);
        Cliente cliente = new Cliente(12345678, "Juan Pérez", "Rivadavia 12, Villa Regina", "299-1234567");
        //instancio más clientes y sucursales para probar si la app funciona correctamente.
        Cliente cliente2 = new Cliente(87654321, "Sara García", "Sarmiento 45, Bariloche", "299-7654321");
        Cliente cliente3 = new Cliente(11223344, "Carlos López", "Mitre 78, Cipolletti", "299-1122334");
        Sucursal sucursal1 = new Sucursal("S001", "Villa Regina");
        Sucursal sucursal2 = new Sucursal("S002", "Bariloche");
        Sucursal sucursal3 = new Sucursal("S003", "Cipolletti");
        Sucursal sucursal4 = new Sucursal("S004", "Brasil");

        //TP4: se registran los clientes y sucursales en el registro central.
        registro.registrarCliente(cliente);
        registro.registrarCliente(cliente2);
        registro.registrarCliente(cliente3);
        registro.registrarSucursal(sucursal1);
        registro.registrarSucursal(sucursal2);
        registro.registrarSucursal(sucursal3);
        registro.registrarSucursal(sucursal4);

        EnvioEstandar estandar = new EnvioEstandar(1, cliente);
        registro.registrarEnvio(estandar); //TP4: se registra el envío en el registro central.
        estandar.agregarPaquete(p1);
        estandar.agregarPaquete(p2);

        sucursal1.recibirEnvio(estandar);
        System.out.println("Recibido: " + estandar.obtenerUltimoMovimiento());
        sucursal1.despachar(1);
        System.out.println("Despachado: " + estandar.obtenerUltimoMovimiento());
        sucursal2.recibirEnvio(estandar);
        System.out.println("Recibido: " + estandar.obtenerUltimoMovimiento());
        sucursal2.entregarEnvio(estandar);
        
        EnvioExpress express = new EnvioExpress(2, cliente2);
        registro.registrarEnvio(express); //TP4: se registra el envío en el registro central.
        express.agregarPaquete(p3);
       
        sucursal2.recibirEnvio(express);
        System.out.println("Recibido: " + express.obtenerUltimoMovimiento());
        sucursal2.despachar(1);
        System.out.println("Despachado: " + express.obtenerUltimoMovimiento());
        sucursal3.recibirEnvio(express);
        System.out.println("Recibido: " + express.obtenerUltimoMovimiento());
        sucursal3.entregarEnvio(express);

        EnvioInternacional internacional = new EnvioInternacional(3, cliente3, "Brasil");
        registro.registrarEnvio(internacional); //TP4: se registra el envío en el registro central.
        internacional.agregarPaquete(p4);

        
        sucursal3.recibirEnvio(internacional);
        System.out.println("Recibido: " + internacional.obtenerUltimoMovimiento());
        sucursal3.despachar(1);
        System.out.println("Despachado: " + internacional.obtenerUltimoMovimiento());
        sucursal4.recibirEnvio(internacional);
        System.out.println("Recibido: " + internacional.obtenerUltimoMovimiento());
        sucursal4.entregarEnvio(internacional);
       
        envios.add(estandar);
        envios.add(express);
        envios.add(internacional);

        /*System.out.println("Iniciando envíos...");
        for (Envio e: envios) {
            e.iniciarEnvio();
        }*/ //Este bloque ya no es necesario, el método iniciarEnvio() 
        // fue eliminado de la clase Envio, ya que la sucursal es la que inicia el movimiento.

        System.out.println("\n=== ESTADO DE PAQUETES ===");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);
        
        //MODIFICACIÓN TP 2.1: como cada envío se entrega en la última sucursal que lo recibió, se elimina el for
       /*  System.out.println("\nFinalizando envíos...");
        for (Envio e: envios) {
            e.finalizarEnvio();
        } */
       /*  System.out.println("\nEntregando envíos...");
        sucursal2.entregarEnvio(estandar);
        sucursal3.entregarEnvio(express);
        sucursal4.entregarEnvio(internacional); *///TP4: cada envío se entrega apenas llega a su sucursal de destino, así sale de la cola.

        System.out.println("\n=== Resumen de Jornada ===");
        for (Envio e: envios) {
            System.out.println(e);
        }

        //MODIFICACIÓN TP 2.1: 
        System.out.println("\n=== Envíos del Cliente ===");
        //cliente.mostrarEnvios();
        for (Envio e : cliente.obtenerEnvios()) {
            System.out.println(e);
        }


        //Prueba de los métodos implementados para mostrar los movimientos del envío.
        System.out.println("\n=== Historial del Envío ===");
        /* estandar.mostrarHistorial();
        estandar.mostrarSucursalesRecorridas();
        estandar.mostrarUltimoMovimiento(); */ //MODIFICACIÓN TP 2.1: consultas del historial:
        for (SeguimientoMovimientos m : estandar.obtenerHistorial()) {
            System.out.println(m);
        }
        System.out.println("Sucursales recorridas:");
        for (Sucursal s : estandar.obtenerSucursalesRecorridas()) {
            System.out.println("- " + s);
        }
        System.out.println("Último movimiento: " + estandar.obtenerUltimoMovimiento());

        //Prueba de la validación para despachar un envío que no tiene paquetes.
        System.out.println("\n=== Prueba: envío vacío ===");
        try {
            EnvioEstandar envioVacio = new EnvioEstandar(4, cliente);            
            sucursal4.recibirEnvio(envioVacio);
            sucursal4.despachar(1);
            System.out.println("No imprimir: El envío no tiene paquetes, no se puede despachar."); //esta línea no debe imprimirse, si se imprime no está funcionando la validación.
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage()); //Excepción esperada lanzada por la validación.
        }
        
        //Prueba de la validación para buscar un envío inexistente
        System.out.println("\n=== Prueba: búsqueda de envío inexistente ===");
        try {
            cliente.buscarEnvio(9999);
            System.out.println("No imprimir: El envío no existe, no se puede buscar."); //esta línea no debe imprimirse, si se imprime no está funcionando la validación.
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage()); //Excepción esperada lanzada por la validación.
        }

       
        /* System.out.println("\n=== Prueba: despachar desde una sucursal equivocada ===");
        try {
            EnvioExpress e6 = new EnvioExpress(6, cliente);           
            e6.agregarPaquete(new Paquete("P0007", "Luis Gómez", "Belgrano 3, Roca", 1));
            sucursal1.recibirEnvio(e6);
            sucursal2.despacharEnvio(e6);
            System.out.println("No imprimir: no debería poder despacharse.");
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } */ //TP4: se elimina la prueba, con la cola esto ya no puede pasar, cada sucursal despacha solo lo que tiene en su propia cola.

        //TP4: prueba de validación para despachar desde una sucursal que no tiene envíos pendientes de despacho.
        System.out.println("\n=== Prueba: despachar con la cola vacía ===");
        try {
            sucursal2.despachar(1);
            System.out.println("No imprimir: no debería poder despacharse.");
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        /* //MODIFICACIÓN TP 2.1: Prueba de validaciones creadas
        System.out.println("\n=== Prueba: despachar un envío ya entregado ===");
        try {
            sucursal2.despacharEnvio(estandar);
            System.out.println("No imprimir: no debería poder despacharse.");
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } */ //TP4: se elimina la prueba, con la cola esto ya no puede pasar.

        //TP4: prueba de validación para recibir un envío que ya fue entregado.
        System.out.println("\n=== Prueba: recibir un envío ya entregado ===");
        try {
            sucursal1.recibirEnvio(estandar);
            System.out.println("No imprimir: no debería poder recibirse.");
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== Prueba: agregar un paquete a un envío que ya salió ===");
        try {
            estandar.agregarPaquete(new Paquete("P0005", "Luis Díaz", "Roca 10, Allen", 3));
            System.out.println("No imprimir: no debería poder agregarse.");
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== Prueba: entregar un envío que no fue despachado ===");
        try {
            EnvioExpress e5 = new EnvioExpress(5, cliente);
            e5.agregarPaquete(new Paquete("P0006", "Ana Ruiz", "Tucumán 5, Roca", 2));
            sucursal1.recibirEnvio(e5);
            sucursal1.entregarEnvio(e5);
            System.out.println("No imprimir: no debería poder entregarse.");
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== Registro: buscar envío por código (HashMap) ===");
        Envio encontrado = registro.buscarEnvio(2);
        System.out.println("Encontrado: " + encontrado + " del cliente " + encontrado.getCliente());

        System.out.println("\n=== Registro: código de envío repetido ===");
        try {
            registro.registrarEnvio(new EnvioEstandar(1, cliente2));
            System.out.println("No imprimir: no debería registrarse.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== Registro: envío inexistente ===");
        try {
            registro.buscarEnvio(9999);
            System.out.println("No imprimir: no debería encontrarse.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== Registro: búsqueda secuencial de clientes ===");
        System.out.println("Encontrado: " + registro.buscarClienteSecuencial(87654321));
        try {
            registro.buscarClienteSecuencial(99999999);
            System.out.println("No imprimir: no debería encontrarse.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        //TP4: prueba nueva de la cola:
        System.out.println("\n=== Prueba: cola de despacho (camión con capacidad 2) ===");
        EnvioEstandar c1 = new EnvioEstandar(7, cliente2);
        c1.agregarPaquete(new Paquete("P0008", "Sara García", "Sarmiento 45, Bariloche", 4));
        EnvioEstandar c2 = new EnvioEstandar(8, cliente3);
        c2.agregarPaquete(new Paquete("P0009", "Carlos López", "Mitre 78, Cipolletti", 6));
        EnvioExpress c3 = new EnvioExpress(9, cliente);
        c3.agregarPaquete(new Paquete("P0010", "Juan Pérez", "Rivadavia 12, Villa Regina", 2));

        sucursal3.recibirEnvio(c1);
        sucursal3.recibirEnvio(c2);
        sucursal3.recibirEnvio(c3);

        for (Envio e : sucursal3.despachar(2)) {
            System.out.println("Primer viaje: " + e.getId());
        }
        for (Envio e : sucursal3.despachar(2)) {
            System.out.println("Segundo viaje: " + e.getId());
        }
        try {
            sucursal3.despachar(2);
            System.out.println("No imprimir: la cola debería estar vacía.");
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        //TP4:Prueba de registro de clientes en RegistroCentral
        System.out.println("\n=== Clientes registrados ===");
        for (Cliente c : registro.listarClientes()) {
            System.out.println(c);
        }
      
    }
}

