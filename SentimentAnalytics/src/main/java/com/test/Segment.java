package com.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Segment {
    public abstract int getDataSize();

    public Sentiment calculateSentiment(){
        Map<String, Integer> frequency = new HashMap<>();
        getSentimentString().stream()
                .forEach(s -> frequency.compute(s , (k, count) -> (count == null) ? 1 : count + 1));
        return getAnalyticsResult(frequency);
    }

    protected abstract List<String> getSentimentString();

    ;
    protected Sentiment  getAnalyticsResult(Map<String, Integer> frequency) {
        int size = getDataSize();
        double positive = ((double) frequency.get("Positive")) / size;
        double neutral = ((double) frequency.get("Neutral")) / size;
        double negative = ((double) frequency.get("Negative")) / size;

        return new Sentiment(positive, neutral, negative);
    }

}
