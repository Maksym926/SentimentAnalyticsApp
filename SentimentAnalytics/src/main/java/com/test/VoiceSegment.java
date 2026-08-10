package com.test;

import java.util.Arrays;
import java.util.List;

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
}
