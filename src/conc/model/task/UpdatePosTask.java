package conc.model.task;

import conc.model.Body;
import conc.model.monitor.Barrier;

import java.util.List;

public final class UpdatePosTask implements Task{
    private final List<Body> bodies;
    private double dt;
    private int start, finish;
    private Barrier barrier;

    public UpdatePosTask(List<Body> bodies, double dt, int start, int finish, Barrier barrier){
        this.bodies = bodies;
        this.dt = dt;
        this.start = start;
        this.finish = finish;
        this.barrier = barrier;
    }

    @Override
    public void executeWork() {
        for(int i = start; i < finish; i++){
            Body b = bodies.get(i);
            b.updatePos(dt);
        }
        try {
            barrier.hitAndWaitAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
