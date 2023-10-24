package sma.agents.done;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import sma.agents.consumer.ConsumerProductRequest;

public class Idle extends Behaviour {
    DroneAgent Agent;
    public Boolean DeliveryIssued = false;

    public Idle(DroneAgent agent) {
        super(agent);
        Agent = agent;
    }

    @Override
    public void onStart(){
        Agent.RegisterDeliveryService();
        System.out.printf("Drone %s idling\n", Agent.Entity.Id.getName());
    }

    @Override
    public void action() {
        ACLMessage message = Agent.receive();
        if (message != null) {
            try {
                Agent.CurrentRequest = (ConsumerProductRequest) message.getContentObject();
                Agent.CurrentFactoryToFetch = message.getSender();
            } catch (UnreadableException e) {
                throw new RuntimeException(e);
            }
            DeliveryIssued = true;
        }
    }

    @Override
    public void reset(){
        super.reset();
        DeliveryIssued = false;
    }

    @Override
    public boolean done() {
        return DeliveryIssued;
    }

    @Override
    public int onEnd() {
        Agent.DeregisterDeliveryService();
        reset();
        return 1;
    }
}
