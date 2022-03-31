package conc.model.task;

import conc.model.Body;
import conc.model.Boundary;
import conc.model.monitor.Barrier;

import java.util.List;

public final class CheckBoundaryTask extends BasicTask{
    private final Boundary boundary;

    public CheckBoundaryTask(List<Body> bodies, Boundary boundary, int start, int finish, Barrier barrier){
        super(bodies, barrier, start, finish);
        this.boundary = boundary;
    }
    @Override
    public void computeList(List<Body> bodies, int start, int finish){
        for(int i = start; i < finish; i++){
            Body b = bodies.get(i);
            b.checkAndSolveBoundaryCollision(boundary);
        }
    }
}
