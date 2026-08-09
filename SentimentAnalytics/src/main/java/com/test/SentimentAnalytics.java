package com.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
            return AnalyticsResult.empty();
        }
        long size = calculateSize(interaction);



        Map<String, Integer> frequency = getFrequency(interaction);

        return getAnalyticsResult(frequency, size);

    }

    private long calculateSize(Interaction interaction) {
        return interaction.getSegments().stream()
                .collect(Collectors.summarizingInt(s->s.getText().size())).getSum();
    }

    private static Map<String, Integer> getFrequency(Interaction interaction) {
        Map<String, Integer> frequency = new HashMap<>();

        for(Segment segment: interaction.getSegments()){
            for(String s : segment.getText()){
                frequency.compute(s, (k, count) -> (count == null) ?  1 : (count + 1));
            }
        }

        return frequency;
    }

    private static AnalyticsResult getAnalyticsResult(Map<String, Integer> frequency, long size) {
        double positive = ((double) frequency.get("Positive")) / size;
        double neutral = ((double) frequency.get("Neutral")) / size;
        double negative = ((double) frequency.get("Negative")) / size;

        return AnalyticsResult.of(positive, neutral, negative);
    }


}
