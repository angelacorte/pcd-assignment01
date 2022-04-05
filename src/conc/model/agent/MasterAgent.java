package conc.model.agent;

import conc.model.Body;
import conc.model.Boundary;
import conc.model.monitor.*;
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
    private final StartSync sync;
    private final StopFlag flag;

    public MasterAgent(SimulationView view, List<Body> bodies, Boundary boundary, final long nSteps, int nWorkers, StartSync sync,
                       StopFlag flag){
        this.view = view;
        this.bodies = bodies;
        this.boundary = boundary;
        this.taskBag = new TaskBagWithLinkedList();
        this.workers = new ArrayList<>();
        this.latch = new LatchImpl(bodies.size());
        this.nSteps = nSteps;
        this.nWorkers = nWorkers;
        this.sync = sync;
        this.flag = flag;
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

        sync.waitStart();

        while(iter < nSteps){

            bodies.forEach(b -> taskBag.addTask(new ComputeAndUpdateVelocityTask(bodies, b, dt)));

            waitLatch();

            bodies.forEach(b -> taskBag.addTask(new UpdatePosTask(bodies, b, dt)));

            waitLatch();

            bodies.forEach(b -> taskBag.addTask(new CheckBoundaryTask(bodies, b, boundary)));

            waitLatch();

            vt = vt + dt;
            iter++;

            if(!flag.isSet()){
                view.display(bodies, vt, iter, boundary);
            }
        }

        workers.forEach(Thread::stop);
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
