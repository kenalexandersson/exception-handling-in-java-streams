package org.kense.exceptionhandling;

public class Pair<F,S> {

    private final F fst;
    private final S snd;

    public static <F,S> Pair<F,S> of(F fst, S snd) {
        return new Pair<>(fst,snd);
    }

    private Pair(F fst, S snd) {
        this.fst = fst;
        this.snd = snd;
    }

    @Override
    public String toString() {
        return String.format("Pair{fst=%s, snd=%s}", fst, snd);
    }
}