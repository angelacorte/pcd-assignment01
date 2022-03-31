package conc.model.task;


import conc.model.Body;
import conc.model.Boundary;
import conc.model.monitor.Barrier;

import java.util.List;

public final class CheckBoundaryTask implements Task{
    private final List<Body> bodies;
    private final Boundary boundary;
    private Barrier barrier;
    private int start, finish;

    public CheckBoundaryTask(List<Body> bodies, Boundary boundary, int start, int finish, Barrier barrier){
        this.bodies = bodies;
        this.boundary = boundary;
        this.start = start;
        this.finish = finish;
        this.barrier = barrier;
    }
    @Override
    public void executeWork(){
        for(int i = start; i < finish; i++){
            Body b = bodies.get(i);
            b.checkAndSolveBoundaryCollision(boundary);
        }
        try {
            barrier.hitAndWaitAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
