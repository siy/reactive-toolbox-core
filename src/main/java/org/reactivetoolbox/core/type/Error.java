package org.reactivetoolbox.core.type;

/*
 * Copyright (c) 2017-2019 Sergiy Yevtushenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.reactivetoolbox.core.lang.Result;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Supplier;

/**
 * Basic interface for error types.
 *
 * @author Sergiy Yevtushenko
 */
//TODO: extend for precise error tracking, include (optional) subcode, file name, line number
public interface Error {
    ErrorType type();
    String message();

    default <T> Result<T> asFailure() {
        return Result.failure(this);
    }

    static <T> Supplier<Result<T>> lazyFailure(final ErrorType type, final String format, final Object ... params) {
        return () -> with(type, format, params).asFailure();
    }

    static Supplier<Error> lazy(final ErrorType type, final String format, final Object ... params) {
        return () -> with(type, format, params);
    }

    static Error with(final ErrorType type, final String format, final Object ... params) {
        return of(type, String.format(format, params));
    }

    static Error of(final ErrorType type, final String message) {
        return new Error() {
            @Override
            public ErrorType type() {
                return type;
            }

            @Override
            public String message() {
                return message;
            }

            @Override
            public int hashCode() {
                return Objects.hash(type, message);
            }

            @Override
            public boolean equals(final Object obj) {
                if (obj == this) {
                    return true;
                }

                return (obj instanceof Error) && (Objects.equals(((Error) obj).type(), type) && Objects.equals(((Error) obj).message(), message));
            }

            @Override
            public String toString() {
                return new StringJoiner(", ", "Error(", ")")
                        .add(type.toString())
                        .add(message)
                        .toString();
            }
        };
    }
}
