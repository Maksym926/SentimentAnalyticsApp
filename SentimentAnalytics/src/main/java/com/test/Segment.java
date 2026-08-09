package com.test;

import java.util.ArrayList;
import java.util.List;

public class Segment {

    List<String> segments = new ArrayList<>();


    public Segment(List<String> segments) {
        this.segments = segments;
    }

    public List<String> getText() {
        return segments;
    }
}
