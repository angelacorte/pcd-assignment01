package conc.model.task;

import conc.model.Body;
import conc.model.monitor.Barrier;

import java.util.List;

public final class UpdatePosTask extends BasicTask{
    private double dt;

    public UpdatePosTask(List<Body> bodies, double dt, int start, int finish){
        super(bodies, start, finish);
        this.dt = dt;
    }

    @Override
    public void computeList(List<Body> bodies, int start, int finish) {
        for(int i = start; i < finish; i++){
            Body b = bodies.get(i);
            b.updatePos(dt);
        }
    }
}
