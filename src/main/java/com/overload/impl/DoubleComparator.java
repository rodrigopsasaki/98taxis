package com.overload.impl;

import java.util.Comparator;

/**
 * Double-precision comparator.
 * @author Odell
 *
 * forked from https://github.com/muhatashim/OverloadLib
 */
public abstract class DoubleComparator<E> implements Comparator<E> {

    public abstract double compareD(E arg0, E arg1);

    @Override
    public int compare(E arg0, E arg1) {
        final double cD = compareD(arg0, arg1);
        if (cD < 0) return -1;
        if (cD > 0) return 1;
        return 0;
    }

}