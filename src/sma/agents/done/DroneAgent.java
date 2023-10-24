package sma.agents.done;

import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import sma.agents.EntityAgent;
import sma.agents.consumer.ConsumerProductRequest;
import jade.core.behaviours.FSMBehaviour;

public class
DroneAgent extends EntityAgent {

    public ConsumerProductRequest CurrentRequest;
    public AID CurrentFactoryToFetch;

    protected void setup(){
        setupEntity(getAID());

        var brain = new FSMBehaviour();
        brain.registerFirstState(new Idle(this), "Idle");
        brain.registerState(new Fetch(this), "Fetch");
        brain.registerState(new Delivery(this), "Deliver");
        brain.registerTransition("Idle","Fetch", 1);
        brain.registerTransition("Fetch","Deliver", 1);
        brain.registerTransition("Deliver","Idle", 1);

        addBehaviour(brain);
    }

    public void RegisterDeliveryService(){
        var agentDescriptor = new DFAgentDescription();
        agentDescriptor.setName(getAID());

        var serviceDescription = new ServiceDescription();
        serviceDescription.setType("Deliver");
        serviceDescription.setName("Deliver");

        agentDescriptor.addServices(serviceDescription);

        try{
            DFService.register(this, agentDescriptor);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

    public void DeregisterDeliveryService(){
        var agentDescriptor = new DFAgentDescription();
        agentDescriptor.setName(getAID());

        var serviceDescription = new ServiceDescription();
        serviceDescription.setType("Deliver");
        serviceDescription.setName("Deliver");

        agentDescriptor.addServices(serviceDescription);

        try{
            DFService.deregister(this, agentDescriptor);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

}
