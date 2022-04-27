package com.bkitsolution;

import java.util.function.Function;

public class Demo {
    public double findZeroProcedural(Function<Double, Double> f, double x1, double x2) {
        double lower = x1;
        double upper = x2;
        double tolerance = 1e-15;

        while (upper - lower > tolerance) {
            double middle = (lower + upper) / 2;
            if (Math.signum(f.apply(middle)) == Math.signum(f.apply(lower))) {
                lower = middle;
            }
            else {
                upper = middle;
            }
        }

        return (lower + upper) / 2;
    }

    public double findZeroObjects(Function<Double, Double> f, double x1, double x2) {
        Segment range = new Segment(x1, x2);
        BisectionAlgorithm algorithm = range.bisect(f);
        double zero = algorithm.convergeTo(0);
        return zero;
    }


    public double findZeroObjectsFluent(Function<Double, Double> f, double x1, double x2) {
        return Segment.between(x1, x2)
            .bisect(f)
            .convergeTo(0);
    }

    public void run() {
        System.out.println(findZeroProcedural(x -> Math.cos(x + Math.PI / 8), Math.PI / 3, Math.PI));
        System.out.println(findZeroObjects(x -> Math.cos(x + Math.PI / 8), Math.PI / 3, Math.PI));
        System.out.println(findZeroObjectsFluent(x -> Math.cos(x + Math.PI / 8), Math.PI / 3, Math.PI));
    }
}
