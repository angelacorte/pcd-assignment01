package conc.model.task;

/**
 * A generic Task is a unit of work to be executed
 */
public interface Task {
    /**
     * The work to be done
     */
    void executeWork();
}