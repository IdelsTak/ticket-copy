package com.ct.views;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Utility class for displaying numbers formatted to two decimal places.
 *
 * @author admin
 */
public class RoundedDecimal {

    /**
     * Displays a number formatted to two decimal places.
     *
     * @param val an unformatted number.
     *
     * @return a number formatted to two decimal places.
     */
    public BigDecimal toTwoDecimalPlaces(BigDecimal val) {
        BigDecimal bd = Objects.requireNonNull(val, "Value should not be null");

        return new BigDecimal(String.format("%.2f", bd));
    }
}
