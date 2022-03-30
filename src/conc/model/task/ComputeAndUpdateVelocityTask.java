package conc.model.task;

import conc.model.Body;
import conc.model.V2d;

import java.util.List;

public final class ComputeAndUpdateVelocityTask implements Task{
    private final List<Body> bodies;
    private double dt;
    
    public ComputeAndUpdateVelocityTask(List<Body> bodies, double dt){
        this.bodies = bodies;
        this.dt = dt;
    }

    @Override
    public void executeWork() {
        for(Body b : bodies){
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
