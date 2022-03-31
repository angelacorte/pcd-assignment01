package conc.model.task;

import conc.model.Body;
import conc.model.V2d;

import java.util.List;

public final class ComputeAndUpdateVelocityTask extends BasicTask{
    private static final String TASK_NAME = "Compute and update velocity Task";
    private final double dt;
    
    public ComputeAndUpdateVelocityTask(List<Body> bodies, Body subject, double dt){
        super(bodies, subject);
        this.dt = dt;
    }

    @Override
    public void computeTask(List<Body> bodies, Body subject) {
        /* compute total force on bodies */
        V2d totalForce = computeTotalForceOnBody(subject);

        /* compute instant acceleration */
        V2d acc = new V2d(totalForce).scalarMul(1.0 / subject.getMass());

        /* update velocity */
        subject.updateVelocity(acc, dt);
    }

    @Override
    public String getName(){return TASK_NAME;}

    private V2d computeTotalForceOnBody(Body b) {

        V2d totalForce = new V2d(0, 0);

        /* compute total repulsive force */

        List<Body> bodies = getBodies();

        for (Body otherBody : bodies) {
            if (!b.equals(otherBody)) {
                try {
                    V2d forceByOtherBody = b.computeRepulsiveForceBy(otherBody);
                    totalForce.sum(forceByOtherBody);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        /* add friction force */
        totalForce.sum(b.getCurrentFrictionForce());

        return totalForce;
    }
}
