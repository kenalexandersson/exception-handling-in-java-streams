package org.kense.exceptionhandling;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.Value;

/**
 * A generic wrapper signifying a 'pair'. Both types of the pair could be of any type.
 * @param <F> the type of value of the first member in the pair
 * @param <S> the type of value of the second memeber in the pair.
 */
@Value
@ToString
@RequiredArgsConstructor(staticName = "of")
public class Pair<F,S> {

    private final F first;
    private final S second;
}