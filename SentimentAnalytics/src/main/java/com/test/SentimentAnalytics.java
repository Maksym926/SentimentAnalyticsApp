package com.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SentimentAnalytics {

    private InteractionGateway interactionGateway;

    public SentimentAnalytics(InteractionGateway interactionGateway) {
        this.interactionGateway = interactionGateway;
    }

    public Optional<AnalyticsResult> analyze(String id) {
        return interactionGateway.getById(id)
                .map(this::analyze);


    }
    private AnalyticsResult analyze(Interaction interaction){

        if(interaction.getSegments().isEmpty()){
            return new AnalyticsResult(null);
        }
        Segment segment = interaction.getSegments().get(0);

        Map<String, Integer> frequency = new HashMap<>();

        long size = segment.getText().size();
        for(String s : segment.getText()){
            frequency.compute(s, (k, count) -> (count == null) ?  1 : (count + 1));
        }

        double positive = ((double) frequency.get("Positive")) / size;
        double neutral = ((double) frequency.get("Neutral")) / size;
        double negative = ((double) frequency.get("Negative")) / size;

        return new AnalyticsResult(new Sentiment(positive, neutral,negative));

    }


}
