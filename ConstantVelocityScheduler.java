package com.bkitsolution;

import java.util.List;
import java.util.stream.Stream;

public class ConstantVelocityScheduler implements PaintingScheduler {

    @Override
    public Stream<WorkAssignment> schedule (List<Painter> painters, double sqMeters) {
        return this.schedule(painters, sqMeters, this.estimateTotalVelocity(painters, sqMeters));
    }

    private Stream<WorkAssignment> schedule(List<Painter> painters, double sqMeters, Velocity totalVelocity) {
        return Painter.stream(painters)
                .available()
                .map(painter -> painter.assign(
                        sqMeters * painter.estimateVelocity(sqMeters).divideBy(totalVelocity)));
    }

    private Velocity estimateTotalVelocity(List<Painter> painters, double sqMeters) {
        return Painter.stream(painters)
                .available()
                .map(painter -> painter.estimateVelocity(sqMeters))
                .reduce(Velocity::add)
                .orElse(Velocity.ZERO);
    }
}
