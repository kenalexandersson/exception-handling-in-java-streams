package org.kense.core;

public class Worker {

    public static int timesTen(int value) throws Exception {

        if (value % 2 == 0) {
            throw new Exception("I don't like even numbers");
        }

        return value * 10;
    }
}
