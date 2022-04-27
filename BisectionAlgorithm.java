package com.bkitsolution;

import java.util.function.Function;

public class BisectionAlgorithm {
    private Segment initialSegment;
    private Function<Double, Double> function;
    private double signFactor;

    public BisectionAlgorithm(Segment initialSegment, Function<Double, Double> function) {
        this.initialSegment = initialSegment;
        this.function = function;
        this.signFactor = initialSegment.valueAtLower(function);
    }

    public double convergeTo(double value) {
        Segment currentSegment = this.initialSegment;

        while (currentSegment.isDivisible()) {
            currentSegment = this.signAtMiddle(currentSegment, value) >= 0
                ? currentSegment.upperHalf()
                : currentSegment.lowerHalf();
        }

        return currentSegment.middle();
    }

    private double signAtMiddle(Segment segment, double goalValue) {
        return this.signFactor * Math.signum(segment.valueAtMiddle(this.function) - goalValue);
    }
}
