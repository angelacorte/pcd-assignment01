package conc.controller;

import conc.view.SimulationView;

public class ConcurrentBodySimulationMain {

    public static void main(String[] args) {
                
    	SimulationView viewer = new SimulationView(620,620);

    	Simulator sim = new Simulator(viewer);
        sim.execute(50000);
    }
}
