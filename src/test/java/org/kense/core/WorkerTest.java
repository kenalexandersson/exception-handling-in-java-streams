package org.kense.core;

import org.junit.Test;
import org.kense.exceptionhandling.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WorkerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerTest.class);
    @Test
    public void name() {

        List<Integer> numberList = new ArrayList<>();
        numberList.add(1);
        numberList.add(2);
        numberList.add(3);
        numberList.add(4);
        numberList.add(5);

        List<Either> eithers = numberList.stream()
                .map(Either.liftWithValue(Worker::timesTen))
                .collect(Collectors.toList());

        eithers.forEach(either -> LOGGER.info(either.toString()));
    }

    @Test
    public void name2() {

        List<Integer> numberList = new ArrayList<>();
        numberList.add(1);
        numberList.add(2);
        numberList.add(3);
        numberList.add(4);
        numberList.add(5);

        List<Integer> collect = numberList.stream()
                .map(Either.liftWithValue(Worker::timesTen))
                .filter(either -> either.getRight().isPresent())
                .map((Function<Either, Optional<Integer>>) Either::getRight)
                .map(Optional::get)
                .collect(Collectors.toList());

        collect.forEach(number -> LOGGER.info(number.toString()));
    }

    @Test
    public void name3() {

        List<Integer> numberList = new ArrayList<>();
        numberList.add(1);
        numberList.add(2);
        numberList.add(3);
        numberList.add(4);
        numberList.add(5);

        List<Integer> successes = numberList.stream()
                .map(Either.liftWithValue(Worker::timesTen))
                .filter(Either::isRight)
                .map(Either::getRightAsInteger)
                .collect(Collectors.toList());

        successes.forEach(success -> LOGGER.info("Success: {}", success));

        List<Object> failures = numberList.stream()
                .map(Either.liftWithValue(Worker::timesTen))
                .filter(Either::isLeft)
                .map(Either::getLeft)
                .map(Optional::get)
                .collect(Collectors.toList());

        failures.forEach(failure -> LOGGER.warn("Failure: {}", failure));
    }

}