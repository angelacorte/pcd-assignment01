package conc.model.agent;

import conc.model.Body;
import conc.model.Boundary;
import conc.model.monitor.Barrier;
import conc.model.monitor.BarrierImpl;
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
    private final int nSteps, nWorkers;
    private Barrier barrier;
    private double dt, vt;

    public MasterAgent(List<Body> bodies, Boundary boundary, final int nSteps, int nWorkers){
        this.bodies = bodies;
        this.boundary = boundary;
        this.taskBag = new TaskBagWithLinkedList();
        this.workers = new ArrayList<>();
        this.barrier = new BarrierImpl(nWorkers+1);
        this.nSteps = nSteps;
        this.nWorkers = nWorkers;
    }

    @Override
    public void run(){
        System.out.println("run master agent");
        vt = 0;
        dt = 0.001;
        long iter = 0;
        int nTasks = bodies.size() / nWorkers;
        for(int i = 0; i < nWorkers; i++){
            WorkerAgent worker = new WorkerAgent(taskBag, barrier);
            workers.add(worker);
            worker.start();
        }

        while(iter < nSteps){

            for(int i = 0; i < nTasks; i++){
                taskBag.addTask(new ComputeAndUpdateVelocityTask(bodies, dt, i*nTasks, i*nTasks+nTasks));
            }

            waitBarrier();

            barrier = new BarrierImpl(nWorkers+1);

            for(int i = 0; i < nTasks; i++){
                taskBag.addTask(new UpdatePosTask(bodies, dt, i*nTasks, i*nTasks+nTasks));
            }

            waitBarrier();

            barrier = new BarrierImpl(nWorkers+1);

            for(int i = 0; i < nTasks; i++){
                taskBag.addTask(new CheckBoundaryTask(bodies, boundary, i*nTasks, i*nTasks+nTasks));
            }

            waitBarrier();

            /* update virtual time */
            vt = vt + dt;
            iter++;
        }
    }

    private void waitBarrier(){
        try {
            barrier.hitAndWaitAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
