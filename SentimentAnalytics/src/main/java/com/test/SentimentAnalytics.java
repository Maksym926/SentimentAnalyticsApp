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


        double positive = 0;
        double neutral = 0;
        double negative = 0;


        List<Sentiment> sentiments = getSentiments(interaction);

        for(Sentiment sentiment : sentiments){
            positive+=sentiment.getPositive();
            neutral+=sentiment.getNeutral();
            negative+=sentiment.getNegative();
        }
        int size = sentiments.size();
        return AnalyticsResult.of(positive / size, neutral / size, negative / size);

    }

    private static List<Sentiment> getSentiments(Interaction interaction) {
        List<Sentiment> sentiments = interaction.getSegments()
                .stream()
                .map(Segment::calculateSentiment)
                .toList();
        return sentiments;
    }

    private long calculateSize(Interaction interaction) {
        return interaction.getSegments().stream()
                .collect(Collectors.summarizingInt(Segment::getDataSize)).getSum();
    }

}
