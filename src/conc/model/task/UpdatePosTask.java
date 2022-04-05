package conc.model.task;

import conc.model.Body;

import java.util.List;

public final class UpdatePosTask extends BasicTask{
    private static final String TASK_NAME = "Update position Task";
    private final double dt;

    public UpdatePosTask(List<Body> bodies, Body subject, double dt){
        super(bodies, subject);
        this.dt = dt;
    }

    @Override
    public void computeTask(List<Body> bodies, Body subject) {
        subject.updatePos(dt);
    }

    @Override
    public String getName(){return TASK_NAME;}
}
