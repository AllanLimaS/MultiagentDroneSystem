package sma.agents.consumer;

import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import sma.common.Vector2;

import java.util.Objects;
import java.util.Random;

public class ClientRequestProduct extends Behaviour {
    ConsumerAgent Agent;

    public ClientRequestProduct(ConsumerAgent agent) {
        super(agent);
        Agent = agent;
    }
    Boolean ProductWasRequested = false;
    @Override
    public void action() {
        var template = new DFAgentDescription();
        var serviceDescription = new ServiceDescription();
        serviceDescription.setType("Producer");
        template.addServices(serviceDescription);

        try {
            var result = DFService.search(myAgent, template);
            if(result.length == 0)
                return;

            var producerDescription = result[0];

            if (producerDescription != null) {
                var msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(producerDescription.getName());

                var productId = new Random().nextInt(0, 5);
                var requestMessage = new ConsumerProductRequest(productId, Agent.getAID());

                Agent.Entity.HasProduct = true;
                Agent.Entity.Product = productId;
                ProductWasRequested = true;

                System.out.printf("Requesting %s to produce %d\n", Agent.getAID().getName(), productId);

                msg.setContentObject(requestMessage);
                Agent.send(msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean done() {
        return ProductWasRequested;
    }
    @Override
    public void reset(){
        super.reset();
        ProductWasRequested = false;
    }
    @Override
    public int onEnd() {
        reset();
        return 1;
    }
}
