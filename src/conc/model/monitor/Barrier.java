package conc.model.monitor;

public interface Barrier {

	void hitAndWaitAll() throws InterruptedException;

}
