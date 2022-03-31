package conc.model.agent;

import conc.model.Body;
import conc.model.Boundary;
import conc.model.monitor.Latch;
import conc.model.monitor.LatchImpl;
import conc.model.task.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A Master Agent that coordinates other {@link WorkerAgent}
 */
public class MasterAgent extends Thread{
    private final TaskBag taskBag;
    private final List<WorkerAgent> workers;
    private final List<Body> bodies;
    private final Boundary boundary;
    private final long nSteps;
    private final int nWorkers;
    private final Latch latch;
    private double dt;

    public MasterAgent(List<Body> bodies, Boundary boundary, final long nSteps, int nWorkers){
        this.bodies = bodies;
        this.boundary = boundary;
        this.taskBag = new TaskBagWithLinkedList();
        this.workers = new ArrayList<>();
        this.latch = new LatchImpl(nWorkers);
        this.nSteps = nSteps;
        this.nWorkers = nWorkers;
    }

    @Override
    public void run(){
        double vt = 0;
        double dt = 0.001;
        long iter = 0;

        for(int i = 0; i < nWorkers-1; i++){
            WorkerAgent worker = new WorkerAgent(taskBag, latch);
            workers.add(worker);
            worker.start();
        }

        WorkerAgent worker = new WorkerAgent(taskBag, latch);
        worker.start();

        while(iter < nSteps){
            latch.reset();
            taskBag.clearBag();

            log("Assigning work...");

            bodies.forEach(b -> taskBag.addTask(new ComputeAndUpdateVelocityTask(bodies, b, dt)));

            waitLatch();

            log("Assigning work...");

            bodies.forEach(b -> taskBag.addTask(new UpdatePosTask(bodies, b, dt)));

            waitLatch();

            log("Assigning work...");

            bodies.forEach(b -> taskBag.addTask(new CheckBoundaryTask(bodies, b, boundary)));

            waitLatch();

            /* update virtual time */
            vt = vt + dt;
            iter++;
            log("Iteration " + iter + " completed");
        }

        workers.forEach(WorkerAgent::stopWorker);
        log("Ended simulation after " + iter + " iterations");
    }

    private void waitLatch(){
        try {
            latch.waitCompletion();
            latch.reset();
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
