// ID - 212945760

package menu.tasks;

/**
 * This is the Task interface, which is an action that needs to be executed and return a value.
 *
 * @param <T> the value that needs to be returned.
 * @author Ori Dabush.
 */
public interface Task<T> {

    /**
     * A method to run the task.
     *
     * @return the value that the task returns.
     */
    T run();
}
