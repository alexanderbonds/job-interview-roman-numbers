package ru.alexanderbond.roman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class NotationConverterTest {
    private static final String EXCEPTION_MESSAGE = "String must contain only valid roman numerals [I, V, X, L, C, D, M].";
    private NotationConverter converter;

    @BeforeEach
    public void init() {
        converter = new NotationConverter();
    }

    public static Stream<Arguments> getDataForCorrectTest() {
        return Stream.of(
                Arguments.of(3, "III"),
                Arguments.of(12, "XII"),
                Arguments.of(14, "XIV"),
                Arguments.of(33, "XXXIII"),
                Arguments.of(59, "LIX"),
                Arguments.of(1100, "MC"),
                Arguments.of(1990, "MCMXC")
        );
    }

    @ParameterizedTest
    @MethodSource("getDataForCorrectTest")
    @DisplayName("Handling correct roman numbers as an argument")
    public void testCorrectRomanNumbersAsAnArgument(int expected, String input) {
        assertEquals(expected, converter.toArabic(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Handling NULL and empty string as an argument")
    public void testNullAndEmptyString(String input) {
        Exception exception = assertThrows(InvalidValueException.class, () -> converter.toArabic(input));
        assertEquals(EXCEPTION_MESSAGE, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "VI1", "1"})
    @DisplayName("Handling illegal characters in an argument")
    public void testIllegalCharacters(String input) {
        Exception exception = assertThrows(InvalidValueException.class, () -> converter.toArabic(input));
        assertEquals(EXCEPTION_MESSAGE, exception.getMessage());
    }
}
