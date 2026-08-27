package com.test;

import java.util.Optional;

public class AnalyticsResult {

    private Sentiment sentiment;


    public AnalyticsResult(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    public static AnalyticsResult empty(){
        return new AnalyticsResult(null);
    }
    public static AnalyticsResult of(double positive, double neutral, double negative){
        return new AnalyticsResult(new Sentiment(positive, neutral, negative));
    }


    public Optional<Sentiment> getSentiment() {
        return Optional.ofNullable(sentiment);
    }
}
