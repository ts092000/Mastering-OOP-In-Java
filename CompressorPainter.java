package com.bkitsolution;

import java.time.Duration;
import java.util.Optional;

public class CompressorPainter implements Painter {
    private String name;
    private Duration fillTime;
    private double fillAfterSqMeters;
    private Duration cleaningTime;
    private double sqMetersPerHour;
    private MoneyRate rate;

    public CompressorPainter(String name, Duration fillTime, double fillAfterSqMeters, Duration cleaningTime, double sqMetersPerHour, MoneyRate rate) {
        this.name = name;
        this.fillTime = fillTime;
        this.fillAfterSqMeters = fillAfterSqMeters;
        this.cleaningTime = cleaningTime;
        this.sqMetersPerHour = sqMetersPerHour;
        this.rate = rate;
    }

    @Override
    public Optional<Painter> available() {
        return Optional.of(this);
    }

    @Override
    public Duration estimateTimeToPaint(double sqMeters) {
        Duration effectivePainting = Duration.ofSeconds((int)(sqMeters / this.sqMetersPerHour * 3600));
        int refillsCount = (int)Math.ceil(sqMeters / this.fillAfterSqMeters);
        Duration refillTime = this.fillTime.multipliedBy(refillsCount);
        return effectivePainting.plus(refillTime).plus(this.cleaningTime);
    }

    @Override
    public Money estimateCompensation(double sqMeters) {
        return this.rate.getTotalFor(this.estimateTimeToPaint(sqMeters));
    }

    @Override
    public String getName() {
        return this.name;
    }
}
