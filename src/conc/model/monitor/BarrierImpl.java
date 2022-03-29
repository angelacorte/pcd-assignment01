package conc.model.monitor;

/*
 * Barrier - to be implemented
 */
public class BarrierImpl implements Barrier {

	private int nParticipants;
	private int count = 0;
	
	public BarrierImpl(int nParticipants) {
		this.nParticipants = nParticipants;
	}
	
	@Override
	public synchronized void hitAndWaitAll() throws InterruptedException {
		count++;
		while(count < nParticipants){
			wait();
		}
		notify(); //notifyAll();
	}

	
}
