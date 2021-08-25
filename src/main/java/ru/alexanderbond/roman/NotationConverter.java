package ru.alexanderbond.roman;

public class NotationConverter {
    private final String[] ROMAN_LITERALS = {"I", "V", "X", "L", "C", "D", "M"};
    private final int[] ARABIC_VALUES = {1, 5, 10, 50, 100, 500, 1000};

    public int toArabic(String romanNotation) throws InvalidValueException {
        if (romanNotation == null || !romanNotation.matches("^[IVXLCDM]+$")) {
            throw new InvalidValueException("String must contain only valid roman numerals [I, V, X, L, C, D, M].");
        }

        int result = 0, previous = 0;
        String[] romanNotationSymbols = romanNotation.split("");

        for (int i = romanNotationSymbols.length - 1; i >= 0; i--) {
            for (int j = 0; j < ROMAN_LITERALS.length; j++) {
                if (romanNotationSymbols[i].equals(ROMAN_LITERALS[j])) {
                    if (ROMAN_LITERALS[j].matches("^[IXC]$")) {
                        result = (previous == ARABIC_VALUES[j] * 5 || previous == ARABIC_VALUES[j] * 10)
                                ? result - ARABIC_VALUES[j]
                                : result + ARABIC_VALUES[j];
                    } else {
                        result += ARABIC_VALUES[j];
                    }
                    previous = ARABIC_VALUES[j];
                }
            }
        }

        return result;
    }
}
