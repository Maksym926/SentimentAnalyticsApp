package com.test;

import java.util.*;
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
                .collect(Collectors.summarizingInt(Segment::getDataSize)).getSum();
    }

    private Map<String, Integer> getFrequency(Interaction interaction) {
        Map<String, Integer> frequency = new HashMap<>();

        interaction.getSegments()
                .stream()
                .filter(s -> s instanceof TextSegment)
                .map(s -> (TextSegment) s)
                .flatMap(s -> s.getText().stream())
                .forEach(string -> frequency.compute(string, (k, count) -> (count == null) ?  1 : (count + 1)));
        interaction.getSegments()
                .stream()
                .filter(s -> s instanceof VoiceSegment)
                .map(s -> (VoiceSegment) s)
                .flatMap(s -> s.getPeaks().stream())
                .map(this::toStringRepresentation)
                .forEach(string -> frequency.compute(string, (k, count) -> (count == null) ?  1 : (count + 1)));
        return frequency;
    }

    private String toStringRepresentation(Integer integer) {
         if(integer >= 1 && integer<50){
             return "Negative";
         }
         else if (integer >= 50 && integer<75 ){
            return "Neutral";
         }
         else
             return "Positive";
    }

    private static AnalyticsResult getAnalyticsResult(Map<String, Integer> frequency, long size) {
        double positive = ((double) frequency.get("Positive")) / size;
        double neutral = ((double) frequency.get("Neutral")) / size;
        double negative = ((double) frequency.get("Negative")) / size;

        return AnalyticsResult.of(positive, neutral, negative);
    }



}
