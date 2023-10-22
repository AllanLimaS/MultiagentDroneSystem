import jade.core.Agent;

import java.util.Random;
import java.util.UUID;

public class IndustryAgent extends Agent {
    int DefaultRadius = 10;
    int BaseTimeIntervalMs = 5000;
    public UUID uuid;
    public AppContext Context;
    protected void setup(){
        Context = (AppContext)getArguments()[0];

        Random rand = new Random();

        var myTransform = new Transform(rand.nextInt(50, 600),rand.nextInt(50, 600), DefaultRadius);
        var myEntity = new Entity(myTransform);

        myEntity.Product = rand.nextInt(1,6);
        uuid = myEntity.Id;

        Context.IndustryTranform.add(myEntity);
    }
}

