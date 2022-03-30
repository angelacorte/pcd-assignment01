package conc.model.monitor;

/*
 * Barrier - to be implemented
 */
public class BarrierImpl implements Barrier {

	private int nBodies;
	private int count = 0;
	
	public BarrierImpl(int nBodies) {
		this.nBodies = nBodies;
	}
	
	@Override
	public synchronized void hitAndWaitAll() throws InterruptedException {
		count++;
		System.out.println("hit #" + count);
		while(count < nBodies){
			wait();
		}
		notify(); //notifyAll();
	}

	
}
