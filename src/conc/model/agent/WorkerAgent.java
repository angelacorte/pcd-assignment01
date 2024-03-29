package conc.model.agent;

import conc.model.monitor.Barrier;
import conc.model.monitor.Latch;
import conc.model.task.Task;
import conc.model.task.TaskBag;

/**
 * A simple, generalist worker agent that executes generic tasks
 */
public class WorkerAgent extends Thread{
    private final TaskBag tasks;
    private final Latch latch;

    /**
     *
     * @param tasks The tasks to execute.
     * @param latch A {@link Latch} used for coordination.
     */
    public WorkerAgent(TaskBag tasks, Latch latch){
        this.tasks = tasks;
        this.latch = latch;
    }

    @Override
    public void run(){
        while(true){
            Task task = tasks.nextTask();
            task.executeWork();
            latch.notifyCompletion();
        }
    }
}
