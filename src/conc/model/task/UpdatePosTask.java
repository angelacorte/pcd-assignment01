package conc.model.task;

import conc.model.Body;

import java.util.List;

public final class UpdatePosTask implements Task{
    private final List<Body> bodies;
    private double dt;

    public UpdatePosTask(List<Body> bodies, double dt){
        this.bodies = bodies;
        this.dt = dt;
    }

    @Override
    public void executeWork() {
        for(Body b : bodies){
            b.updatePos(dt);
        }
    }
}
