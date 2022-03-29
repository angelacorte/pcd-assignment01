package conc.model.agent;

import conc.model.task.TaskBag;

/**
 * A simple, generalist worker agent that executes generic tasks
 */
public class WorkerAgent extends Thread{
    private final TaskBag tasks;

    /**
     *
     * @param tasks The tasks to execute.
     */
    public WorkerAgent(TaskBag tasks){
        this.tasks = tasks;
    }

    @Override
    public void run(){
        while(true){
            while(!tasks.isEmpty()){
                tasks.nextTask().executeWork();
            }
        }
    }
}
