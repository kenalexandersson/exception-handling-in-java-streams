package org.kense.exceptionhandling;

public class Pair<F,S> {

    private final F first;
    private final S second;

    public static <F,S> Pair<F,S> of(F fst, S snd) {
        return new Pair<>(fst,snd);
    }

    private Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return String.format("Pair{first=%s, second=%s}", first, second);
    }
}