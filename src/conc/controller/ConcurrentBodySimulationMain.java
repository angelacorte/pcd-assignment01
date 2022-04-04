package conc.controller;

public class ConcurrentBodySimulationMain {

    public static void main(String[] args) {
        int nBodies = 2;
        int nSteps = 3;
        //int nWorkers = Runtime.getRuntime().availableProcessors()+1;
        int nWorkers = 2;
        System.out.println("SIMULATION STARTED \n #BODIES = " + nBodies + "\n #STEPS = " + nSteps + "\n #WORKERS = " + nWorkers);
        Simulator sim = new Simulator(nBodies, nSteps, nWorkers);
        sim.execute();
    }

//    java -jar ../pcd-2122/JPF/jpf-core/build/RunJPF.jar ./config.jpf
}
