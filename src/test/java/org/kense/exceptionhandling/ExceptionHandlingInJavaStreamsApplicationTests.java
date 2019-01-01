package org.kense.exceptionhandling;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kense.core.Worker;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExceptionHandlingInJavaStreamsApplicationTests {

    @Test
    public void contextLoads() {
    }

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

        eithers.forEach(System.out::println);
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

        collect.forEach(System.out::println);
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

        successes.forEach(System.out::println);

        List<Object> failures = numberList.stream()
                .map(Either.liftWithValue(Worker::timesTen))
                .filter(Either::isLeft)
                .map(Either::getLeft)
                .map(Optional::get)
                .collect(Collectors.toList());

        failures.forEach(System.out::println);
    }
}

