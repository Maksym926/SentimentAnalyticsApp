package com.test;

public class Sentiment {
    private double positive;
    private double neutral;
    private double negative;

    public Sentiment(double positive, double neutral, double negative) {
        this.positive = positive;
        this.neutral = neutral;
        this.negative = negative;
    }

    public double getPositive() {
        return positive;
    }

    public double getNeutral() {
        return neutral;
    }

    public double getNegative() {
        return negative;
    }
}
