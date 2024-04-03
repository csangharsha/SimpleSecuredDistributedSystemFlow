package contract;

import java.io.Serializable;

/**
 * The `TaskObject` class represents a serializable object that encapsulates
 * task-related information. It stores the task ID, credit, and a reference to
 * the task object itself.
 *
 */
public class TaskObject implements Serializable {

    private Integer TaskID = 0;
    private Integer Credit = 0;
    private Task TObject = null;

    /**
     * Constructs a new `TaskObject` instance with default values.
     */
    public TaskObject() {
    }

    /**
     * Gets the task ID.
     *
     * @return The task's unique identifier.
     */
    public Integer getTaskID() {
        return TaskID;
    }

    /**
     * Sets the task ID.
     *
     * @param TaskID The new task ID to set.
     */
    public void setTaskID(Integer TaskID) {
        this.TaskID = TaskID;
    }

    /**
     * Gets the credit associated with the task.
     *
     * @return The credit associated with the task.
     */
    public Integer getCredit() {
        return Credit;
    }

    /**
     * Sets the credit associated with the task.
     *
     * @param Credit The new credit value to set.
     */
    public void setCredit(Integer Credit) {
        this.Credit = Credit;
    }

    /**
     * Gets the reference to the task object.
     *
     * @return The reference to the task object.
     */
    public Task getTObject() {
        return TObject;
    }

    /**
     * Sets the reference to the task object.
     *
     * @param TObject The new task object reference to set.
     */
    public void setTObject(Task TObject) {
        this.TObject = TObject;
    }
}
