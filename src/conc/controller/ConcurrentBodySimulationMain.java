package conc.controller;

import conc.model.agent.MasterAgent;
import conc.view.SimulationView;

public class ConcurrentBodySimulationMain {

    public static void main(String[] args) {

    	//SimulationView viewer = new SimulationView(620,620);

        Simulator sim = new Simulator();
        sim.execute(50000);
        System.out.println("bounds " + sim.getBounds() + " n bodies " + sim.getBodies().size());
        System.out.println("threads " + Runtime.getRuntime().availableProcessors() + 1);
        new MasterAgent(sim.getBodies(), sim.getBounds(), 50000, Runtime.getRuntime().availableProcessors() + 1).start();
    }
}
