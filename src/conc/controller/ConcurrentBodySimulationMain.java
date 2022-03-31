package conc.controller;

public class ConcurrentBodySimulationMain {

    public static void main(String[] args) {

    	//SimulationView viewer = new SimulationView(620,620);

        Simulator sim = new Simulator();
        sim.execute(50000);
    }
}
