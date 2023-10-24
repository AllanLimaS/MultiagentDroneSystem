package sma;

import jade.wrapper.ContainerController;
import sma.common.Entity;

import javax.swing.*;
import java.util.*;

public class AppContext {
    public JFrame UI;
    public ContainerController JadeContainer;
    public List<Entity> Entities;

    public AppContext(ContainerController jadeContainer){
        Entities = new ArrayList<>();
        JadeContainer = jadeContainer;
    }
}

