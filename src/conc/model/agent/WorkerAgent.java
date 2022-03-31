package conc.model.agent;

import conc.model.monitor.Barrier;
import conc.model.task.Task;
import conc.model.task.TaskBag;

/**
 * A simple, generalist worker agent that executes generic tasks
 */
public class WorkerAgent extends Thread{
    private final TaskBag tasks;
    private final Barrier barrier;

    /**
     *
     * @param tasks The tasks to execute.
     * @param barrier A {@link Barrier} used for coordination.
     */
    public WorkerAgent(TaskBag tasks, Barrier barrier){
        this.tasks = tasks;
        this.barrier = barrier;
    }

    @Override
    public void run(){
        System.out.println("Running");
        while(true){
            log("Searching for task");
            Task task = tasks.nextTask();
            log("Executing work...");
            task.executeWork();
        }
    }

    private void log(String msg) {
        synchronized(System.out) {
            System.out.println("[ "+getName()+" ] "+msg);
        }
    }
}
