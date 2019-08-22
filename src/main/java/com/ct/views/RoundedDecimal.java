/*
 * Copyright (c) 2019, Hiram K.
 * All rights reserved.
 *
 */
package com.ct.views;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author admin
 */
public class RoundedDecimal {

    public BigDecimal toTwoDecimalPlaces(BigDecimal val) {
        BigDecimal bd = Objects.requireNonNull(val, "Value should not be null");

        return new BigDecimal(String.format("%.2f", bd));
    }
}
