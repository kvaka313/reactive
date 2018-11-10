package com.infopulse.main;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.List;

public class Main {



    public static void main(String[] args) {
        Flux<String> sequence = Flux.just("foo", "bar", "foobar");
//        List<String> iterable = Arrays.asList("foo", "bar", "foobar");
//        Flux<String> sequence = Flux.fromIterable(iterable);
//
//        Mono<String> noData = Mono.empty();  //пустой Mono
//        Mono<String> data = Mono.just("foo"); //строка "foo"
//
//        Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 3);

//        subscribe();  //запустить исполнение..
//
//// .. и сделать что-то с каждым полученным значением
//        subscribe(Consumer<? super T> consumer);
//
//        // .. и сделать что-то в случае исключения
//        subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> errorConsumer);
//
//// .. и сделать что-то по завершению
//        subscribe(
//                Consumer<? super T> consumer,
//                Consumer<? super Throwable> errorConsumer,
//                Runnable completeConsumer
//        );


        sequence.subscribe(i -> System.out.println(i));

        Flux<Integer> ints = Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) {
                        return i;
                    }
                    throw new RuntimeException("Got to 4");
                });
        ints.subscribe(
                i -> System.out.println(i), error -> System.err.println("Error: " + error)
        );

        Flux<Integer> otherInts = Flux.range(1, 4);
        otherInts.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error " + error),
                () -> {System.out.println("Done");
        });

        Flux<Integer> newSequence = Flux.range(0, 100).publishOn(Schedulers.single());
         //вызовы onNext, onComplete и onError будут происходить в шедулере single.
        newSequence.subscribe(n -> {
            System.out.println("n = " + n);
            System.out.println("Thread.currentThread() = " + Thread.currentThread());
        });
        newSequence.blockLast();

    }
}
