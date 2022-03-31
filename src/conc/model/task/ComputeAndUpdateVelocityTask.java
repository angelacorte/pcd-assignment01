package conc.model.task;

import conc.model.Body;
import conc.model.V2d;
import conc.model.monitor.Barrier;

import java.util.List;

public final class ComputeAndUpdateVelocityTask implements Task{
    private final List<Body> bodies;
    private double dt;
    private int start, finish;
    private Barrier barrier;
    
    public ComputeAndUpdateVelocityTask(List<Body> bodies, double dt, int start, int finish, Barrier barrier ){
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
            /* compute total force on bodies */
            V2d totalForce = computeTotalForceOnBody(b);

            /* compute instant acceleration */
            V2d acc = new V2d(totalForce).scalarMul(1.0 / b.getMass());

            /* update velocity */
            b.updateVelocity(acc, dt);
        }
        try {
            barrier.hitAndWaitAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
