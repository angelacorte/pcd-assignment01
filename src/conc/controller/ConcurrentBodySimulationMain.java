package conc.controller;

public class ConcurrentBodySimulationMain {

    public static void main(String[] args) {
        int nBodies = Integer.parseInt(args[0]);
        int nSteps = Integer.parseInt(args[1]);
        //int nWorkers = Runtime.getRuntime().availableProcessors()+1;
        int nWorkers = Integer.parseInt(args[2]);
        System.out.println("SIMULATION STARTED \n #BODIES = " + nBodies + "\n #STEPS = " + nSteps + "\n #WORKERS = " + nWorkers);
        Simulator sim = new Simulator(nBodies, nSteps, nWorkers);
        sim.execute();
    }

//    java -jar JPF/jpf-core/build/RunJPF.jar ./config.jpf
}
