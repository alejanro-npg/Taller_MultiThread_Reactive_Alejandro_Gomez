package com.example;

import io.reactivex.Observable;
import reactor.core.publisher.Flux;

public class ZipOperatorExample {
    public static void main(String[] args) {
        Observable<Integer> observable1 = Observable.just(1, 2, 3);
        Observable<String> observable2 = Observable.just("A", "B", "C");

        Observable.zip(observable1, observable2, (num, letter) -> num + letter)
                .subscribe(System.out::println);

                System.out.println("Ejemplo de operadores");

                Flux<Integer> flux1 = Flux.just(1, 2, 3);
                Flux<String> flux2 = Flux.just("A", "B", "C");
        
                Flux.zip(flux1, flux2, (num, letter) -> num + letter)
                        .subscribe(System.out::println);


    }
}