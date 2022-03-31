package conc.model.task;

import conc.model.Body;
import conc.model.monitor.Barrier;

import java.util.List;

public abstract class BasicTask implements Task{
    private final List<Body> bodies;
    private int start, finish;
    private Barrier barrier;

    public BasicTask(List<Body> bodies, Barrier barrier , int start, int finish) {
        this.bodies = bodies;
        this.barrier = barrier;
        this.start = start;
        this.finish = finish;
    }

    @Override
    public void executeWork() {
        computeList(bodies, start, finish);
        try {
            barrier.hitAndWaitAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    abstract protected void computeList(List<Body> bodies, int start, int finish);

    public List<Body> getBodies() {
        return bodies;
    }
}
