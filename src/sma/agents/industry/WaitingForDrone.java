package sma.agents.industry;

import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import sma.common.Vector2;

import java.util.Objects;

public class WaitingForDrone extends Behaviour {
    Boolean ProductGathered = false;
    IndustryAgent Agent;

    public WaitingForDrone(IndustryAgent agent) {
        Agent = agent;
    }

    @Override
    public void action() {
        var message = Agent.receive();
        if(message != null){
            ProductGathered = true;
        }else{
            block();
        }
    }
    @Override
    public boolean done() {
        return ProductGathered;
    }
    @Override
    public void reset(){
        super.reset();
        Agent.Entity.HasProduct = false;
        ProductGathered = false;

        Agent.CurrentRequest = null;
        Agent.RegisterProducer();
    }
    @Override
    public int onEnd() {
        reset();
        return 1;
    }
}
