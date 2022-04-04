package conc.controller;

public class ConcurrentBodySimulationMain {

    public static void main(String[] args) {
        int nBodies = 2;
        int nSteps = 2;
        //int nWorkers = Runtime.getRuntime().availableProcessors()+1;
        int nWorkers = 2;
        if(args.length > 0){
            nBodies = Integer.parseInt(args[0]);
            nSteps = Integer.parseInt(args[1]);
            nWorkers = Integer.parseInt(args[2]);
        }
        Simulator sim = new Simulator(nBodies, nSteps, nWorkers);
        sim.execute();
    }

//    java -jar JPF/jpf-core/build/RunJPF.jar ./config.jpf
}
