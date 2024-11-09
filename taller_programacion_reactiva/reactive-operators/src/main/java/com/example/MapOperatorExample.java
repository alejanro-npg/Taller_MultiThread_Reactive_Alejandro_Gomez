package com.example;

import io.reactivex.Observable;
import reactor.core.publisher.Flux;

public class MapOperatorExample {
    public static void main(String[] args) {
        Observable.just(1, 2, 3, 4, 5)
                .map(item -> item * 2)
                .subscribe(System.out::println);


        System.out.println("Ejemplo de operadores");

        Flux.just(1, 2, 3, 4, 5)
                .map(item -> item * 2)
                .subscribe(System.out::println);
    }
}