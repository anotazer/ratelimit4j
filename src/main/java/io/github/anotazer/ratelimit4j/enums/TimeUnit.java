package io.github.anotazer.ratelimit4j.enums;

public enum TimeUnit {
    SECOND(1),
    MINUTE(60),
    HOUR(3600),
    DAY(3600 * 24);

    private final int secondsMultiplier;

    TimeUnit(int secondsMultiplier) {
        this.secondsMultiplier = secondsMultiplier;
    }

    public int toSeconds(int duration) {
        return duration * secondsMultiplier;
    }
}
