package sma;

import sma.agents.consumer.ConsumerAgent;
import sma.common.Entity;
import sma.common.EntityType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenManager extends JFrame {
    private  AppContext Context;
    public ScreenManager(AppContext context){
        Context = context;
        context.UI = this;
        setTitle("Platform Manager");

        add(new ScreenDrawer(context));
        pack();
        setSize(Constants.WIDTH, Constants.HEIGHT);
        setLayout(null);
    }
}

class ScreenDrawer extends JPanel{
    private  AppContext Context;
    public ScreenDrawer (AppContext context){
        Context = context;

        Dimension size = new Dimension(Constants.WIDTH, Constants.HEIGHT);
        setPreferredSize(size);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Context.Entities.forEach(entity -> {
            switch (entity.Type){
                case Consumer -> DrawClientEntity(g, entity);
                case Industry -> DrawIndustryEntity(g, entity);
                case Drone -> DrawDroneEntity(g, entity);
            }
        });
    }

    private static void DrawDroneEntity(Graphics g, Entity entity) {
        getProductColor(g, entity);
        try {
            BufferedImage image = ImageIO.read(new File("src/images/drone.png"));
            g.drawImage(image,(int) entity.Transform.Position.x, (int) entity.Transform.Position.y,30,30,null);
            if(entity.HasProduct){
                g.fillOval((int) entity.Transform.Position.x+15, (int) entity.Transform.Position.y+20, entity.Transform.Radius, entity.Transform.Radius);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void DrawIndustryEntity(Graphics g, Entity entity) {
        getProductColor(g, entity);
        try {
            BufferedImage image = ImageIO.read(new File("src/images/industry.png"));
            g.drawImage(image,(int) entity.Transform.Position.x, (int) entity.Transform.Position.y,40,40,null);

            if(entity.IsProducing){
                g.drawString(String.valueOf(entity.ProductConstruictionPercent), (int)entity.Transform.Position.x, (int)entity.Transform.Position.y);
            }
            if(entity.HasProduct){
                g.fillOval((int) entity.Transform.Position.x, (int) entity.Transform.Position.y, entity.Transform.Radius, entity.Transform.Radius);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getProductColor(Graphics g, Entity entity) {
        switch (entity.Product) {
            case 0:
                g.setColor( new Color(0, 0, 0));
                break;
            case 1:
                g.setColor( new Color(46, 243, 9));
                break;
            case 2:
                g.setColor( new Color(21, 218, 225));
                break;
            case 3:
                g.setColor( new Color(224, 7, 7));
                break;
            case 4:
                g.setColor( new Color(133, 6, 178));
                break;
            case 5:
                g.setColor( new Color(231, 227, 19));
                break;
        }
    }

    private static void DrawClientEntity(Graphics g, Entity entity) {
        getProductColor(g, entity);
        try {
            BufferedImage image = ImageIO.read(new File("src/images/consumer.png"));
            g.drawImage(image,(int) entity.Transform.Position.x, (int) entity.Transform.Position.y,25,25,null);
            if(entity.HasProduct){
                g.fillOval((int) entity.Transform.Position.x, (int) entity.Transform.Position.y-7, entity.Transform.Radius, entity.Transform.Radius);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
