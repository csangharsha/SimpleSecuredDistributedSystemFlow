package contract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The `CalculatePrimes` class represents a task for calculating prime numbers
 * within a specified range. It implements the `Task` interface and is
 * serializable for distributed computation.
 *
 * <p>
 * The class provides methods to execute the prime number calculation and
 * retrieve the list of prime numbers.
 *
 */
public class CalculatePrimes implements Task, Serializable {

    private final int low;
    private final int high;
    private List<Integer> result;

    /**
     * Constructs a new `CalculatePrimes` instance for calculating prime numbers
     * within the specified range.
     *
     * @param low The lower bound of the range.
     * @param high The upper bound of the range.
     */
    public CalculatePrimes(int low, int high) {
        this.low = low;
        this.high = high;
    }

    /**
     * Executes the prime number calculation for the specified range and stores
     * the result.
     */
    @Override
    public void executeTask() {
        result = new ArrayList<>();
        for (int i = low; i <= high; i++) {
            if (isPrime(i)) {
                result.add(i);
            }
        }
    }

    /**
     * Checks if a given number is prime.
     *
     * @param number The number to check for primality.
     * @return `true` if the number is prime; otherwise, `false`.
     */
    private boolean isPrime(int number) {
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the result of the prime number calculation.
     *
     * @return A list of prime numbers within the specified range.
     */
    @Override
    public Object getResult() {
        return result;
    }

}
