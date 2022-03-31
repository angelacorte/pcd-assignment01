package conc.model.task;

import conc.model.Body;
import conc.model.monitor.Barrier;

import java.util.List;

public abstract class BasicTask implements Task{
    private final List<Body> bodies;
    private final int start;
    private final int finish;

    public BasicTask(List<Body> bodies, int start, int finish) {
        this.bodies = bodies;
        this.start = start;
        this.finish = finish;
    }

    @Override
    public void executeWork() {
        computeList(bodies, start, finish);
    }

    abstract protected void computeList(List<Body> bodies, int start, int finish);

    public List<Body> getBodies() {
        return bodies;
    }
}
