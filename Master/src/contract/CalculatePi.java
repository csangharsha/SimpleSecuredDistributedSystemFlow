package contract;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The `CalculatePi` class represents a task for calculating the mathematical
 * constant Pi (π) to a specified number of digits after the decimal point. It
 * implements the `Task` interface and is serializable for distributed
 * computation.
 *
 * <p>
 * The class utilizes the Machine's formula and a power series expansion of
 * arctan(x) to compute the value of Pi:
 *
 * <pre>
 * pi/4 = 4 * arctan(1/5) - arctan(1/239)
 * </pre>
 *
 * The computation is performed with a specified precision and rounding mode.
 *
 */
public class CalculatePi implements Task, Serializable {

    private static final long serialVersionUID = 227L;

    /**
     * constants used in pi computation
     */
    private static final BigDecimal FOUR = BigDecimal.valueOf(4);

    /**
     * rounding mode to use during pi computation
     */
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

    /**
     * digits of precision after the decimal point
     */
    private final int digits;

    private BigDecimal result = null;

    /**
     * Constructs a new `CalculatePi` instance with the specified number of digits of precision.
     *
     * @param digits The number of digits of precision for Pi computation.
     */
    public CalculatePi(int digits) {
        this.digits = digits;
    }

    /**
     * Executes the Pi computation and stores the result.
     */
    @Override
    public void executeTask() {
        result = computePi(digits);
    }

     /**
     * Gets the result of the Pi computation.
     *
     * @return The computed value of Pi as a BigDecimal.
     */
    @Override
    public Object getResult() {
        return result;
    }

    /**
     * Compute the value of pi to the specified number of digits after the
     * decimal point. The value is computed using Machine's formula:
     *
     * <pre> pi/4 = 4 * arctan(1/5) - arctan(1/239) </pre>
     *
     * and a power series expansion of arctan(x) to sufficient precision.
     *
     * @return result
     *
     * @param digits
     */
    public static BigDecimal computePi(int digits) {
        int scale = digits + 5;
        BigDecimal arctan1_5 = arctan(5, scale);
        BigDecimal arctan1_239 = arctan(239, scale);
        BigDecimal pi = arctan1_5.multiply(FOUR).subtract(arctan1_239).multiply(FOUR);
        return pi.setScale(digits, RoundingMode.HALF_UP);
    }

    /**
     * Compute the value, in radians, of the arc tangent of the inverse of the
     * supplied integer to the specified number of digits after the decimal
     * point. The value is computed using the power series expansion for the arc
     * tangent:
     *
     * <pre>arctan(x) = x - (x^3)/3 + (x^5)/5 - (x^7)/7 +(x^9)/9 ...</pre>
     *
     * @return result
     *
     * @param inverseX
     * @param scale
     */
    public static BigDecimal arctan(int inverseX, int scale) {
        BigDecimal result, numer, term;
        BigDecimal invX = BigDecimal.valueOf(inverseX);
        BigDecimal invX2 = BigDecimal.valueOf(inverseX * inverseX);
        numer = BigDecimal.ONE.divide(invX, scale, ROUNDING_MODE);
        result = numer;
        int i = 1;
        do {
            numer = numer.divide(invX2, scale, ROUNDING_MODE);
            int denom = 2 * i + 1;
            term = numer.divide(BigDecimal.valueOf(denom), scale, ROUNDING_MODE);
            if ((i % 2) != 0) {
                result = result.subtract(term);
            } else {
                result = result.add(term);
            }
            i++;
        } while (term.compareTo(BigDecimal.ZERO) != 0);

        return result;
    }

}
