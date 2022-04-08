package conc.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class ConcurrentBodySimulationMain {

    public static void main(String[] args) {
        String date = new Date().toLocaleString();
        int nBodies = 1000;
        int nSteps = 1000;
        int nWorkers = Runtime.getRuntime().availableProcessors()+1;
//        int nWorkers = 1;
        if(args.length > 0){
            nBodies = Integer.parseInt(args[0]);
            nSteps = Integer.parseInt(args[1]);
            nWorkers = Integer.parseInt(args[2]);
        }
        System.out.println("SIMULATION STARTED " + date + " \n #BODIES = " + nBodies + "\n #STEPS = " + nSteps + "\n #WORKERS = " + nWorkers);
        Simulator sim = new Simulator(nBodies, nSteps, nWorkers);
        sim.execute();
    }

//    java -jar JPF/jpf-core/build/RunJPF.jar ./config.jpf
}
