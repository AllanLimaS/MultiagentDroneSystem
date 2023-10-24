package sma.agents;

import jade.core.behaviours.Behaviour;

public abstract class FrameDependentBehaviour extends Behaviour {
    private long Start = System.currentTimeMillis();
    protected double DeltaTime = 0;
    @Override
    public void action() {
        long End = System.currentTimeMillis();
        long Delta = End - Start;

        DeltaTime = Delta / 1000.0;
        onAction();

        Start = System.currentTimeMillis();
    }

    @Override
    public void reset() {
        super.reset();
        DeltaTime = 0;
    }

    @Override
    public void onStart(){
        Start = System.currentTimeMillis();
    }

    public abstract void onAction();
}
