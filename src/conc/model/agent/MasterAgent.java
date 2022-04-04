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

    public MasterAgent(List<Body> bodies, Boundary boundary, final long nSteps, int nWorkers){
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

        while(iter < nSteps){

            bodies.forEach(b -> taskBag.addTask(new ComputeAndUpdateVelocityTask(bodies, b, dt)));

            waitLatch();

            bodies.forEach(b -> taskBag.addTask(new UpdatePosTask(bodies, b, dt)));

            waitLatch();

            bodies.forEach(b -> taskBag.addTask(new CheckBoundaryTask(bodies, b, boundary)));

            waitLatch();

            vt = vt + dt;
            iter++;
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;

        workers.forEach(Thread::stop);

        System.out.println("SIMULATION ENDED \n #ITERATIONS = " + iter + "\n DURATION = " + duration + "ms\n=========================================");
        System.exit(0);
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
}
