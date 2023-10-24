package sma.agents.industry;

import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import sma.common.Vector2;

import java.util.Objects;


public class IdleWithProduct extends Behaviour {
    Boolean FetchRequested = false;
    IndustryAgent Agent;

    public IdleWithProduct(IndustryAgent agent) {
        Agent = agent;
    }

    @Override
    public void action() {
        var template = new DFAgentDescription();
        var serviceDescription = new ServiceDescription();
        serviceDescription.setType("Deliver");
        template.addServices(serviceDescription);

        try{
            var result = DFService.search(myAgent, template);
            if(result.length == 0){
                return;
            }

            var droneDescription = result[0];

            if(droneDescription != null){
                var msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(droneDescription.getName());

                msg.setContentObject(Agent.CurrentRequest);
                Agent.send(msg);
                FetchRequested = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean done() {
        return FetchRequested;
    }

    @Override
    public void reset(){
        super.reset();
        FetchRequested = false;
    }
    @Override
    public int onEnd() {
        reset();
        return 1;
    }
}
