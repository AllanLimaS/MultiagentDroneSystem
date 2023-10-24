package sma.agents.industry;

import jade.core.behaviours.FSMBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import sma.agents.EntityAgent;
import sma.agents.consumer.ConsumerProductRequest;
import sma.common.EntityType;

public class IndustryAgent extends EntityAgent {
    public ConsumerProductRequest CurrentRequest;

    protected void setup(){
        setupEntity(getAID());

        var brain = new FSMBehaviour();
        brain.registerFirstState(new Idle(this), "Idle");
        brain.registerState(new Produce(this), "Produce");
        brain.registerState(new IdleWithProduct(this), "IdleProduct");
        brain.registerState(new WaitingForDrone(this), "WaitingForDrone");
        brain.registerTransition("Idle","Produce", 1);
        brain.registerTransition("Produce","IdleProduct", 1);
        brain.registerTransition("IdleProduct","WaitingForDrone", 1);
        brain.registerTransition("WaitingForDrone","Idle", 1);

        addBehaviour(brain);

        RegisterProducer();
    }
    public void RegisterProducer(){
        var agentDescriptor = new DFAgentDescription();
        agentDescriptor.setName(getAID());

        var serviceDescription = new ServiceDescription();
        serviceDescription.setType("Producer");
        serviceDescription.setName("Producer");

        agentDescriptor.addServices(serviceDescription);

        try{
            DFService.register(this, agentDescriptor);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
    public void DeregisterProducer(){
        var agentDescriptor = new DFAgentDescription();
        agentDescriptor.setName(getAID());

        var serviceDescription = new ServiceDescription();
        serviceDescription.setType("Producer");

        agentDescriptor.addServices(serviceDescription);

        try{
            DFService.deregister(this, agentDescriptor);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}

