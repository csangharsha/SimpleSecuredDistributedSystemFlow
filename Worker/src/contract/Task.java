package contract;

import java.io.Serializable;

/**
 * The `Task` interface represents a serializable task that can be executed and has a result.
 * 
 */
public interface Task extends Serializable {

    /**
     * Executes the task logic.
     */
    public void executeTask();

    /**
     * Retrieves the result of the task.
     *
     * @return The result of the task.
     */
    public Object getResult();
}
