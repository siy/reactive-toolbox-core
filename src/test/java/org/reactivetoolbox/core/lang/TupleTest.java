package org.reactivetoolbox.core.lang;

import org.junit.jupiter.api.Test;

import java.util.UUID;

class TupleTest {

    @Test
    void tuple3CanBeCreatedAndMapped() {
        final var tuple = Tuple.with(10, "some string", UUID.randomUUID());

        tuple.map((integer, string, uuid) -> System.out.printf("Received: %d, %s, %s", integer, string, uuid));
    }
}