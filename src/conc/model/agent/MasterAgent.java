package conc.model.agent;

import conc.model.Body;
import conc.model.Boundary;
import conc.model.task.TaskBag;
import conc.model.task.TaskBagWithLinkedList;

import java.util.List;

/**
 * A Master Agent that coordinates other {@link WorkerAgent}
 */
public class MasterAgent extends Thread{
    private final TaskBag taskBag;
    private final List<Body> bodies;
    private final Boundary boundary;
    private double dt, vt;
    private int nSteps;

    public MasterAgent(List<Body> bodies, Boundary boundary){
        this.bodies = bodies;
        this.boundary = boundary;
        this.taskBag = new TaskBagWithLinkedList();
    }

    @Override
    public void run(){
        long iter = 0;
        while(iter < nSteps){
            for(Body b : bodies) {
                //taskBag.add(Compute total force task)
            }
        }
    }
}
