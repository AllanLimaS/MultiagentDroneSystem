package sma.agents.industry;

import sma.agents.FrameDependentBehaviour;

public class Produce extends FrameDependentBehaviour {
    IndustryAgent Agent;
    double CurrentTime = 0;

    public Produce(IndustryAgent agent) {
        Agent = agent;
    }
    @Override
    public void onAction() {
        CurrentTime += DeltaTime;
        Agent.Entity.ProductConstruictionPercent = (float) CurrentTime;
    }
    @Override
    public boolean done() {
        if (CurrentTime < 1)
            return false;

        Agent.Entity.ProductConstruictionPercent = 0;
        Agent.Entity.IsProducing = false;
        Agent.Entity.HasProduct = true;
        return true;
    }
    @Override
    public void onStart() {
        super.onStart();

        Agent.Entity.IsProducing = true;
        Agent.Entity.Product = Agent.CurrentRequest.ProductId;
    }
    @Override
    public void reset(){
        super.reset();
        Agent.Entity.IsProducing = false;
        CurrentTime = 0;
    }
    @Override
    public int onEnd() {
        reset();
        return 1;
    }
}
