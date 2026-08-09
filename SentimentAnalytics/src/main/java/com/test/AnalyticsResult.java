package com.test;

import java.util.Optional;

public class AnalyticsResult {

    private Sentiment sentiment;


    public AnalyticsResult(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    public AnalyticsResult() {

    }


    public Optional<Sentiment> getSentiment() {
        return Optional.ofNullable(sentiment);
    }
}
