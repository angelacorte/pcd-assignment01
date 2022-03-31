package conc.model.monitor;

public class LatchImpl implements Latch {
    private int nWorkers;
    private boolean stopped;
    private int nCompletionsNotified;

    public LatchImpl(int nWorkers) {
        this.nWorkers = nWorkers;
        nCompletionsNotified = 0;
        stopped = false;
    }

    @Override
    public synchronized void reset() {
        nCompletionsNotified = 0;
    }

    @Override
    public synchronized void waitCompletion() throws InterruptedException {
        while (nCompletionsNotified < nWorkers && !stopped) {
            wait();
        }
        if (stopped) {
            throw new InterruptedException();
        }
    }

    @Override
    public synchronized void notifyCompletion() {
        nCompletionsNotified++;
        notifyAll();
    }

    @Override
    public synchronized void stop() {
        stopped = true;
        notifyAll();
    }

    @Override
    public synchronized boolean stopped() {
        return stopped;
    }

}