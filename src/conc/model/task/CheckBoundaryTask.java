package conc.model.task;


import conc.model.Body;
import conc.model.Boundary;

import java.util.List;

public final class CheckBoundaryTask implements Task{
    private final List<Body> bodies;
    private final Boundary boundary;
    private int start, finish;

    public CheckBoundaryTask(List<Body> bodies, Boundary boundary, int start, int finish){
        this.bodies = bodies;
        this.boundary = boundary;
        this.start = start;
        this.finish = finish;
    }
    @Override
    public void executeWork() {
        for(int i = start; i < finish; i++){
            Body b = bodies.get(i);
            b.checkAndSolveBoundaryCollision(boundary);
        }
    }
}
