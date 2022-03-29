package conc.controller;

import conc.model.Body;

/**
 * Implements the monitor to manage threads in a parallel way
 */
public class Executor {
    /**
     * Here goes the monitor
     */

    private int counter = 0;
    private int nBodies;

    public Executor(int nBodies){
        this.nBodies = nBodies;
    }

    //TODO maybe its just hitAndWait and we can call it multiple times
    public synchronized void updateBodyVelocity(Body body) throws InterruptedException {
        counter++;
        //compute things???
        while(counter < nBodies){
            wait();
        }
        notify();
    }

    public synchronized void computeBodyNewPos(Body body) throws InterruptedException {

    }
}