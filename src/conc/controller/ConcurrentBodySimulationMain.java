package conc.controller;

public class ConcurrentBodySimulationMain {

    public static void main(String[] args) {
        int nBodies = 1000;
        int nSteps = 50000;
        int nWorkers = Runtime.getRuntime().availableProcessors()+1;
        //int nWorkers = 1;
        Simulator sim = new Simulator(nBodies, nSteps, nWorkers);
        sim.execute();
    }
}
