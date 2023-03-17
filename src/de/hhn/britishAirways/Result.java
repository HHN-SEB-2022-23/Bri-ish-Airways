package de.hhn.britishAirways;

import java.util.function.Function;

public class Result<T, E> {
    private final T value;
    private final E error;
    private final boolean isOk;

    private Result(T value, E error, boolean isOk) {
        this.value = value;
        this.error = error;
        this.isOk = isOk;
    }

    public static <T, E> Result<T, E> ok(T value) {
        return new Result<>(value, null, true);
    }

    public static <T, E> Result<T, E> err(E error) {
        return new Result<>(null, error, false);
    }

    public T unwrap() throws IllegalStateException {
        if (this.isOk) {
            return this.value;
        }
        throw new IllegalStateException("Cannot unwrap error");
    }

    public E unwrapErr() throws IllegalStateException {
        if (this.isOk) {
            throw new IllegalStateException("Cannot unwrap value");
        }
        return this.error;
    }

    public boolean isOk() {
        return this.isOk;
    }

    public boolean isErr() {
        return !this.isOk;
    }

    public <U> Result<U, E> map(Function<T, U> f) {
        if (this.isOk) {
            return Result.ok(f.apply(this.value));
        }
        return (Result<U, E>) this.error;
    }

    public <U> Result<T, U> mapErr(Function<E, U> f) {
        if (this.isOk) {
            return (Result<T, U>) this.value;
        }
        return Result.err(f.apply(this.error));
    }

    public <U> Result<U, E> and(Result<U, E> other) {
        if (this.isOk) {
            return other;
        }
        return (Result<U, E>) this.error;
    }

    public <U> Result<U, E> andThen(Function<T, Result<U, E>> f) {
        if (this.isOk) {
            return f.apply(this.value);
        }
        return (Result<U, E>) this.error;
    }

    public <U> Result<T, U> or(Result<T, U> other) {
        if (this.isOk) {
            return (Result<T, U>) this.value;
        }
        return other;
    }

    public <U> Result<T, U> orElse(Function<E, Result<T, U>> f) {
        if (this.isOk) {
            return (Result<T, U>) this.value;
        }
        return f.apply(this.error);
    }

    public T unwrapOr(T other) {
        if (this.isOk) {
            return this.value;
        }
        return other;
    }

    public T unwrapOrElse(Function<E, T> f) {
        if (this.isOk) {
            return this.value;
        }
        return f.apply(this.error);
    }

    @Override
    public String toString() {
        if (this.isOk) {
            return "Ok(" + this.value + ')';
        }
        return "Err(" + this.error + ')';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Result<?, ?> other = (Result<?, ?>) obj;
        if (this.isOk) {
            return other.isOk && this.value.equals(other.value);
        }
        return !other.isOk && this.error.equals(other.error);
    }

    @Override
    public int hashCode() {
        int valueHash = ( this.isOk ? this.value.hashCode() : this.error.hashCode() ) << 1;

        return this.isOk ? valueHash + 1 : valueHash;
    }
}
