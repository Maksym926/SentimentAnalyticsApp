package sentiment;

import com.test.Interaction;
import com.test.InteractionGateway;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InteractionGatewayFake implements InteractionGateway {

    Map<String, Interaction> interactions = new HashMap<>();

    @Override
    public String create(Interaction interaction) {
        String id = UUID.randomUUID().toString();
        interactions.put(id, interaction);

        return id;
    }

    @Override
    public Optional<Interaction> getById(String id) {
        return Optional.ofNullable(interactions.get(id));
    }
}
