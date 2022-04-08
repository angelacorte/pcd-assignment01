package conc.controller;

public class ConcurrentBodySimulationMain {

    public static void main(String[] args) {
        int nBodies = 2;
        int nSteps = 3;
        int nWorkers = 2;
        Simulator sim = new Simulator(nBodies, nSteps, nWorkers);
        sim.execute();
    }

//    java -jar JPF/jpf-core/build/RunJPF.jar ./config.jpf
}
