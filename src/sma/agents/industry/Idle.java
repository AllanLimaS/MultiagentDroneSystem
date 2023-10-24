package sma.agents.industry;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import sma.agents.consumer.ConsumerProductRequest;

public class Idle extends Behaviour {
    IndustryAgent Agent;
    Boolean ProductWasRequested = false;

    public Idle(IndustryAgent agent) {
        Agent = agent;
    }

    @Override
    public void onStart(){
        System.out.print("Factory entering idle");
    }
    @Override
    public void reset(){
        super.reset();
        ProductWasRequested = false;
    }

    @Override
    public void action() {
        var message = myAgent.receive();

        if (message != null) {
            if (message.getPerformative() == ACLMessage.REQUEST) {
                ConsumerProductRequest consumerRequest = null;
                try {
                    consumerRequest = (ConsumerProductRequest) message.getContentObject();
                } catch (UnreadableException e) {
                    throw new RuntimeException(e);
                }
                Agent.CurrentRequest = consumerRequest;
                Agent.DeregisterProducer();
                ProductWasRequested = true;
            }
        }
    }
    @Override
    public boolean done() {
        return ProductWasRequested;
    }
    @Override
    public int onEnd() {
        this.reset();
        return 1;
    }
}
