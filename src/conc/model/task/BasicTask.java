package conc.model.task;

import conc.model.Body;

import java.util.List;

public abstract class BasicTask implements Task{
    private final List<Body> bodies;
    private final Body subject;

    public BasicTask(List<Body> bodies, Body subject) {
        this.bodies = bodies;
        this.subject = subject;
    }

    @Override
    public void executeWork() {
        computeTask(bodies, subject);
    }

    abstract protected void computeTask(List<Body> bodies, Body subject);

    public List<Body> getBodies() {
        return bodies;
    }

    public Body getSubject(){return subject;}
}
