package sma.agents.consumer;

import jade.core.behaviours.Behaviour;
import sma.agents.industry.IndustryAgent;

public class ClientProductWaitingIdle extends Behaviour {
    Boolean ProductGathered = false;
    ConsumerAgent Agent;

    public ClientProductWaitingIdle(ConsumerAgent agent) {
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
        ProductGathered = false;
        Agent.Entity.HasProduct = false;
    }
    @Override
    public int onEnd() {
        reset();
        return 1;
    }
}
