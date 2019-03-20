package org.kense.exceptionhandling;

import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.function.Function;

/**
 * A generic wrapper with two possibilities. It can either be a Left or a Right but never both.
 * Both left and right can be of any types. By convention, the Left signifies a failure case result
 * and the Right signifies a success.
 *
 * @param <L> the type of value for left
 * @param <R> the type of value for right
 */
@AllArgsConstructor
public class Either<L, R> {
    private final L left;
    private final R right;

    public static <L,R> Either<L,R> left(L value) {
        return new Either(value, null);
    }

    public static <L,R> Either<L,R> right(R value) {
        return new Either(null, value);
    }


    /**
     * Applies the given {@link CheckedFunction} and returns an Either with the result.
     * @param function the function to apply.
     * @param <T> the type of the function parameter
     * @param <R> the type of the returned value
     * @return an Either holding successful execution value to the right, or if exception was thrown, a left containing
     * the exception.
     */
    public static <T,R> Function<T, Either> lift(CheckedFunction<T,R> function) {
        return t -> {
            try {
                return Either.right(function.apply(t));
            } catch (Exception ex) {
                return Either.left(ex);
            }
        };
    }

    /**
     * Applies the given {@link CheckedFunction} and returns an Either with the result.
     * @param function the function to apply.
     * @param <T> the type of the function parameter
     * @param <R> the type of the returned value
     * @return an Either holding successful execution value to the right, or if exception was thrown, a left containing
     * a Pair object holding the exception and the original parameter value
     */
    public static <T,R> Function<T, Either> liftWithValue(CheckedFunction<T,R> function) {
        return t -> {
            try {
                return Either.right(function.apply(t));
            } catch (Exception ex) {
                return Either.left(Pair.of(ex,t));
            }
        };
    }

    public Optional<L> getLeft() {
        return Optional.ofNullable(left);
    }

    public Optional<R> getRight() {
        return Optional.ofNullable(right);
    }

    public Integer getRightAsInteger() {
        return (Integer) right;
    }

    public boolean isLeft() {
        return left != null;
    }

    public boolean isRight() {
        return right != null;
    }

    public <T> Optional<T> mapLeft(Function<? super L, T> mapper) {
        if (isLeft()) {
            return Optional.of(mapper.apply(left));
        }
        return Optional.empty();
    }

    public <T> Optional<T> mapRight(Function<? super R, T> mapper) {
        if (isRight()) {
            return Optional.of(mapper.apply(right));
        }
        return Optional.empty();
    }

    public String toString() {
        return isLeft() ? String.format("Left(%s)", left) : String.format("Right(%s)", right);
    }
}