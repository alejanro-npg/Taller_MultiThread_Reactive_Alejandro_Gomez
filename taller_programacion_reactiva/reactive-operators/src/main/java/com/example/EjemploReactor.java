package com.example;

import io.reactivex.Observable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EjemploReactor {


    //Caso de uso, hay unos usuarios que tienen un nombre de usuario, contraseña y id, a los cuales van a pasar por ciertas operaciones.
    public static void main(String[] args) {
        // Lista de usuarios con  atributos.
        List<Usuario> usuarios = Arrays.asList(
                new Usuario(1, "Juan", 1234),
                new Usuario(2, "Peppe", 1023),
                new Usuario(3, "Camille", 4560),
                new Usuario(4, "Jhonson", 7894)
        );

        // Se crea un observabel de los usuarios
        Observable<Usuario> usuarioObservable = Observable.fromIterable(usuarios);

        // zip - Combina la contraseña con el id, para poder asociar esa información
        System.out.println("Operación: zip (combinación de nombre y contraseña)");
        usuarioObservable
                .map(usuario -> usuario.getId() + " - " + usuario.getContrasena())
                .subscribe(resultado -> System.out.println("Resultado zip: " + resultado));
        
        System.out.println("\nOperación: filter (filtro de contraseñas con '0')");
        // filter - Se filtra todas los contraseñas con valor 0.
        usuarioObservable
                .filter(usuario -> String.valueOf(usuario.getContrasena()).contains("0"))
                .subscribe(usuario -> System.out.println("Resultado filter: " + usuario));

    
        // map - Se suma los id con el valor 2024
        Observable<Usuario> usuariosModificados = usuarioObservable
                .map(usuario -> {
                    usuario.setId(usuario.getId() + 2024);
                    return usuario;
                });

                    
                // flatMap - Se multiplica la contraseña y el id por un número aleatorio en forma de enmascarar la información.
        Observable<Usuario> usuariosModificadosFinal = usuariosModificados
                .flatMap(usuario -> {
                    Random random = new Random();
                    int factor = random.nextInt(10) + 1; // Número aleatorio entre 1 y 10
                    usuario.setId(usuario.getId() * factor);
                    usuario.setContrasena(usuario.getContrasena() * factor);
                    return Observable.just(usuario);
                });

        System.out.println("\nOperación: merge (usuarios antes y después de la modificación)");
        // merge - Se tiene los usuarios antes y depués de ser modificados
        Observable.merge(usuarioObservable, usuariosModificadosFinal)
             
                .subscribe(usuario -> System.out.println("Resultado merge: " + usuario),
                        Throwable::printStackTrace,
                        () -> System.out.println("Proceso completado."));
    }

    // Clase Usuario
    static class Usuario {
        private int id;
        private String nombre;
        private int contrasena;

        public Usuario(int id, String nombre, int contrasena) {
            this.id = id;
            this.nombre = nombre;
            this.contrasena = contrasena;
        }

        // Los get y set
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public int getContrasena() { return contrasena; }
        public void setContrasena(int contrasena) { this.contrasena = contrasena; }

        @Override
        public String toString() {
            return "Usuario{" +
                    "id=" + id +
                    ", nombre='" + nombre + '\'' +
                    ", contrasena=" + contrasena +
                    '}';
        }
    }
}
