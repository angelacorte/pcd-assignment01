package conc.model.task;

import java.util.LinkedList;

/**
 * A {@link TaskBag} that keeps track of tasks with a Linked List
 */
public class TaskBagWithLinkedList implements TaskBag{
    private final LinkedList<Task> tasks;

    public TaskBagWithLinkedList(){
        this.tasks = new LinkedList<>();
    }

    @Override
    public synchronized void addTask(Task task) {
        tasks.addLast(task);
        notifyAll();
    }

    @Override
    public synchronized void clearBag() {
        tasks.clear();
    }

    @Override
    public synchronized boolean isEmpty() {
        return tasks.isEmpty();
    }

    @Override
    public synchronized Task nextTask() {
        while(tasks.isEmpty()){
            try{
                wait();
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return tasks.removeFirst();
    }
}
