package conc.model.task;

import conc.model.Body;
import conc.model.Boundary;

import java.util.List;

public final class CheckBoundaryTask extends BasicTask{
    private static final String TASK_NAME = "Check Boundary Task";
    private final Boundary boundary;

    public CheckBoundaryTask(List<Body> bodies, Body subject, Boundary boundary){
        super(bodies, subject);
        this.boundary = boundary;
    }

    @Override
    public void computeTask(List<Body> bodies, Body subject){
        subject.checkAndSolveBoundaryCollision(boundary);
    }

    @Override
    public String getName(){return TASK_NAME;}
}
