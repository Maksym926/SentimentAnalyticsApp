package com.test;

import java.util.ArrayList;
import java.util.List;

public class Interaction {
    List<Segment> segments = new ArrayList<>();
    public void addSegment(Segment segment){
        segments.add(segment);
    }

    public List<Segment> getSegments() {
        return segments;
    }
}
