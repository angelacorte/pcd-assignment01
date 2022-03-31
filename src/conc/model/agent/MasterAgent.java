package conc.model.agent;

import conc.model.Body;
import conc.model.Boundary;
import conc.model.monitor.Latch;
import conc.model.monitor.LatchImpl;
import conc.model.task.*;
import conc.view.SimulationView;

import java.util.ArrayList;
import java.util.List;

/**
 * A Master Agent that coordinates other {@link WorkerAgent}
 */
public class MasterAgent extends Thread{
    private final SimulationView view;
    private final TaskBag taskBag;
    private final List<WorkerAgent> workers;
    private final List<Body> bodies;
    private final Boundary boundary;
    private final long nSteps;
    private final int nWorkers;
    private final Latch latch;

    public MasterAgent(SimulationView view, List<Body> bodies, Boundary boundary, final long nSteps, int nWorkers){
        this.view = view;
        this.bodies = bodies;
        this.boundary = boundary;
        this.taskBag = new TaskBagWithLinkedList();
        this.workers = new ArrayList<>();
        this.latch = new LatchImpl(bodies.size());
        this.nSteps = nSteps;
        this.nWorkers = nWorkers;
    }

    @Override
    public void run(){
        double vt = 0;
        double dt = 0.001;
        long iter = 0;

        for(int i = 0; i < nWorkers; i++){
            WorkerAgent worker = new WorkerAgent(taskBag, latch);
            workers.add(worker);
            worker.start();
        }

        long startTime = System.nanoTime();

        log("Simulation started...");

        while(iter < nSteps){

            bodies.forEach(b -> taskBag.addTask(new ComputeAndUpdateVelocityTask(bodies, b, dt)));

            waitLatch();

            bodies.forEach(b -> taskBag.addTask(new UpdatePosTask(bodies, b, dt)));

            waitLatch();

            bodies.forEach(b -> taskBag.addTask(new CheckBoundaryTask(bodies, b, boundary)));

            waitLatch();

            /* update virtual time */
            vt = vt + dt;
            iter++;
            view.display(bodies, vt, iter, boundary);
            log("Iteration " + iter + " completed");
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;

        workers.forEach(WorkerAgent::stopWorker);
        log("Ended simulation after " + iter + " iterations and " + duration + "ms");
    }

    private void waitLatch(){
        try {
            latch.waitCompletion();
            latch.reset();
            taskBag.clearBag();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void log(String msg) {
        synchronized(System.out) {
            System.out.println("[ "+getName()+" ] "+msg);
        }
    }
}
