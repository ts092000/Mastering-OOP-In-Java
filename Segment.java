package com.bkitsolution;

import java.util.function.Function;

public class Segment {
    private double low;
    private double high;

    public Segment(double low, double high) {
        this.low = low;
        this.high = high;
    }

    public static Segment between(double low, double high) {
        return new Segment(low, high);
    }

    public double valueAtLower(Function<Double, Double> f) {
        return f.apply(this.low);
    }

    public double valueAtMiddle(Function<Double, Double> f) {
        return f.apply(this.middle());
    }

    public Segment lowerHalf() {
        return new Segment(this.low, this.middle());
    }

    public Segment upperHalf() {
        return new Segment(this.middle(), this.high);
    }

    public boolean isDivisible() {
        double middle = this.middle();
        return middle != this.low && middle != this.high;
    }

    public double middle() {
        return (this.low + this.high) / 2;
    }

    public BisectionAlgorithm bisect(Function<Double, Double> usingFunction) {
        return new BisectionAlgorithm(this, usingFunction);
    }

    @Override
    public boolean equals(Object other) {
        return other != null && other instanceof Segment && this.equals((Segment)other);
    }

    private boolean equals(Segment other) {
        return this.low == other.low && this.high == other.high;
    }
}
