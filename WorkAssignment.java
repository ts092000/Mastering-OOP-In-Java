package com.bkitsolution;

import java.time.Duration;

public class WorkAssignment {
    private Painter painter;
    private double sqMeters;

    public WorkAssignment(Painter painter, double sqMeters) {
        this.painter = painter;
        this.sqMeters = sqMeters;
    }

    public Money estimateCompensation() {
        return this.painter.estimateCompensation(this.sqMeters);
    }

    public Duration estimateTimeToPaint() {
        return this.painter.estimateTimeToPaint(this.sqMeters);
    }
}
