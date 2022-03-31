package conc.model.agent;

import conc.model.Body;
import conc.model.Boundary;
import conc.model.monitor.BarrierImpl;
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
    private Latch latch;
    private double dt, vt;

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
        vt = 0;
        dt = 0.001;
        long iter = 0;
        int nTasks = bodies.size() / nWorkers;
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

            //TODO nTasks = 0 per qualche ragione
            log("Assigning "+nTasks+" tasks");

            for(int i = 0; i < nTasks; i++){
                log("task added");
                taskBag.addTask(new ComputeAndUpdateVelocityTask(bodies, dt, i*nTasks, i*nTasks+nTasks));
            }

            waitLatch();

            latch.reset();

            log("Assigning work");

            for(int i = 0; i < nTasks; i++){
                taskBag.addTask(new UpdatePosTask(bodies, dt, i*nTasks, i*nTasks+nTasks));
            }

            waitLatch();

            latch.reset();

            log("Assigning work");

            for(int i = 0; i < nTasks; i++){
                taskBag.addTask(new CheckBoundaryTask(bodies, boundary, i*nTasks, i*nTasks+nTasks));
            }

            waitLatch();

            latch.reset();

            /* update virtual time */
            vt = vt + dt;
            iter++;
        }
    }

    private void waitLatch(){
        try {
            latch.waitCompletion();
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
