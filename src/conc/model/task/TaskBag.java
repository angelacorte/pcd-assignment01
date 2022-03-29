package conc.model.task;

/**
 * Data structure used to store tasks that need to be completed
 */
public interface TaskBag {
    /**
     * Add the task to the bag.
     * @param task The task to be added.
     */
    void addTask(Task task);

    /**
     * Resets the bag, removing any task left.
     */
    void clearBag();

    /**
     *
     * @return True if the bag of task is empty.
     */
    boolean isEmpty();

    /**
     * Get the next task to be done, removing it from the bag.
     * @return the next task.
     */
    Task nextTask();
}
