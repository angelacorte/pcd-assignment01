package conc.model.monitor;

public interface Latch {
    void reset();

    void waitCompletion() throws InterruptedException;

    void notifyCompletion();

    void stop();

    boolean stopped();
}
