package sma.agents.consumer;

import jade.core.AID;
import sma.common.Vector2;

import java.io.Serializable;

public class ConsumerProductRequest implements Serializable {
    public AID IssuerId;
    public int ProductId;

    public ConsumerProductRequest(int productId, AID issuerId){
        ProductId = productId;
        IssuerId = issuerId;
    }
}
