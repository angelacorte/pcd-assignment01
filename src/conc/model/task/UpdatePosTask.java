package conc.model.task;

import conc.model.Body;

import java.util.List;

public final class UpdatePosTask implements Task{
    private final List<Body> bodies;
    private double dt;
    private int start, finish;

    public UpdatePosTask(List<Body> bodies, double dt, int start, int finish){
        this.bodies = bodies;
        this.dt = dt;
        this.start = start;
        this.finish = finish;
    }

    @Override
    public void executeWork() {
        for(int i = start; i < finish; i++){
            Body b = bodies.get(i);
            b.updatePos(dt);
        }
    }
}
