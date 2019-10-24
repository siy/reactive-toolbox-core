package org.reactivetoolbox.core.lang;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TupleTest {

    @Test
    void tuple1CanBeCreatedAndMapped() {
        final var tuple = Tuple.with(10);

        assertEquals("10", tuple.map((integer) -> "" + integer));
    }

    @Test
    void tuple2CanBeCreatedAndMapped() {
        final var tuple = Tuple.with(10, "some string");

        assertEquals("10some string", tuple.map((integer, string) -> "" + integer + string));
    }

    @Test
    void tuple3CanBeCreatedAndMapped() {
        final var param3 = UUID.randomUUID();
        final var tuple = Tuple.with(10, "some string", param3);

        assertEquals("10some string" + param3.toString(), tuple.map((integer, string, uuid) -> "" + integer + string + uuid));
    }
}