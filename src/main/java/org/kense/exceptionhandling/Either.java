package org.kense.exceptionhandling;

import java.util.Optional;
import java.util.function.Function;

public class Either<L, R> {
    private final L left;
    private final R right;

    private Either(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L,R> Either<L,R> left(L value) {
        return new Either(value, null);
    }

    public static <L,R> Either<L,R> right(R value) {
        return new Either(null, value);
    }


    public static <T,R> Function<T, Either> lift(CheckedFunction<T,R> function) {
        return t -> {
            try {
                return Either.right(function.apply(t));
            } catch (Exception ex) {
                return Either.left(ex);
            }
        };
    }

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
        if (isLeft()) {
            return String.format("Left(%s)", left);
        }
        return String.format("Right(%s)", right);
    }
}