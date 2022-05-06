package com.bkitsolution;

import java.time.Duration;

public class MoneyRate {
    private Money intervalAmount;
    private Duration interval;

    private Duration getInterval() { return this.interval; }
    private long getSeconds() { return this.getInterval().getSeconds(); }

    private MoneyRate(Money intervalAmount, Duration interval) {
        this.intervalAmount = intervalAmount;
        this.interval = interval;
    }

    public static MoneyRate hourly(Money amount) {
        return new MoneyRate(amount, Duration.ofHours(1));
    }

    public Money getTotalPerHour() {
        return this.getTotalFor(Duration.ofHours(1));
    }

    public Money getTotalFor(Duration interval) {
        return this.intervalAmount.scale(interval.getSeconds(), this.getSeconds());
    }

    public String toString() {
        return this.getTotalPerHour() + "/hr.";
    }
}
