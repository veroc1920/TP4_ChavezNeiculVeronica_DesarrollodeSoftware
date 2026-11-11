package logistica.enums;
//Usamos un enum para representar los estados posibles de un paquete, 
//dado que son un conjunto fijo de valores que no se modifican.
/*public enum EstadoPaquete {
    EN_PREPARACION, EN_DISTRIBUCION, ENTREGADO, DEVUELTO
}*/

//Correcciones TP 2.1
//Se crean un enum que define por defecto ninguna transición permitida, cada estado sobrescribe las que sí acepta:

public enum EstadoPaquete {
    EN_PREPARACION {
        @Override
        public EstadoPaquete distribuir() { return EN_DISTRIBUCION; }
    },
    EN_DISTRIBUCION {
        @Override
        public EstadoPaquete distribuir() { return this; } // ya está en camino, pasa por otra sucursal
        @Override
        public EstadoPaquete entregar() { return ENTREGADO; }
        @Override
        public EstadoPaquete devolver() { return DEVUELTO; }
    },
    ENTREGADO {
        @Override
        public boolean esFinal() { return true; }
    },
    DEVUELTO {
        @Override
        public boolean esFinal() { return true; }
    };

    
    // Cada estado sobrescribe solo las transiciones que sí acepta.
    public EstadoPaquete distribuir() {
        throw new IllegalStateException("El paquete " + this + " no puede distribuirse.");
    }
    public EstadoPaquete entregar() {
        throw new IllegalStateException("El paquete " + this + " no puede entregarse.");
    }
    public EstadoPaquete devolver() {
        throw new IllegalStateException("El paquete " + this + " no puede devolverse.");
    }
    public boolean esFinal() {
        return false;
    }
}