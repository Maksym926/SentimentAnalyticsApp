package sentiment;


import com.test.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.testng.AssertJUnit.*;


public class SentimentAnalyticsTests {
    SentimentAnalytics sut;
    private InteractionGateway interactionGateway;
    private final double DELTA = 0.0000001;


    @BeforeEach
    void setUp() {
        interactionGateway = new InteractionGatewayFake();
        sut = new SentimentAnalytics(interactionGateway);
    }

    @Test
    public void emptyResultOfNonExistingInteractionTest(){
        Optional<AnalyticsResult> result = sut.analyze("NON-EXISTING_INTERACTION");
        assertEquals(Optional.empty(), result);
    }
    @Test
    public void resultOfInteractionWithoutSegmentTest(){
        Interaction interaction = new Interaction();
        String id = interactionGateway.create(interaction);

        AnalyticsResult res = sut.analyze(id).get();

        assertEquals(Optional.empty(), res.getSentiment());
    }
    @Test
    public void resultOfInteractionWithSingleSegmentTest(){
        Interaction interaction = new Interaction();
        Segment segment = textSegmentForSentiment(0.7, 0.2, 0.1);
        interaction.addSegment(segment);
        String id = interactionGateway.create(interaction);

        AnalyticsResult res = sut.analyze(id).get();
        Sentiment sentiment = res.getSentiment().get();

        assertSentiment(sentiment, 0.7, 0.2, 0.1);



    }

    @Test
    public void resultOfInteractionWithMultipleSegmentsTest(){
        Interaction interaction = new Interaction();
        Segment segment1 = textSegmentForSentiment(0.5, 0.2, 0.3);
        Segment segment2 =  textSegmentForSentiment(0.7, 0.1, 0.2);
        interaction.addSegment(segment1);
        interaction.addSegment(segment2);
        String id = interactionGateway.create(interaction);

        AnalyticsResult res = sut.analyze(id).get();
        Sentiment sentiment = res.getSentiment().get();

        assertSentiment(sentiment, 0.6, 0.15, 0.25);


    }



    @Test
    public void resultOfInteractionWithSingleVoiceSegmentTest(){
        VoiceSegment voiceSegment = voiceSegmentForSentiment(0.9, 0.04, 0.06);
        Interaction interaction = new Interaction();
        interaction.addSegment(voiceSegment);
        String id = interactionGateway.create(interaction);

        AnalyticsResult result = sut.analyze(id).get();
        Sentiment sentiment = result.getSentiment().get();

        assertSentiment(sentiment, 0.9, 0.04, 0.06);
    }
    @Test
    public void resultOfInteractionWithVoiceAnTextSegmentsTest(){
        VoiceSegment voiceSegment = voiceSegmentForSentiment(0.4, 0.5,0.1);
        TextSegment textSegment = textSegmentForSentiment(0.6, 0.3, 0.1);
        Interaction interaction = new Interaction();
        interaction.addSegment(voiceSegment);
        interaction.addSegment(textSegment);
        String id = interactionGateway.create(interaction);

        AnalyticsResult result = sut.analyze(id).get();
        Sentiment sentiment = result.getSentiment().get();

        assertSentiment(sentiment, 0.5, 0.4, 0.1);

    }
    private void assertSentiment(Sentiment sentiment, double positive, double neutral, double negative) {
        assertEquals(positive, sentiment.getPositive(), DELTA);
        assertEquals(neutral, sentiment.getNeutral(), DELTA);
        assertEquals(negative, sentiment.getNegative(), DELTA);
    }
    private VoiceSegment voiceSegmentForSentiment(double positive, double neutral , double negative){
        List<Integer> peaks = new ArrayList<>();
        peaks.addAll(generatePeaks(sizeOfScore(positive), 75, 100));
        peaks.addAll(generatePeaks(sizeOfScore(neutral), 50, 74));
        peaks.addAll(generatePeaks(sizeOfScore(negative), 1, 49));

        return new VoiceSegment(peaks);
    }
    private List<Integer> generatePeaks(int size, int from, int to){
        Random random = new Random();
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i<size; i++){
            result.add(random.nextInt(to - from) + from );
        }
        return  result;
    }

    private TextSegment textSegmentForSentiment(double  positive, double neutral, double negative){
        List<String> text = new ArrayList<>();
        text.addAll(generateStringList(sizeOfScore(positive), "Positive"));
        text.addAll(generateStringList(sizeOfScore(neutral), "Neutral"));
        text.addAll(generateStringList(sizeOfScore(negative), "Negative"));



        return new TextSegment(text);

    }

    private int sizeOfScore(double score){
        return (int) (score * 100);
    }

    private List<String> generateStringList(int size, String word){
        List<String> result = new ArrayList<>();
        for(int i = 0; i<size; i++){
            result.add(word);
        }
        return  result;
    }

}
