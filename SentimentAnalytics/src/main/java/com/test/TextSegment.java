package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextSegment extends Segment {

    List<String> texts = new ArrayList<>();


    public TextSegment(List<String> texts) {
        this.texts = texts;
    }

    public List<String> getText() {
        return texts;
    }

    @Override
    public int getDataSize() {
        return texts.size();
    }

    @Override
    protected List<String> getSentimentString() {
        return texts;
    }


}
