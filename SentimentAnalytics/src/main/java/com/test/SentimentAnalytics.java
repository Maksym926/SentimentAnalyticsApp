package com.test;

import java.util.Optional;

public class SentimentAnalytics {

    private InteractionGateway interactionGateway;

    public SentimentAnalytics(InteractionGateway interactionGateway) {
        this.interactionGateway = interactionGateway;
    }

    public Optional<AnalyticsResult> analyze(String id) {
        Optional<Interaction> interaction = interactionGateway.getById(id);
        if(interaction.isPresent()){

            return Optional.of(new AnalyticsResult());
        }
        return Optional.empty();


    }

    public String create(Interaction interaction) {
        return interactionGateway.create(interaction);
    }
}
