package com.example;


import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//El caso de uso el una aplicación donde los usuarios pueden comprar productos y el dinero de las compras se va almacenando de forma simúltanea para tener las ganacias finales.

// Clase para los producto a comprar
class Producto {
    private final String nombre;
    private final double precio;

    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return nombre + " $" + precio;
    }
}

// Clase de los usuarios que compran los productos
class Usuario implements Callable<Double> {
    private final String nombre;
    private final List<Producto> productos;

    public Usuario(String nombre, List<Producto> productos) {
        this.nombre = nombre;
        this.productos = productos;
    }
//llama el método call para el el proceso de compra y guardar el dinero total que compro
    @Override
    public Double call() {
        double totalCompra = 0;
        System.out.println(nombre + " está comprando productos...");
        for (Producto producto : productos) {
            totalCompra += producto.getPrecio();
            System.out.println(nombre + " compró: " + producto);
        }
        System.out.println(nombre + " total de compra: $" + totalCompra);
        return totalCompra;
    }
}

// Clase de las ganancias
class Ganancias {
    private double totalGanancias = 0;

    // Synchronized para asegurar que se puedan ver las ganacias totales
    public synchronized void agregarGanancia(double ganancia) {
        totalGanancias += ganancia;
        System.out.println("Ganancia actualizada: $" + totalGanancias);
    }

    public double getTotalGanancias() {
        return totalGanancias;
    }
}

public class ExampleMultiThread {
    public static void main(String[] args) {
        // Crear productos
        Producto prod1 = new Producto("Cartuchera", 15.99);
        Producto prod2 = new Producto("Cuaderno", 25.49);
        Producto prod3 = new Producto("Lapiz", 9.99);

        // Crear usuarios con sus listas de productos
        List<Producto> productosUsuario1 = List.of(prod1, prod2, prod3);
        List<Producto> productosUsuario2 = List.of(prod1, prod3);
        List<Producto> productosUsuario3 = List.of(prod2, prod3);

        // Crear instancias de usuarios
        Usuario usuario1 = new Usuario("Pepe", productosUsuario1);
        Usuario usuario2 = new Usuario("Gabriel", productosUsuario2);
        Usuario usuario3 = new Usuario("Pedro", productosUsuario3);

        // Objeto para las ganancias totales
        Ganancias gananciasTotales = new Ganancias();

        // Crear un ExecutorService con los threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Lista para almacenar las tareas de compra de los usuarios
        List<Callable<Double>> tareas = List.of(usuario1, usuario2, usuario3);

        try {
            // Ejecutar las tareas y obtener los resultados
            List<Future<Double>> resultados = executor.invokeAll(tareas);

            // Procesar cada resultado y agregar a las ganancias totales
            for (Future<Double> resultado : resultados) {
                gananciasTotales.agregarGanancia(resultado.get());
            }

            System.out.println("Ganancias totales al final de las compras: $" + gananciasTotales.getTotalGanancias());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            
            executor.shutdown();
        }
    }
}
