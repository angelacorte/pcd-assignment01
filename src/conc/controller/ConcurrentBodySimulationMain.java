package conc.controller;

import java.time.LocalDateTime;
import java.util.Date;

public class ConcurrentBodySimulationMain {

    public static void main(String[] args) {
        int nBodies = 1000;
        int nSteps = 1000;
        int nWorkers = Runtime.getRuntime().availableProcessors()+1;
        //int nWorkers = 1;
        System.out.println("SIMULATION STARTED \n #BODIES = " + nBodies + "\n #STEPS = " + nSteps + "\n #WORKERS = " + nWorkers);
        Simulator sim = new Simulator(nBodies, nSteps, nWorkers);
        sim.execute();
    }

//    java -jar ../pcd-2122/JPF/jpf-core/build/RunJPF.jar ./config.jpf
}
