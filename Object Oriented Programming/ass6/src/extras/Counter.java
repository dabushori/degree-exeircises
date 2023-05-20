// ID - 212945760

package extras;

/**
 * This is the counter class, which will be used to count stuff.
 *
 * @author Ori Dabush.
 */
public class Counter {
    private int value;

    /**
     * A constructor for the Counter class.
     */
    public Counter() {
        this.value = 0;
    }

    /**
     * A method to add a number to the current count.
     *
     * @param number the number that will be added to the count.
     */
    public void increase(int number) {
        this.value += number;
    }

    /**
     * A method to subtract a number from the current count.
     *
     * @param number the number that will be subtracted from the count.
     */
    public void decrease(int number) {
        this.value -= number;
    }

    /**
     * A method to get the current count.
     *
     * @return the current count.
     */
    public int getValue() {
        return this.value;
    }
}