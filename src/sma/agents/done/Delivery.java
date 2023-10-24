package sma.agents.done;

import jade.lang.acl.ACLMessage;
import sma.agents.FrameDependentBehaviour;
import sma.common.Vector2;

import java.util.Objects;

public class Delivery extends FrameDependentBehaviour {
    DroneAgent Agent;

    public Delivery(DroneAgent agent) {
        Agent = agent;
    }

    @Override
    public void onStart(){
        super.onStart();
        Agent.Entity.HasProduct = true;
        Agent.Entity.Product = Agent.CurrentRequest.ProductId;
    }
    @Override
    public void onAction() {
        var transform = Agent.Entity.Transform;
        var targetTransform = Agent.Context.Entities.stream().filter(x -> Objects.equals(x.Id.getName(), Agent.CurrentRequest.IssuerId.getName())).findFirst().get().Transform;

        var direction = Vector2.Distance(transform.Position, targetTransform.Position).Normalized();

        transform.Position.x += (float) (direction.x * DeltaTime * 150.0);
        transform.Position.y += (float) (direction.y * DeltaTime * 150.0);
    }

    @Override
    public void reset(){
        super.reset();
        Agent.Entity.HasProduct = false;
        Agent.Entity.Product = 0;

        Agent.CurrentRequest = null;
        Agent.CurrentFactoryToFetch = null;
    }
    @Override
    public boolean done() {
        var transform = Agent.Entity.Transform;
        var targetTransform = Agent.Context.Entities.stream().filter(x -> Objects.equals(x.Id.getName(), Agent.CurrentRequest.IssuerId.getName())).findFirst().get().Transform;
        return Vector2.Distance(targetTransform.Position, transform.Position).Magnitude() <= 5;
    }
    @Override
    public int onEnd() {
        var msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(Agent.CurrentRequest.IssuerId);
        msg.setContent("");
        Agent.send(msg);
        super.reset();
        reset();
        return 1;
    }
}
