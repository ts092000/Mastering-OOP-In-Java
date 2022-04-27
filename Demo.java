package com.bkitsolution;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PrimitiveIterator;

public class Demo {
    private static Optional<Painter> findCheapest1(double sqMeters, List<Painter> painters) {
        return painters.stream()
                .filter(Painter::isAvailable)
                .min(Comparator.comparing(painter -> painter.estimateCompensation(sqMeters)));
    }

    private static Optional<Painter> findCheapest2(double sqMeters, List<Painter> painters) {
        return Painter.stream(painters).available().cheapest(sqMeters);
    }


    private static Money getTotalCost(double sqMeters, List<Painter> painters) {
        return painters.stream()
                .filter(Painter::isAvailable)
                .map(painter -> painter.estimateCompensation(sqMeters))
                .reduce(Money::add)
                .orElse(Money.ZERO);
    }

    public void workTogether(double sqMeters, List<Painter> painters) {
        Velocity groupVelocity =
                Painter.stream(painters).available()
                        .map(painter -> painter.estimateVelocity(sqMeters))
                        .reduce(Velocity::add)
                        .orElse(Velocity.ZERO);

        Painter.stream(painters).available()
                .forEach(painter -> {
                    double partialSqMeters = sqMeters * painter.estimateVelocity(sqMeters).divideBy(groupVelocity);
                    Money partialCost = painter.estimateCompensation(partialSqMeters);
                    Duration partialTime = painter.estimateTimeToPaint(partialSqMeters);
                });
    }


    public void run() {
    }
}
