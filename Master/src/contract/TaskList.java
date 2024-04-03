package contract;

import java.io.Serializable;

/**
 * The `TaskList` class represents a serializable object that stores a list of
 * available tasks and their corresponding class names.
 *
 */
public class TaskList implements Serializable {

    private String AvailableTasks[];
    private String TaskClassName[];

    /**
     * Gets the array of available task names.
     *
     * @return An array of available task names.
     */
    public String[] getAvailableTasks() {
        return AvailableTasks;
    }

    /**
     * Sets the array of available task names.
     *
     * @param AvailableTasks An array of available task names to set.
     */
    public void setAvailableTasks(String[] AvailableTasks) {
        this.AvailableTasks = AvailableTasks;
    }

    /**
     * Gets the array of class names corresponding to the available tasks.
     *
     * @return An array of class names corresponding to the available tasks.
     */
    public String[] getTaskClassName() {
        return TaskClassName;
    }

    /**
     * Sets the array of class names corresponding to the available tasks.
     *
     * @param TaskClassName An array of class names to set.
     */
    public void setTaskClassName(String[] TaskClassName) {
        this.TaskClassName = TaskClassName;
    }
}
