package conc.model.task;


import conc.model.Body;
import conc.model.Boundary;

import java.util.List;

public final class CheckBoundaryTask implements Task{
    private final List<Body> bodies;
    private final Boundary boundary;

    public CheckBoundaryTask(List<Body> bodies, Boundary boundary){
        this.bodies = bodies;
        this.boundary = boundary;
    }
    @Override
    public void executeWork() {
        for (Body b : bodies) {
            b.checkAndSolveBoundaryCollision(boundary);
        }
    }
}
