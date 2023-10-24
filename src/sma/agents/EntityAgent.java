package sma.agents;

import jade.core.AID;
import jade.core.Agent;
import sma.AppContext;
import sma.common.Entity;


public class EntityAgent extends Agent {
    public AppContext Context;
    public Entity Entity;
    protected void setupEntity(AID agentAID){
        Context = (AppContext)getArguments()[0];
        Entity = (Entity)getArguments()[1];

        this.Entity.Id = agentAID;
    }
}
