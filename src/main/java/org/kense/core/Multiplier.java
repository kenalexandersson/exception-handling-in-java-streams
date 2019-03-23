package org.kense.core;

public class Multiplier {

    public static int timesTen(int value) throws Exception {

        if (value % 2 == 0) {
            throw new Exception(String.format("I despise even numbers: %d", value));
        }

        return value * 10;
    }
}
