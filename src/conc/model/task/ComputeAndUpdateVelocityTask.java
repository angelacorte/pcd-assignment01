package conc.model.task;

import conc.model.Body;
import conc.model.V2d;
import conc.model.monitor.Barrier;

import java.util.List;

public final class ComputeAndUpdateVelocityTask extends BasicTask{
    private double dt;
    
    public ComputeAndUpdateVelocityTask(List<Body> bodies, double dt, int start, int finish, Barrier barrier ){
        super(bodies, barrier, start, finish);
        this.dt = dt;
    }

    @Override
    public void computeList(List<Body> bodies, int start, int finish) {
        for(int i = start; i < finish; i++){
            Body b = bodies.get(i);
            /* compute total force on bodies */
            V2d totalForce = computeTotalForceOnBody(b);

            /* compute instant acceleration */
            V2d acc = new V2d(totalForce).scalarMul(1.0 / b.getMass());

            /* update velocity */
            b.updateVelocity(acc, dt);
        }
    }

    private V2d computeTotalForceOnBody(Body b) {

        V2d totalForce = new V2d(0, 0);

        /* compute total repulsive force */

        List<Body> bodies = getBodies();

        for (int j = 0; j < bodies.size(); j++) {
            Body otherBody = bodies.get(j);
            if (!b.equals(otherBody)) {
                try {
                    V2d forceByOtherBody = b.computeRepulsiveForceBy(otherBody);
                    totalForce.sum(forceByOtherBody);
                } catch (Exception ex) {
                }
            }
        }

        /* add friction force */
        totalForce.sum(b.getCurrentFrictionForce());

        return totalForce;
    }
}
