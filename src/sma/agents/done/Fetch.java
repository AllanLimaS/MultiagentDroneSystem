package sma.agents.done;

import jade.lang.acl.ACLMessage;
import sma.agents.FrameDependentBehaviour;
import sma.common.Vector2;

import java.util.Objects;

public class Fetch extends FrameDependentBehaviour {
    DroneAgent Agent;

    public Fetch(DroneAgent agent) {
        Agent = agent;
    }

    @Override
    public void onAction() {
        var transform = Agent.Entity.Transform;
        var targetTransform = Agent.Context.Entities.stream().filter(x -> Objects.equals(x.Id.getName(), Agent.CurrentFactoryToFetch.getName())).findFirst().get().Transform;

        var direction = Vector2.Distance(transform.Position, targetTransform.Position).Normalized();

        transform.Position.x += (float) (direction.x * DeltaTime * 150.0);
        transform.Position.y += (float) (direction.y * DeltaTime * 150.0);
    }

    @Override
    public boolean done() {
        var transform = Agent.Entity.Transform;
        var targetTransform = Agent.Context.Entities.stream().filter(x -> Objects.equals(x.Id.getName(), Agent.CurrentFactoryToFetch.getName())).findFirst().get().Transform;
        return Vector2.Distance(targetTransform.Position, transform.Position).Magnitude() <= 5;
    }

    @Override
    public int onEnd() {
        try {
            var msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(Agent.CurrentFactoryToFetch);
            msg.setContent("");
            Agent.send(msg);
        } catch (Exception e){
            e.printStackTrace();
        }

        super.reset();
        return 1;
    }
}
