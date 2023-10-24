package sma.agents.consumer;

import sma.agents.FrameDependentBehaviour;
import sma.agents.industry.Idle;

import java.util.Random;

class ClientIdle extends FrameDependentBehaviour {
    double IdleTime ;
    double MaxIdleTime;

    @Override
    public void onStart(){
        super.onStart();
        IdleTime = 0;
        MaxIdleTime = new Random().nextDouble(1, 5);
    }
    @Override
    public void onAction() {
        IdleTime += DeltaTime;

    }
    @Override
    public boolean done() {
        return !(IdleTime <= MaxIdleTime);
    }
    @Override
    public void reset() {
        super.reset();
        MaxIdleTime = 0;
        IdleTime = 0;
    }
    @Override
    public int onEnd() {
        reset();
        return 1;
    }
}
