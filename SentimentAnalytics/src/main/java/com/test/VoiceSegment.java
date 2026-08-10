package com.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoiceSegment extends Segment {

    private final List<Integer> peaks;

    public VoiceSegment(List<Integer> peaks) {
        this.peaks = peaks;
    }

    public List<Integer> getPeaks() {
        return peaks;
    }

    @Override
    public int getDataSize() {
        return peaks.size();
    }

    @Override
    public Sentiment calculateSentiment() {
        Map<String, Integer> frequency = new HashMap<>();
        peaks.stream()
                .map(this::toStringRepresentation)
                .forEach(s -> frequency.compute(s , (k, count) -> (count == null) ? 1 : count + 1));
        return getAnalyticsResult(frequency);


    }

    @Override
    protected List<String> getSentimentString() {
        return peaks.stream()
               .map(this::toStringRepresentation)
               .toList();
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
}
