package sentiment;


import com.test.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.testng.AssertJUnit.*;


public class SentimentAnalyticsTests {
    SentimentAnalytics sut;
    private InteractionGateway interactionGateway;


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
        Segment segment = segmentForSentiment(0.7, 0.2, 0.1);
        interaction.addSegment(segment);
        String id = interactionGateway.create(interaction);

        AnalyticsResult res = sut.analyze(id).get();
        Sentiment sentiment = res.getSentiment().get();

        assertEquals(0.7, sentiment.getPositive());
        assertEquals(0.2, sentiment.getNeutral());
        assertEquals(0.1, sentiment.getNegative());


    }

    @Test
    public void resultOfInteractionWithMultipleSegmentsTest(){
        Interaction interaction = new Interaction();
        Segment segment1 = segmentForSentiment(0.5, 0.2, 0.3);
        Segment segment2 =  segmentForSentiment(0.7, 0.1, 0.2);
        interaction.addSegment(segment1);
        interaction.addSegment(segment2);
        String id = interactionGateway.create(interaction);

        AnalyticsResult res = sut.analyze(id).get();
        Sentiment sentiment = res.getSentiment().get();

        assertEquals(0.6, sentiment.getPositive());
        assertEquals(0.15, sentiment.getNeutral());
        assertEquals(0.25, sentiment.getNegative());


    }

    private Segment segmentForSentiment(double  positive, double neutral, double negative){
        List<String> text = new ArrayList<>();
        text.addAll(generateStringList(sizeOfScore(positive), "Positive"));
        text.addAll(generateStringList(sizeOfScore(neutral), "Neutral"));
        text.addAll(generateStringList(sizeOfScore(negative), "Negative"));



        return new Segment(text);

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
