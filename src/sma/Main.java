package sma;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import sma.agents.consumer.ConsumerAgent;
import sma.agents.industry.IndustryAgent;
import sma.common.Entity;
import sma.common.EntityType;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Main  {
    public static void main(String args[]) throws StaleProxyException {
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.MAIN_PORT, "3250");

        Runtime r = Runtime.instance();
        final ContainerController cc = r.createMainContainer(p);

        AppContext context = new AppContext(cc);
        JFrame field = new ScreenManager(context);

        System.out.println("Field Agent initialized");


        for(int i = 0; i < 4; i++){
            var entity = new Entity(EntityType.Consumer, 10);
            context.Entities.add(entity);
            var controller = cc.createNewAgent("ConsumerAgent_"+i, ConsumerAgent.class.getName(), new Object[]{context, entity});
            controller.start();
        }

        for(int i = 0; i < 8; i++){
            var entity = new Entity(EntityType.Industry, 10);
            context.Entities.add(entity);
            var controller = cc.createNewAgent("IndustryAgent_"+i, IndustryAgent.class.getName(), new Object[]{context, entity});
            controller.start();
        }

        for(int i = 0; i < 8; i++){
            var entity = new Entity(EntityType.Drone, 10);
            context.Entities.add(entity);
            var controller = cc.createNewAgent("DroneAgent_"+i, sma.agents.done.DroneAgent.class.getName(), new Object[]{context, entity});
            controller.start();
        }

        field.setVisible(true);
        new Timer("Drawer", true).scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                field.repaint();
            }
        }, 100, (int)1000/60);
    }
}

