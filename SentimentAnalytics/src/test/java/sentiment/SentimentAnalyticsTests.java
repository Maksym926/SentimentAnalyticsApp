package sentiment;


import com.test.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.testng.AssertJUnit.*;


public class SentimentAnalyticsTests {
    SentimentAnalytics sut;
    @BeforeEach
    void setUp() {
        InteractionGateway InteractionGateway = new InteractionGatewayFake();
         sut = new SentimentAnalytics(InteractionGateway);
    }

    @Test
    public void emptyResultOfNonExistingInteractionTest(){

        Optional<AnalyticsResult> result = sut.analyze("NON-EXISTING_INTERACTION");
        assertEquals(Optional.empty(), result);
    }
    @Test
    public void resultOfInteractionWithoutSegmentTest(){
        Interaction interaction = new Interaction();

        String id = sut.create(interaction);
        AnalyticsResult res = sut.analyze(id).get();


        assertEquals(Optional.empty(), res.getSentiment());





    }
}
