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
     * @param barrier A {@link Barrier} used for coordination.
     */
    public WorkerAgent(TaskBag tasks, Latch latch){
        this.tasks = tasks;
        this.latch = latch;
    }

    @Override
    public void run(){
        log("Running");
        while(true){
            log("Waiting for task");
            Task task = tasks.nextTask();
            log("Executing work...");
            task.executeWork();
            latch.notifyCompletion();
        }
    }

    private void log(String msg) {
        synchronized(System.out) {
            System.out.println("[ "+getName()+" ] "+msg);
        }
    }
}
