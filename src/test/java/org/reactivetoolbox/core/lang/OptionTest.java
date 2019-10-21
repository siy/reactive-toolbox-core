package org.reactivetoolbox.core.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

//TODO: fix tests
class OptionTest {
    @Test
    void emptyOptionCanBeCreated() {
        Option.empty()
              .whenPresent(v -> fail());
    }

    @Test
    void optionWithDataCanBeCreated() {
        Option.with("not empty")
              .whenPresent(v -> assertEquals("not empty", v))
              .whenEmpty(Assertions::fail);
    }

    @Test
    void nonEmptyOptionCanBeMappedToOtherOption() {
        Option.with(123)
                .whenEmpty(Assertions::fail)
                .whenPresent(v -> assertEquals(123, v))
                .map(Object::toString)
                .whenEmpty(Assertions::fail)
                .whenPresent(v -> assertEquals("123", v));
    }

    @Test
    void emptyOptionRemainsEmptyAfterMapping() {
        Option.empty()
              .whenPresent(v -> fail())
              .map(Object::toString)
              .whenPresent(v -> fail());
    }

    @Test
    void nonEmptyCanContainNull() {
        Option.with(null)
              .whenEmpty(Assertions::fail)
              .whenPresent(Assertions::assertNull);
    }

    @Test
    void nonEmptyOptionCanBeFlatMappedIntoOtherOption() {
        Option.with(345)
              .whenEmpty(Assertions::fail)
              .whenPresent(v -> assertEquals(345, v))
              .flatMap(val -> Option.with(val + 2))
              .whenEmpty(Assertions::fail)
              .whenPresent(v -> assertEquals(347, v));
    }

    @Test
    void emptyOptionRemainsEmptyAndNotFlatMapped() {
        Option.empty()
              .whenPresent(v -> fail())
              .flatMap(val -> Option.with("not empty"))
              .whenPresent(v -> fail());
    }

    @Test
    void logicalOrChoosesFirsNonEmptyOption1() {
        final var firstNonEmpty = Option.with("1");
        final var secondNonEmpty = Option.with("2");
        final var firstEmpty = Option.<String>empty();
        final var secondEmpty = Option.<String>empty();

        assertEquals(firstNonEmpty, firstNonEmpty.or(() -> secondNonEmpty));
        assertEquals(firstNonEmpty, firstEmpty.or(() -> firstNonEmpty));
        assertEquals(firstNonEmpty, firstEmpty.or(() -> firstNonEmpty).or(() -> secondNonEmpty));
        assertEquals(firstNonEmpty, firstEmpty.or(() -> secondEmpty).or(() -> firstNonEmpty));
    }

    @Test
    void logicalOrChoosesFirsNonEmptyOption2() {
        final var firstNonEmpty = Option.with("1");
        final var secondNonEmpty = Option.with("2");
        final var firstEmpty = Option.<String>empty();
        final var secondEmpty = Option.<String>empty();

        assertEquals(firstNonEmpty, firstNonEmpty.or(secondNonEmpty));
        assertEquals(firstNonEmpty, firstEmpty.or(firstNonEmpty));
        assertEquals(firstNonEmpty, firstEmpty.or(firstNonEmpty).or(secondNonEmpty));
        assertEquals(firstNonEmpty, firstEmpty.or(secondEmpty).or(firstNonEmpty));
    }

    @Test
    void otherwiseProvidesValueIfOptionIsEmpty() {
        assertEquals(123, Option.empty().otherwise(123));
        assertEquals(123, Option.empty().otherwise(() -> 123));
    }

    @Test
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    void optionCanBeStreamed() {
        assertTrue(Option.empty().stream().findFirst().isEmpty());
        assertEquals(123, Option.with(123).stream().findFirst().get());
    }

    @Test
    void nonEmptyInstanceCanBeFiltered() {
        Option.with(123)
              .whenEmpty(Assertions::fail)
              .filter(val -> val > 1)
              .whenEmpty(Assertions::fail)
              .filter(val -> val < 100)
              .whenPresent(val -> fail());
    }

    @Test
    void emptyInstanceRemainsEmptyAfterFilteringAndPredicateIsNotInvoked() {
        Option.empty()
              .whenPresent(v -> fail())
              .filter(v -> true)
              .whenPresent(v -> fail());
    }
}