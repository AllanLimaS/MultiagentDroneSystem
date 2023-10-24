package sma.common;
import jade.core.AID;
import sma.Constants;
import java.util.concurrent.ThreadLocalRandom;

public class Entity {
    public AID Id;
    public Transform Transform;
    public EntityType Type;

    // General
    public int Product;

    // Industry
    public float ProductConstruictionPercent = 0;
    public Boolean IsProducing = false;
    public Boolean HasProduct = false;

    public Entity(EntityType type, int radius){
        var positionX = ThreadLocalRandom.current().nextInt(radius*4, (int)(Constants.WIDTH * .7));
        var positionY = ThreadLocalRandom.current().nextInt(radius*4, (int)(Constants.HEIGHT* .7));

        Transform = new Transform(positionX, positionY, radius);
        Type = type;
    }
}

