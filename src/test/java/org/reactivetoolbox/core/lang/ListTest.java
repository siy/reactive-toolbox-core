package org.reactivetoolbox.core.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListTest {
    @Test
    void listCanBeCreated() {
        final ArrayList<Integer> arrayList = new ArrayList<>();
        final var list = List.list(1, 2, 3);

        list.apply(arrayList::add);

        assertEquals(Arrays.asList(1, 2, 3), arrayList);
    }

    @Test
    void listsCanBeMerged() {
        final var list1 = List.list(1, 2, 3);
        final var list2 = List.list(4, 5, 6);

        assertEquals(List.list(1, 2, 3, 4, 5, 6), list1.append(list2));
        assertEquals(List.list(1, 2, 3, 4, 5, 6), list2.prepend(list1));
    }

    @Test
    void mapNProvidesIndexes() {
        final var list = List.list(3, 2, 1).mapN(Integer::sum);

        assertEquals(List.list(3, 3, 3), list);
    }

    @Test
    void applyNProvidesIndexes() {
        final var list = List.list(3, 2, 1);
        final int[] values = new int[list.size()];

        list.applyN((n, v) -> values[n] = v);

        assertEquals(3, values[0]);
        assertEquals(2, values[1]);
        assertEquals(1, values[2]);
    }

    @Test
    void mapIsApplied() {
        final var list = List.list(3, 2, 1).map(v -> v + 1);

        assertEquals(List.list(4, 3, 2), list);
    }

    @Test
    void emptyListIsEmpty() {
        final var counter = new AtomicInteger(0);

        List.list().apply(v -> counter.incrementAndGet());
        List.list().applyN((n, v) -> counter.incrementAndGet());

        assertEquals(0, counter.get());
    }

    @Test
    void firstAndLastElementsCanBeRetrieved() {
        final var list = List.list(1, 2, 3);

        list.first()
            .whenPresent(v -> assertEquals(1, v))
            .whenEmpty(Assertions::fail);
        list.last()
            .whenPresent(v -> assertEquals(3, v))
            .whenEmpty(Assertions::fail);
    }

    @Test
    void sublistCanBeObtained() {
        final var list = List.list(1, 2, 3);

        assertEquals(List.list(1), list.first(1));
        assertEquals(List.list(1, 2), list.first(2));
        assertEquals(List.list(1, 2, 3), list.first(3));
        assertEquals(List.list(1, 2, 3), list.first(4));
        assertEquals(List.list(), list.first(-1));
    }

    @Test
    void emptyListRemainsEmpty() {
        assertEquals(List.list(), List.list().first(1));
        assertEquals(List.list(), List.list().filter(v -> true));
        assertEquals(Option.empty(), List.list().first());
    }

    @Test
    void listCanBeFiltered() {
        final var list = List.list(1, 2, 3);

        assertEquals(List.list(1), list.filter(v -> v < 2));
        assertEquals(List.list(1, 2), list.filter(v -> v < 3));
        assertEquals(List.list(1, 2, 3), list.filter(v -> v < 4));
        assertEquals(List.list(1, 2, 3), list.filter(v -> v > 0));
        assertEquals(List.list(), list.filter(v -> v < 0));
    }
}