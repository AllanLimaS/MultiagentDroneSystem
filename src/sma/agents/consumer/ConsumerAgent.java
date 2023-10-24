package sma.agents.consumer;

import jade.core.behaviours.FSMBehaviour;
import sma.agents.EntityAgent;

public class ConsumerAgent extends EntityAgent {

    protected void setup(){
        setupEntity(getAID());

        System.out.printf("Consumer created: %s%n at %f, %f\n", getAID().getName(), Entity.Transform.Position.x, Entity.Transform.Position.y);

        var brain = new FSMBehaviour();
        brain.registerFirstState(new ClientIdle(), "Idle");
        brain.registerState(new ClientRequestProduct(this), "RequestProduct");
        brain.registerState(new ClientProductWaitingIdle(this), "WaitingProduct");
        brain.registerTransition("Idle","RequestProduct", 1);
        brain.registerTransition("RequestProduct","WaitingProduct", 1);
        brain.registerTransition("WaitingProduct","Idle", 1);

        addBehaviour(brain);
    }

}

