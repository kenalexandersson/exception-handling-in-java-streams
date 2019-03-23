package org.kense.core;

import org.junit.Test;
import org.kense.exceptionhandling.Either;
import org.kense.exceptionhandling.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerTest.class);

    @Test
    public void basicUsage() {

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);

        List<Either> eithers = integers.stream()
                .map(Either.lift(Worker::timesTen))
                .collect(Collectors.toList());

        eithers.forEach(either -> LOGGER.info(either.toString()));
    }

    @Test
    public void successesAndFailures() {

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);

        List<Integer> successes = integers.stream()
                .map(Either.liftWithValue(Worker::timesTen))
                .filter(Either::isRight)
                .map(Either::getRightAsInteger)
                .peek(integer -> LOGGER.info("Success: {}", integer))
                .collect(Collectors.toList());

        List<Pair> failures = integers.stream()
                .map(Either.liftWithValue(Worker::timesTen))
                .filter(Either::isLeft)
                .map(Either::getLeft)
                .map(Optional::get)
                .map(Pair.class::cast)
                .peek(pair -> LOGGER.warn("Failure: {}", pair))
                .collect(Collectors.toList());

        assertThat(successes)
                .hasSize(3)
                .containsExactly(10, 30, 50);

        assertThat(failures)
                .as("I don't like even number so I throw exception")
                .hasSize(2)
                .extracting("first")
                .containsExactly(2, 4);
    }

}