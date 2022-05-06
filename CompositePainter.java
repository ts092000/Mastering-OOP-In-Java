package com.bkitsolution;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompositePainter implements Painter {
    private List<Painter> painters;
    private PaintingScheduler scheduler;

    private CompositePainter(List<Painter> painters, PaintingScheduler scheduler) {
        this.painters = painters;
        this.scheduler = scheduler;
    }

    public static Optional<CompositePainter> of(List<Painter> painters, PaintingScheduler scheduler) {
        return painters.size() == 0
                ? Optional.empty()
                : Optional.of(new CompositePainter(painters, scheduler));
    }

    @Override
    public Optional<Painter> available() {
        return CompositePainter.of(Painter.stream(this.painters).available().collect(Collectors.toList()),
                this.scheduler)
                .<Painter>map(Function.identity());
    }


    @Override
    public Duration estimateTimeToPaint(double sqMeters) {
        return this.scheduler.schedule(this.painters, sqMeters)
                .map(WorkAssignment::estimateTimeToPaint)
                .max(Duration::compareTo)
                .get();
    }

    @Override
    public Money estimateCompensation(double sqMeters) {
        return this.scheduler.schedule(this.painters, sqMeters)
                .map(WorkAssignment::estimateCompensation)
                .reduce(Money::add)
                .orElse(Money.ZERO);
    }

    @Override
    public String getName() {
        return this.getPainterNames().collect(Collectors.joining(", ", "{ ", " }"));
    }

    private Stream<String> getPainterNames() {
        return Painter.stream(this.painters).map(Painter::getName);
    }
}
