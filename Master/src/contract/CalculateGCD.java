package contract;

import java.io.Serializable;

/**
 * The `CalculateGCD` class represents a task for calculating the greatest common divisor (GCD) of two long integers.
 * It implements the `Task` interface and is also serializable, making it suitable for distributed computation scenarios.
 * 
 */
public class CalculateGCD implements Task, Serializable {

    private final long first;
    private final long second;

    private Long result = null;

    /**
     * Constructs a new `CalculateGCD` instance with the specified long integers.
     *
     * @param first  The first long integer for GCD calculation.
     * @param second The second long integer for GCD calculation.
     */
    public CalculateGCD(long first, long second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Executes the GCD calculation using the Euclidean algorithm and stores the result.
     */
    @Override
    public void executeTask() {
        result = calculateGCD(first, second);
    }

    /**
     * Calculates the greatest common divisor (GCD) of two long integers using the Euclidean algorithm.
     *
     * @param a The first long integer.
     * @param b The second long integer.
     * @return The GCD of the two long integers.
     */
    public long calculateGCD(long a, long b) {
        if (a == 0) {
            return b;
        } else {
            while (b != 0) {
                if (a > b) {
                    a = a - b;
                } else {
                    b = b - a;
                }
            }
            return a;
        }
    }

    /**
     * Gets the result of the GCD calculation.
     *
     * @return The GCD result as a Long.
     */
    @Override
    public Object getResult() {
        return result;
    }

    /**
     * Gets the first long integer used for GCD calculation.
     *
     * @return The first long integer.
     */
    public long getFirst() {
        return first;
    }

    /**
     * Gets the second long integer used for GCD calculation.
     *
     * @return The second long integer.
     */
    public long getSecond() {
        return second;
    }

}
